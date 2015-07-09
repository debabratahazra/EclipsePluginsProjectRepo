package com.odcgroup.t24.server.external.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.jface.dialogs.MessageDialog;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.server.model.IDSProject;
import com.odcgroup.server.model.IDSServer;
import com.odcgroup.server.ui.IServerContributions;
import com.odcgroup.server.ui.IServerExternalChangeListener;
import com.odcgroup.server.ui.UnableToStartServerException;
import com.odcgroup.server.ui.UnableToStopServerException;
import com.odcgroup.server.ui.views.IAddAction;
import com.odcgroup.t24.server.external.model.IExternalServer;
import com.odcgroup.t24.server.external.ui.util.T24ServerClasspathHelper;
import com.odcgroup.t24.server.external.util.T24ExternalServerManager;

public class ExternalT24ServerContributions implements IServerContributions {

	private static Logger logger = LoggerFactory.getLogger(ExternalT24ServerContributions.class);
	
	private static List<IServerExternalChangeListener> serverAddedRemovedExternallyListeners = 
			new LinkedList<IServerExternalChangeListener>();
	
	@Override
	public void refreshServers() {
		T24ExternalServerManager.getInstance().refresh();
	}

	@Override
	public List<IDSServer> getServers() {
		List<IDSServer> servers = new ArrayList<IDSServer>();
		servers.addAll(T24ServerUIExternalCore.getDefault().getDisplayableExternalServers());
		return servers;
	}

	@Override
	public void notifyProjectChanged(IProject project, IServerContributions.ChangeKind kind) {
		switch (kind) {
		case ADDED:
			if (T24ExternalServerManager.getInstance().isServerProject(project)) {
				boolean isAlreadyInServerList = false;
				for (IDSServer server : T24ExternalServerManager.getInstance().getServers()) {
					if (project.equals(server.getServerProject())) {
						isAlreadyInServerList = true;
						break;
					}
				}
				if (!isAlreadyInServerList) {
					IDSServer newServer = T24ExternalServerManager.getInstance().addServer(project);
					T24ExternalServerManager.initiliazeExternalServer();
					notifyServerAddedExternally(newServer);
				}
			}
			break;
		case CHANGED:
			if (T24ExternalServerManager.getInstance().isServerProject(project)) {
				boolean isAlreadyInServerList = false;
				for (IDSServer server : T24ExternalServerManager.getInstance().getServers()) {
					if (project.equals(server.getServerProject())) {
						isAlreadyInServerList = true;
						break;
					}
				}
				if (!isAlreadyInServerList) {
					IDSServer server = T24ExternalServerManager.getInstance().addServer(project);
					if (server != null) {
						notifyServerAddedExternally(server);
					}
				}
			} else {
				IDSServer serverFound = null;
				for (IDSServer server : T24ExternalServerManager.getInstance().getServers()) {
					if (project.equals(server.getServerProject())) {
						serverFound = server;
						break;
					}
				}
				if (serverFound != null) {
					T24ExternalServerManager.getInstance().removeServer(project);
					notifyServerRemovedExternally(serverFound);
				}
			}
			break;
		case REMOVED:
			IDSServer serverFound = null;
			for (IDSServer server : T24ExternalServerManager.getInstance().getServers()) {
				if (project.equals(server.getServerProject())) {
					serverFound = server;
					Preferences t24ExternalServerState = ConfigurationScope.INSTANCE.getNode("com.odcgroup.t24.server.external");
				    Preferences t24ExtServPref = t24ExternalServerState.node("t24ExtServ");
			        try {
			        	//delete old preference value
			        	t24ExtServPref.clear();
			            // save the preferences
			        	t24ExternalServerState.flush();
			        } catch (BackingStoreException e) {
			        	return;
			        }
					break;
				}
			}
			if (serverFound != null) {
				T24ExternalServerManager.getInstance().removeServer(project);
				
				notifyServerRemovedExternally(serverFound);
			}
			break;
		}

	}
	
	@Override
	public void addListenerServerAddedRemovedExternally(
			IServerExternalChangeListener listener) {
		serverAddedRemovedExternallyListeners.add(listener);
	}

