package com.odcgroup.ocs.server.external.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.dialogs.PreferencesUtil;

import com.odcgroup.ocs.server.ServerCore;
import com.odcgroup.ocs.server.external.model.IExternalServer;
import com.odcgroup.ocs.server.external.ui.builder.PrepareDeploymentFacade;
import com.odcgroup.ocs.server.external.ui.builder.PrepareUndeploymentFacade;
import com.odcgroup.ocs.server.external.ui.builder.UndeployJob;
import com.odcgroup.ocs.server.external.ui.util.ExternalServerManager;
import com.odcgroup.ocs.server.ui.OcsServerUICore;
import com.odcgroup.server.model.IDSProject;
import com.odcgroup.server.model.IDSServer;
import com.odcgroup.server.model.IDSServerStates;
import com.odcgroup.server.ui.IServerContributions;
import com.odcgroup.server.ui.IServerExternalChangeListener;
import com.odcgroup.server.ui.views.IAddAction;
import com.odcgroup.server.ui.views.ShowPreferencesAction;
import com.odcgroup.server.util.DSProjectUtil;

public class ExternalServerContributions implements IServerContributions {

	@Override
	public void refreshServers() {
		ExternalServerManager.initiliazeExternalServer();
	}

	@Override
	public List<IDSServer> getServers() {
		List<IDSServer> servers = new ArrayList<IDSServer>();
		servers.addAll(OCSServerUIExternalCore.getDefault().getDisplayableExternalServers());
		return servers;
	}

	@Override
	public void addListenerServerAddedRemovedExternally(
			IServerExternalChangeListener listener) {
		// Nothing to do
	}

	@Override
	public void fillAddServerToolbarMenu(IAddAction addAction) {
		Action showPreferencesAction = new ShowPreferencesAction("com.odcgroup.eds.prefs.servers.external");
		showPreferencesAction.setText("Configure External Server...");
		addAction.addAction(showPreferencesAction);
	}

	@Override
	public void fillAddServerContextMenu(IDSServer server,	IAddAction addAction) {
		IExternalServer externalServer = OCSServerUIExternalCore.getDefault().getExternalServer();

		if (externalServer.getState() == IDSServerStates.STATE_NOT_CONFIGURED) {
			Action showPreferencesAction = new ShowPreferencesAction("com.odcgroup.eds.prefs.servers.external");
			showPreferencesAction.setText("Configure External Server...");
			addAction.addAction(showPreferencesAction);
		}

	}

	@Override
	public void fillConfigureServerContextMenu(IDSServer server, IAddAction addAction) {
		if (server instanceof IExternalServer) {
			IExternalServer externalServer = (IExternalServer)server;
			if (externalServer.getState() != IDSServerStates.STATE_NOT_CONFIGURED) {
				Action showPreferencesAction = new ShowPreferencesAction("com.odcgroup.eds.prefs.servers.external");
				showPreferencesAction.setText("Open Server Preferences...");
				addAction.addAction(showPreferencesAction);

				Action openLoggingPref = new Action() {
					@Override
					public void run() {
						PreferenceDialog dialog = PreferencesUtil.createPreferenceDialogOn(Display.getDefault().getActiveShell(), "com.odcgroup.ocs.servers", null, null);
						dialog.open();
					}
				};
				openLoggingPref.setText("Open Logging Preferences ...");
				addAction.addAction(openLoggingPref);
			}
		}
	}
	
