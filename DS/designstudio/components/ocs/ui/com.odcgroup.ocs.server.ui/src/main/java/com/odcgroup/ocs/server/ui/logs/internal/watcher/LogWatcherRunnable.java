package com.odcgroup.ocs.server.ui.logs.internal.watcher;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;
import java.util.List;

import com.odcgroup.ocs.server.ServerCore;
import com.odcgroup.ocs.server.ui.logs.ILogChangedListener;
import com.odcgroup.ocs.server.ui.logs.ILogWatcherStoppedListener;

/**
 * @since 1.40
 * @author yan
 */
public class LogWatcherRunnable implements ILogWatcherRunnable {

	/** Name of the log file */
	private String name;
	
	/** Refresh interval */
	private static final long REFRESH_INTERVAL = 1000; // milliseconds
	
	/** Interval between stop checks */
	private static final long STOP_CHECK_INTERVAL = 100; // milliseconds

	/** 
	 * Nb checks interval for one refresh interval
	 */
	private static final long NB_CHECK_INTERVALS = REFRESH_INTERVAL/STOP_CHECK_INTERVAL;

	/** Set to true when the thread must stop itself */
	private boolean stopRequested = false;
	
	/** Defined if the console should be closed when the stop is requested */
	private boolean closeConsole = false;

	/** Log file to watch */
	private File logFile;

	/**
	 * Holds the listener which want to be notified when the watcher
	 * is stopped
	 */
	private List<ILogWatcherStoppedListener> watcherStoppedListener = 
		new LinkedList<ILogWatcherStoppedListener>();

	/**
	 * Holds the listener which want to be notified when new lines are
	 * detected
	 */
	private List<ILogChangedListener> logChangedListener = 
		new LinkedList<ILogChangedListener>();

	/**
	 * Create a runnable to what the log
	 * @param name name of the file (i.e. otf_trace.log)
	 * @param logFile reference to the file (include the full path)
	 */
	public LogWatcherRunnable(String name, File logFile) {
		this.name = name;
		this.logFile = logFile;
	}

	/**
	 * Add a listener to the watcher stopped event
	 * @param listener
	 */
	public void addStopListener(ILogWatcherStoppedListener listener) {
		watcherStoppedListener.add(listener);
	}

	/**
	 * Add a listener to the new lines detected event
	 * @param listener
	 */
	public void addLogChangedListener(ILogChangedListener listener) {
		logChangedListener.add(listener);
	}

	/**
	 * Request the watcher to stop itself
	 */
	public void requestStop(boolean closeConsole) {
		stopRequested = true;
		this.closeConsole = closeConsole;
	}

	/**
	 * Update the log file (i.e. if the user change the server location in the preferences)
	 * @param newLogFile
	 */
	public void updateLogFileName(File newLogFile) {
		this.logFile = newLogFile;
		if (!logFile.exists()) {
			notifyLogChangeListeners("Waiting for " + logFile);   //$NON-NLS-1$
		}
	}

	/**
	 * Notify the listeners of the watcher stopped event
	 */
	private void notifyStopListeners() {
		for (ILogWatcherStoppedListener listener : watcherStoppedListener) {
			try {
				listener.logWatcherStopped(this, closeConsole);
			} catch (RuntimeException e) {
				ServerCore.getDefault().logWarning("An error occurs in the log watcher stop listener: " + listener.getClass(), e);   //$NON-NLS-1$ 
			}
		}
	}
	
	/**
	 * Notify the listeners of the new line detected event
	 */
	private void notifyLogChangeListeners(String newText) {
		for (ILogChangedListener listener : logChangedListener) {
			try {
				listener.logChanged(newText);
			} catch (RuntimeException e) {
				ServerCore.getDefault().logWarning("An error occurs in the log watcher 'log changed' listener: " + listener.getClass(), e);   //$NON-NLS-1$
			}
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		if (!logFile.exists()) {
			notifyLogChangeListeners("Waiting for " + logFile);   //$NON-NLS-1$
		}
		
		long filePointer = logFile.length();
		main: while (true) {
			long fileLength = logFile.length();
			
			// Log file rotated or deleted ?
			if (fileLength < filePointer) {
				filePointer = 0;
				notifyLogChangeListeners("Reopening " + logFile.getName());
			}
			
			// New data to read ?
			if (fileLength > filePointer) {
				RandomAccessFile file = null;
				try {
					// Open the file (only if new data must be read)
					file = new RandomAccessFile(logFile, "r");
					file.seek(filePointer);
					for (String line = file.readLine(); line != null;) {
						notifyLogChangeListeners(line);
						line = file.readLine();
					}
					filePointer = file.getFilePointer();
				} catch (IOException e) {
					notifyLogChangeListeners("ERROR: Error during log reading " + logFile.getName() + ": " + e.toString());   //$NON-NLS-1$
				} finally {
					// Always release the file to avoid locking
					if (file != null) {
						try {
							file.close();
						} catch (IOException e) {
							notifyLogChangeListeners("ERROR: Unable to close properly " + logFile.getName());   //$NON-NLS-1$
						}
					}
				}
			}

			for (int i=0; i<NB_CHECK_INTERVALS; i++) {
				if (stopRequested) {
					break main;
				}
				try {
					Thread.sleep(STOP_CHECK_INTERVAL);
				} catch (InterruptedException e) {
					notifyLogChangeListeners("Log Watcher " + getName() + " is interrupted");  //$NON-NLS-1$  //$NON-NLS-2$
				}
			}
			
			if (stopRequested) {
				break;
			}
		}
		notifyLogChangeListeners("Log watching successfully finished.");
		notifyStopListeners();
	}

	/**
	 * @return the name of the watcher
	 */
	public String getName() {
		return name;
	}
	

}