	public static void notifyServerAddedExternally(IDSServer server) {
		for (IServerExternalChangeListener listener : serverAddedRemovedExternallyListeners) {
			try {
				listener.serverAddedExternally(server);
			} catch (RuntimeException e) {
				logger.error("Unexpected error while calling listener", e);
			}
		}
	}

	public static void notifyServerRemovedExternally(IDSServer server) {
		for (IServerExternalChangeListener listener : serverAddedRemovedExternallyListeners) {
			try {
				listener.serverRemovedExternally(server);
			} catch (RuntimeException e) {
				logger.error("Unexpected error while calling listener", e);
			}
		}
	}
	
	@Override
	public void fillAddServerToolbarMenu(IAddAction addAction) {
		addAction.addAction(new AddT24ExternalServerAction());
		//addAction.addAction(new AddT24IRISServerAction());
	}

	@Override
	public void fillAddServerContextMenu(IDSServer server, IAddAction addAction) {
		addAction.addAction(new AddT24ExternalServerAction());
		//addAction.addAction(new AddT24IRISServerAction());
	}

	@Override
	public void updateDeployedProjects(IDSServer server, IDSProject[] projects) {

		if (server instanceof IExternalServer) {
			final IExternalServer externalServer = (IExternalServer) server;

			// Compute the list of added/removed project
			List<IDSProject> previouslySelectedProjects = server.getDsProjects();
			List<IDSProject> newlySelectedProjects = Arrays.asList(projects);
			final List<IDSProject> addedDsProjects = new ArrayList<IDSProject>(newlySelectedProjects);
			addedDsProjects.removeAll(previouslySelectedProjects);
			final List<IDSProject> removedDsProjects = new ArrayList<IDSProject>(previouslySelectedProjects);
			removedDsProjects.removeAll(newlySelectedProjects);
	
			WorkspaceJob job = new WorkspaceJob("Updating server build path") {
				@Override
				public IStatus runInWorkspace(IProgressMonitor monitor)
						throws CoreException {
					
					T24ServerClasspathHelper.getInstance().updateServerBuildPath(externalServer, addedDsProjects, removedDsProjects);
					return Status.OK_STATUS;
				}
			};
			job.setSystem(true);
			job.setRule(ResourcesPlugin.getWorkspace().getRoot());
			job.schedule();

			StringBuffer projectsList = new StringBuffer();
			for (int i = 0; i < addedDsProjects.size(); i++) {
				projectsList.append(addedDsProjects.get(i).getName());
				if (i < addedDsProjects.size() - 1) {
					projectsList.append(", ");
				}
			}
		}
	}

	@Override
	public void start(final IDSServer server,final boolean debug)
			throws UnableToStartServerException {
		if (server instanceof IExternalServer) {
			IExternalServer externalT24Server = (IExternalServer)server;
			if (debug) {
				externalT24Server.setState(IDSServer.STATE_ACTIVE_IN_DEBUG);
			} else {
				externalT24Server.setState(IDSServer.STATE_ACTIVE);
			}
			T24ServerUIExternalCore.getDefault().getDeployBuilderConsole().setDisplayDebug(debug);
			
			for (IDSServer iDSServer : getServers()) {
				if(!iDSServer.equals(externalT24Server)){
					iDSServer.setState(IDSServer.STATE_INACTIVE);
				}	
			}
			
			if (externalT24Server.getDeployedProjects().isEmpty()) {
				MessageDialog.openWarning(null, "No project deployed", 
						"The active server does not have any project, " +
						"therefore nothing will be deployed.");
			}
		}
	}

	@Override
	public void stop(IDSServer server) throws UnableToStopServerException {
		if (server instanceof IExternalServer) {
			IExternalServer externalT24Server = (IExternalServer)server;
			externalT24Server.setState(IDSServer.STATE_INACTIVE);
		}	
	}

	@Override
	public void fillConfigureServerContextMenu(IDSServer server,
			IAddAction addAction) {
	}

}