	@Override
	public void updateDeployedProjects(IDSServer server, IDSProject[] newProjectsToBeDeployed) {
		if (server instanceof IExternalServer) {
			IExternalServer externalServer = (IExternalServer) server;
			List<IDSProject> previouslyDeployedProjects = externalServer.getDsProjects();

			ServerCore.getDefault().getOCSServerPreferenceManager().updateDeployedProjects(newProjectsToBeDeployed);
			externalServer.updateProjectList(newProjectsToBeDeployed);
			// DS-2959: Do not deploy/undeploy if the server is not configured or started
			if (externalServer.getState() == IDSServerStates.STATE_STOPPED) {
				PrepareDeploymentFacade.makeProjectsDeployable(
						DSProjectUtil.convertToIProjects(newProjectsToBeDeployed), true);
				PrepareUndeploymentFacade.undeployProjects(previouslyDeployedProjects, newProjectsToBeDeployed);
			} else {
				// Compute the list of added project
				List<IDSProject> addedProjects = new ArrayList<IDSProject>();
				for (IDSProject ocsProject : newProjectsToBeDeployed) {
					if (!previouslyDeployedProjects.contains(ocsProject)) {
						addedProjects.add(ocsProject);
					}
				}

				// Compute the list of removed IProject
				List<IProject> listUndeployedProjects =
					PrepareUndeploymentFacade.listUndeployedProjects(
							DSProjectUtil.convertToIProjects(previouslyDeployedProjects),
							DSProjectUtil.convertToIProjects(newProjectsToBeDeployed));

				// Register projects for later undeployment
				for (IProject project: listUndeployedProjects) {
					ExternalServerManager.registerProjectForUndeployment(project);
				}

				if (addedProjects.size() > 0 || listUndeployedProjects.size() > 0) {
					// Provide a clear warning message if the server is already started
					String serverLocation = externalServer.getInstallationDirectory();
					if (serverLocation == null || !new File(serverLocation).exists()) {
						Display.getDefault().asyncExec(new Runnable() {
							@Override
							public void run() {
								MessageDialog.openWarning(Display.getDefault().getActiveShell(),
										"Server Already Started",
										"The External Server Preferences are not properly configured. No deployment will occur for now.\n"
												+ "You must first configure the External Server Preferences, "
												+ "then start the server.");
							}
						});
					} else {
						Display.getDefault().asyncExec(new Runnable() {
							@Override
							public void run() {
								MessageDialog.openWarning(Display.getDefault().getActiveShell(),
										"Server Already Started",
										"The server is already started. Therefore, the changes to deployed projects will take effect at the next server startup.");
							}
						});
					}
				}
			}
		}
	}

	@Override
	public void start(final IDSServer server, final boolean debug) {
		if (server instanceof IExternalServer) {
			final IExternalServer classicalServer = (IExternalServer)server;

			ExternalServerManager.processLateUndeployment();

			WorkspaceJob workspaceJob = new WorkspaceJob("Starting External Server") {
				@Override
				public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
					try {
						Job.getJobManager().join(UndeployJob.UNDEPLOY_JOB_FAMILY, monitor);

						OcsServerUICore.getDefault().getLogWatcherManager().startAllLogWatchers(server);
						if (debug) {
							server.setState(IDSServer.STATE_STARTING_DEBUG);
							if (ServerLauncherHelper.startServerInDebug(classicalServer)) {
								server.setState(IDSServer.STATE_STARTED_DEBUG);
							} else {
								server.setState(IDSServer.STATE_STOPPED);
								OcsServerUICore.getDefault().getLogWatcherManager().stopAllLogWatchers(false);
							}
						} else {
							if (ServerLauncherHelper.startServer(classicalServer)) {
								server.setState(IDSServer.STATE_STARTED);
							} else {
								server.setState(IDSServer.STATE_STOPPED);
								OcsServerUICore.getDefault().getLogWatcherManager().stopAllLogWatchers(false);
							}
						}
					} catch (OperationCanceledException e) {
						// Only cancel the server's startup
					} catch (InterruptedException e) {
						// Only cancel the server's startup
					}
					return Status.OK_STATUS;
				}
			};
			workspaceJob.setPriority(Job.BUILD);
			workspaceJob.schedule();
		}
	}

	@Override
	public void stop(IDSServer server) {
		if (server instanceof IExternalServer) {
			boolean alreadyStopping = server.getState() == IDSServer.STATE_STOPPING;
			boolean forceShutdown = false;
			IExternalServer classicalServer = (IExternalServer)server;
			// 1st attempt to stop the server
			if (!alreadyStopping) {
				server.setState(IDSServer.STATE_STOPPING);
				if (ServerLauncherHelper.stopServer(classicalServer)) {
				} else {
					forceShutdown = true;
				}
				OcsServerUICore.getDefault().getLogWatcherManager().stopAllLogWatchers(false);
			} else {
				forceShutdown = true;
			}

			if (forceShutdown) {
				ServerLauncherHelper.forceStopServer(classicalServer);
			}
		}

	}

	@Override
	public void notifyProjectChanged(IProject project, ChangeKind kind) {
		// Nothing to do as there is not server project for external TAP server
	}
}
