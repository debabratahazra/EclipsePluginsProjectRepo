package com.odcgroup.ocs.server.ui.logs;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.progress.WorkbenchJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.ocs.server.ServerCore;
import com.odcgroup.ocs.server.preferences.DSServerPreferenceManager;
import com.odcgroup.ocs.server.ui.logs.internal.watcher.ILogWatcherRunnable;
import com.odcgroup.ocs.server.ui.logs.internal.watcher.LogWatcherRunnable;
import com.odcgroup.ocs.support.preferences.OCSRuntimePreference;
import com.odcgroup.server.model.IDSServer;
import com.odcgroup.server.model.impl.DSServer;
import com.odcgroup.server.ui.views.ServerTreeLabelProvider;
import com.odcgroup.server.ui.views.ServerView;

/**
 * Manages the LogWatcher (threads)
 *
 * @author yan
 * @since 1.40
 */
public class LogWatcherManager implements ILogWatcherStoppedListener,
		IPreferenceChangeListener {

	private static Logger logger = LoggerFactory.getLogger(LogWatcherManager.class);

	private static final String THREAD_NAME = "LogWatcher for "; //$NON-NLS-1$

	private Map<String, ILogWatcherRunnable> logWatcherRunnables;
	private Map<String, LogConsole> logConsoles;

	static class NullServer extends DSServer {
		public NullServer() {
			super("null.id", "Null Server");
		}
		@Override
		public String getListeningPort() {
			return "";
		}
		@Override
		public String getLogDirectory() {
			return "";
		}
		@Override
		public String getInstallationDirectory() {
			return "";
		}
		@Override
		public IProject getServerProject() {
			return null;
		}
		@Override
		public boolean canDeploy(IProject project) {
			return false;
		}
	}

	private static final IDSServer NULL_SERVER = new NullServer();

	private IDSServer lastStartedServer = NULL_SERVER;

	public LogWatcherManager() {
		logWatcherRunnables = new HashMap<String, ILogWatcherRunnable>();
		logConsoles = new HashMap<String, LogConsole>();
		// Listen to log list changes
		ServerCore.getDefault().getProjectPreferences().addPreferenceChangeListener(this);
		// Listen to server location changes
		ServerCore.getDefault().getProjectPreferences().addPreferenceChangeListener(this);
	}

	public void startAllLogWatchers(IDSServer server) {
		stopAllLogWatchers(true);
		waitForWatchersToFinish();

		lastStartedServer = server;

		for (String log : server.getWatchedLogFiles()) {
			startLogWatcher(server, log);
		}

		// Bring the stdout console to the front
		IConsole[] consoles = ConsolePlugin.getDefault().getConsoleManager().getConsoles();
		for (IConsole console: consoles) {
			if (console.getType() != null &&
					console.getType().equals("org.eclipse.debug.ui.ProcessConsoleType") &&
					console.getName() != null &&
					console.getName().contains(ServerTreeLabelProvider.getDisplayedServerLabel(server))) {
				ConsolePlugin.getDefault().getConsoleManager().showConsoleView(console);
				break;
			}
		}
		WorkbenchJob workbenchJob = new WorkbenchJob("Show Server View") {
			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				try {
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(ServerView.SERVER_VIEW_ID);
				} catch (PartInitException e) {
					logger.error("Unable to show the Server View", e);
				}
				return Status.OK_STATUS;
			}
		};
		workbenchJob.setPriority(Job.SHORT);
		workbenchJob.setSystem(true);
		workbenchJob.schedule(100);
	}

	private void waitForWatchersToFinish() {
		for (int i=0; i<20; i++) {
			if (logWatcherRunnables.size() == 0) {
				return;
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// Ignore
			}
		}
		// Forces clean up
		logWatcherRunnables.clear();
	}

	public void startLogWatcher(IDSServer server, String logFileName) {
		// Does nothing if there is already a watching thread
		if (!logWatcherRunnables.containsKey(logFileName)) {
			// Create or reuse the console
			if (logConsoles.containsKey(logFileName)) {
				logConsoles.get(logFileName).dispose();
				logConsoles.remove(logFileName);
			}

			String serverName = ServerTreeLabelProvider.getDisplayedServerLabel(server);
			LogConsole logConsole = new LogConsole(logFileName, serverName);
			logConsoles.put(logFileName, logConsole);

			// Create the runnable
			ILogWatcherRunnable runnable = new LogWatcherRunnable(logFileName, getLogLocation(server, logFileName));
			logWatcherRunnables.put(logFileName, runnable);
			runnable.addStopListener(this);
			runnable.addLogChangedListener(logConsole);

			// Start the watching thread
			new Thread(runnable, THREAD_NAME + logFileName).start();
		}
	}

	public void stopAllLogWatchers(boolean closeConsole) {
		for (String key : logWatcherRunnables.keySet()) {
			stopLogWatcher(key, closeConsole);
		}
		lastStartedServer = NULL_SERVER;
	}

	private void stopLogWatcher(String logFileName, boolean closeConsole) {
		// Does nothing if there is no watching thread for this log
		if (logWatcherRunnables.containsKey(logFileName)) {
			logWatcherRunnables.get(logFileName).requestStop(closeConsole);
		}
	}

	private File getLogLocation(IDSServer server, String logFileName) {
		return new File(server.getLogDirectory(), logFileName);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.odcgroup.ocs.server.logs.ILogWatcherStoppedListener#logWatcherStopped
	 * (com.odcgroup.ocs.server.logs.internal.watcher.LogWatcherThread)
	 */
	@Override
	public void logWatcherStopped(ILogWatcherRunnable logWatcher,
			boolean closeConsole) {
		logWatcherRunnables.remove(logWatcher.getName());
		LogConsole logConsole = logConsoles.get(logWatcher.getName());
		if (logConsole != null) {
			logConsole.setLoggingFinished(true);
			if (closeConsole) {
				logConsoles.remove(logWatcher.getName());
				logConsole.dispose();
			}
		}
	}

	/**
	 * Stop the watcher for the shutdown and clean up the console reference to
	 * shared resources.
	 */
	public void dispose() {
		stopAllLogWatchers(false);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.core.runtime.preferences.IEclipsePreferences.
	 * IPreferenceChangeListener
	 * #preferenceChange(org.eclipse.core.runtime.preferences
	 * .IEclipsePreferences.PreferenceChangeEvent)
	 */
	@Override
	public void preferenceChange(PreferenceChangeEvent event) {
		if (DSServerPreferenceManager.WATCHED_LOG_FILES.equals(event.getKey())) {
			updateLogList(DSServerPreferenceManager.convertWatchedLogFileStringToList((String) event.getNewValue()));
		} else if (OCSRuntimePreference.INSTALL_DIR.equals(event.getKey())) {
			// TODO: should be moved in a the ocs-server-external(-ui?) plugin
			updateLogLocation((String) event.getNewValue());
		}
	}

	/**
	 * Update the log file list watched
	 *
	 * @param logFiles
	 */
	private void updateLogList(List<String> logFiles) {
		// Are watchers running ?
		if (logWatcherRunnables.size() > 0) {
			Set<String> watchersToStop = new HashSet<String>(
					logWatcherRunnables.keySet());
			for (String logFile : logFiles) {
				watchersToStop.remove(logFile);
			}
			// Stop removed watcher that are running & related console
			for (String watcherToStop : watchersToStop) {
				logWatcherRunnables.get(watcherToStop).requestStop(true);
			}

			// Start log watchers
			for (String logFile : logFiles) {
				if (logWatcherRunnables.containsKey(logFile)) {
					logWatcherRunnables.get(logFile).updateLogFileName(new File(lastStartedServer.getLogDirectory(), logFile));
				} else {
					startLogWatcher(lastStartedServer, logFile);
				}
			}
		}
	}

	/**
	 * Update the location of the watched files
	 *
	 * @param newValue
	 */
	private void updateLogLocation(String newValue) {
		if (logWatcherRunnables.size() > 0) {
			for (Map.Entry<String, ILogWatcherRunnable> entry : logWatcherRunnables.entrySet()) {
				entry.getValue().updateLogFileName(
						new File(lastStartedServer.getLogDirectory(), entry.getKey()));
			}
		}
	}

	/**
	 * @return the logWatcherRunnables
	 */
	public Map<String, ILogWatcherRunnable> getLogWatcherRunnables() {
		return logWatcherRunnables;
	}

}
