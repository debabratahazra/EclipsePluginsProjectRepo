package com.odcgroup.ocs.server.external.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Display;

import com.odcgroup.ocs.server.external.model.IExternalServer;
import com.odcgroup.ocs.server.external.ui.builder.PrepareDeploymentFacade;
import com.odcgroup.ocs.server.ui.OcsServerUICore;
import com.odcgroup.server.model.IDSProject;
import com.odcgroup.server.model.IDSServer;

/**
 * @author yan
 */
class ServerLauncherHelper {
	
	/** Keep a reference to the launch of the last debug session, allowing to kill the server later */
	private static ILaunch lastDebugLaunch;

	/**
	 * Keep a reference to the shutdown launch, allowing to kill the server later 
	 */
	private static ILaunch waitForShutdownLauch;


	public static boolean startServer(IExternalServer classicalServer) {
		// Ensure the builder of deployed project are correctly setup
		PrepareDeploymentFacade.updateServerViewAndFixConfig();
		
		// Clear log at startup (if configured to do it)
		clearLogs(classicalServer);
		
		// Ensure all projects that couldn't have been deployed when added to 
		// the server will be deployed at startup
		List<IProject> projects = new ArrayList<IProject>();
		for (IDSProject ocsProject : classicalServer.getDsProjects()) {
			projects.add(ResourcesPlugin.getWorkspace().getRoot().getProject(ocsProject.getName()));
		}
		PrepareDeploymentFacade.makeProjectsDeployable(projects, false /* Do not force the deploy */);
		
		// Launch the startup script
		return Program.launch(classicalServer.getStartScript());
	}
	
	public static boolean startServerInDebug(final IExternalServer classicalServer) {
		boolean started = startServer(classicalServer);

		if (started) {
			// Start a separate thread to attach to the server and wait for the server to finish
			new Thread() {
				public void run() {
					try {
						// Waits 5 sec. to let the patch processing to finish
						Thread.sleep(5000); 
						
						// Launch the server in debug
						lastDebugLaunch = ServerLauncherHelper.launchDebugSession();
						
						// Wait for the server to finish
						while (lastDebugLaunch != null && !lastDebugLaunch.isTerminated()) {
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								break;
							}
						}
						classicalServer.setState(IDSServer.STATE_STOPPED);
						OcsServerUICore.getDefault().getLogWatcherManager().stopAllLogWatchers(false);
					} catch (CoreException e1) {
						// Unable to launch in debug mode
						classicalServer.setState(IDSServer.STATE_STARTED);
					} catch (InterruptedException e1) {
						classicalServer.setState(IDSServer.STATE_STOPPED);
					}
					lastDebugLaunch = null;
				}
				
			}.start();
		}
		
		return started;
	}

	/**
	 * @return true if the debug session is running
	 */
	public static boolean isDebugSessionRunning() {
		return lastDebugLaunch != null;
	}

	/**
	 * Stop the current debug session
	 */
	public static void stopDebugSession() {
		if (lastDebugLaunch != null && !lastDebugLaunch.isTerminated()) {
			try {
				lastDebugLaunch.terminate();
			} catch (DebugException e) {
				// Ignore terminate failure
			}
		}
		lastDebugLaunch = null;
	}
	

	public static ILaunch launchDebugSession() throws CoreException {
		return launchDebugSession("Triple'A Plus Remote Debug");
	}

	public static ILaunch launchDebugSession(String name) throws CoreException {
		return createLaunchConfiguration(name).launch(ILaunchManager.DEBUG_MODE, null);
	}

	private static ILaunchConfiguration createLaunchConfiguration(String name) throws CoreException {
		ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
		ILaunchConfigurationType type = manager.getLaunchConfigurationType(IJavaLaunchConfigurationConstants.ID_REMOTE_JAVA_APPLICATION);
		ILaunchConfigurationWorkingCopy wc = type.newInstance(null, name);
		wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME, "");
		wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_VM_CONNECTOR, IJavaLaunchConfigurationConstants.ID_SOCKET_ATTACH_VM_CONNECTOR);
		wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_ALLOW_TERMINATE, true);
		Map<String, String> connectionProperties = new HashMap<String, String>();
		connectionProperties.put("port", "8989");
		connectionProperties.put("hostname", "localhost");
		wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_CONNECT_MAP, connectionProperties);
		return wc.doSave();
	}

	public static void clearLogs(IExternalServer server) {
		if (server.isClearLogAtStartup()) {
			File logDirectory = new File(server.getLogDirectory());
			if (logDirectory.isDirectory()) {
				for (File logfile : logDirectory.listFiles()) {
					if (logfile.isFile()) {
						logfile.delete();
					}
				}
			}
		}
	}

	public static boolean stopServer(final IExternalServer classicalServer) {
		if (Program.launch(classicalServer.getStopScript())) {
			// Create a separate thread to wait for the end of the script execution
			if (!ServerLauncherHelper.isDebugSessionRunning()) {
				new Thread() {
					public void run() {
						try {
							waitForShutdownLauch = ServerLauncherHelper.launchDebugSession("Triple'A Plus Remote Debug - wait for shutdown");
							while (waitForShutdownLauch != null &&
									!waitForShutdownLauch.isTerminated()) {
								Thread.sleep(1000);
							}
						} catch (InterruptedException e) {
							// Ignore, process already finished
						} catch (CoreException e) {
							// Ignore, process already finished
						}
						Display.getDefault().asyncExec(new Runnable() {
							public void run() {
								classicalServer.setState(IDSServer.STATE_STOPPED);
							}
						});
						OcsServerUICore.getDefault().getLogWatcherManager().stopAllLogWatchers(false);
					}
				}.start();
			}
			return true;
		} else  {
			return false;
		}
	}

	public static void forceStopServer(IExternalServer classicalServer) {
		if (ServerLauncherHelper.isDebugSessionRunning()) {
			// If a debug session is started, stop the session itself
			ServerLauncherHelper.stopDebugSession();
		} else {
			// Otherwise, connect to the session to terminate it 
			try {
				if (waitForShutdownLauch != null) {
					waitForShutdownLauch.terminate();
				} else {
					ServerLauncherHelper.launchDebugSession("Triple'A Plus Remote Debug - kill session").terminate();
				}
			} catch (Exception e) {
				// Ignore failure
			}
			waitForShutdownLauch = null;
		}
		classicalServer.setState(IDSServer.STATE_STOPPED);
	}

}
