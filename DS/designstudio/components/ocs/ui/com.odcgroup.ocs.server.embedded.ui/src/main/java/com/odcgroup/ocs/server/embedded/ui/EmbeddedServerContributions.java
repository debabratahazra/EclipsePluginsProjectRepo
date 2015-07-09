package com.odcgroup.ocs.server.embedded.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.ocs.server.embedded.model.IEmbeddedServer;
import com.odcgroup.ocs.server.embedded.util.EmbeddedServerClasspathHelper;
import com.odcgroup.ocs.server.embedded.util.EmbeddedServerManager;
import com.odcgroup.ocs.server.ui.OcsServerUICore;
import com.odcgroup.server.model.IDSProject;
import com.odcgroup.server.model.IDSServer;
import com.odcgroup.server.model.IDSServerStates;
import com.odcgroup.server.ui.IServerContributions;
import com.odcgroup.server.ui.IServerExternalChangeListener;
import com.odcgroup.server.ui.UnableToStartServerException;
import com.odcgroup.server.ui.UnableToStopServerException;
import com.odcgroup.server.ui.views.IAddAction;
import com.odcgroup.server.ui.views.ShowPreferencesAction;
import com.odcgroup.workbench.core.OfsCore;

public class EmbeddedServerContributions implements IServerContributions {
	
	private static Logger logger = LoggerFactory.getLogger(EmbeddedServerContributions.class);
	
	private static List<IServerExternalChangeListener> serverAddedRemovedExternallyListeners = 
		new LinkedList<IServerExternalChangeListener>();
	
	public EmbeddedServerContributions() {
	}
	

	@Override
	public void refreshServers() {
		EmbeddedServerManager.getInstance().refresh();
	}

	@Override
	public List<IDSServer> getServers() {
		List<IDSServer> servers = new ArrayList<IDSServer>();
		servers.addAll(EmbeddedServerManager.getInstance().getEmbeddedServers());
		return servers;
	}
	
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
		addAction.addAction(new AddEmbeddedServerAction());
	}
	
	@Override
	public void fillAddServerContextMenu(IDSServer server,	IAddAction addAction) {
		addAction.addAction(new AddEmbeddedServerAction());
	}

	@Override
	public void fillConfigureServerContextMenu(IDSServer server, IAddAction addAction) {
		if (server instanceof IEmbeddedServer) {
			Action showPreferencesAction = new ShowPreferencesAction("com.odcgroup.eds.prefs.servers.embedded");
			showPreferencesAction.setText("Open Server Preferences ...");
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

	@Override
	public void updateDeployedProjects(IDSServer server, IDSProject[] ocsProjects) {
		if (server instanceof IEmbeddedServer) {
			final IEmbeddedServer embeddedServer = (IEmbeddedServer) server;

			// Compute the list of added/removed project
			List<IDSProject> previouslySelectedProjects = server.getDsProjects();
			List<IDSProject> newlySelectedProjects = Arrays.asList(ocsProjects);
			final List<IDSProject> addedOcsProjects = new ArrayList<IDSProject>(newlySelectedProjects);
			addedOcsProjects.removeAll(previouslySelectedProjects);
			final List<IDSProject> removedOcsProjects = new ArrayList<IDSProject>(previouslySelectedProjects);
			removedOcsProjects.removeAll(newlySelectedProjects);

			WorkspaceJob job = new WorkspaceJob("Updating server build path") {
				@Override
				public IStatus runInWorkspace(IProgressMonitor monitor)
						throws CoreException {
					EmbeddedServerClasspathHelper.getInstance()
							.updateServerBuildPath(embeddedServer,
									addedOcsProjects, removedOcsProjects);
					return Status.OK_STATUS;
				}
			};
			job.setSystem(true);
			job.setRule(ResourcesPlugin.getWorkspace().getRoot());
			job.schedule();

			if (embeddedServer.getState() != IDSServerStates.STATE_STOPPED
					&& embeddedServer.getState() != IDSServerStates.STATE_NOT_CONFIGURED) {
				StringBuffer projectsList = new StringBuffer();
				for (int i = 0; i < addedOcsProjects.size(); i++) {
					projectsList.append(addedOcsProjects.get(i).getName());
					if (i < addedOcsProjects.size() - 1) {
						projectsList.append(", ");
					}
				}
				MessageDialog
						.openWarning(
								Display.getDefault().getActiveShell(),
								"Server Already Started",
								"The server is already started. Therefore, the newly added project(s) will be deployed at the next server startup.\n\n"
										+ "Projects: " + projectsList);
			}
		}
	}

	@Override
	public void start(IDSServer server, boolean debug) throws UnableToStartServerException {
		if (server instanceof IEmbeddedServer) {
			IEmbeddedServer embeddedServer = (IEmbeddedServer)server;
			if (!getHelper().checkProjectsConfiguration(embeddedServer)) {
				// Abort the start
				return;
			}
			checkReadyToStart(server);
			
			try {
				server.setState(debug?IDSServer.STATE_STARTING_DEBUG:IDSServer.STATE_STARTING);
				getHelper().start(embeddedServer, debug);
				OcsServerUICore.getDefault().getLogWatcherManager().startAllLogWatchers(server);
			} catch (UnableToStartServerException e) {
				server.setState(IDSServer.STATE_STOPPED);
				OcsServerUICore.getDefault().getLogWatcherManager().stopAllLogWatchers(false);
				throw e;
			}
		}
	}
	
	void checkReadyToStart(IDSServer server) throws UnableToStartServerException {
		List<String> emptyGenProjects = new ArrayList<String>();
		List<String> emptyModelsProjects = new ArrayList<String>();
		for (IDSProject ocsProject: server.getDsProjects()) {
			String projectName = ocsProject.getName();
			if (!projectName.endsWith("-models-gen")) {
				continue;
			}
			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
			try {
				IJavaProject javaProject = (IJavaProject) project
						.getNature(JavaCore.NATURE_ID);
				
				IProject modelsProject = ResourcesPlugin.getWorkspace().getRoot().getProject(StringUtils.removeEnd(projectName, "-gen"));
				if (!OfsCore.isOfsProject(modelsProject)) {
					continue;
				}
				
				boolean fileFoundInOutputFolder = false;
				
				for (String folder : getOutputFolder(javaProject)) {
					File absoluteFolder = new File(project.getLocation().toPortableString(), folder);
					if (absoluteFolder.list() != null && absoluteFolder.list().length > 0) {
						fileFoundInOutputFolder = true;
						break;
					}
				}
				if (!fileFoundInOutputFolder) {
					emptyGenProjects.add(projectName);
					emptyModelsProjects.add(modelsProject.getName());
				}
			} catch (CoreException e) {
				// Ignore project with nature problem (i.e. closed, non existing, ...)
			}
		}
		if (emptyGenProjects.size() > 0) {
			throw new UnableToStartServerException("Empty project(s) detected: " + StringUtils.join(emptyGenProjects, ", ") + "\n\n" +
						"Please generate the code of the following models project before starting the server: " + StringUtils.join(emptyModelsProjects, ", "));
		}
	}

	List<String> getOutputFolder(IJavaProject project) {
		List<String> folders = new ArrayList<String>();
		
		try {
			if (project.getOutputLocation() != null) {
				folders.add(StringUtils.removeStart(project.getOutputLocation().toFile().toString(), "\\" + project.getProject().getName() + "\\"));
			}
			
			for (IClasspathEntry entry : project.getResolvedClasspath(true)) {
				if (entry.getEntryKind() == IClasspathEntry.CPE_SOURCE) {
					if (entry.getOutputLocation() != null) {
						folders.add(StringUtils.removeStart(entry.getOutputLocation().toFile().toString(), "\\" + project.getProject().getName() + "\\"));
					}
				}
			}
		} catch (JavaModelException e) {
			// Ignore
		}
		
		return folders;
	}

	@Override
	public void stop(IDSServer server) throws UnableToStopServerException {
		if (server instanceof IEmbeddedServer) {
			IEmbeddedServer embeddedServer = (IEmbeddedServer)server;
			server.setState(IDSServer.STATE_STOPPED);
			getHelper().stop(embeddedServer);
			OcsServerUICore.getDefault().getLogWatcherManager().stopAllLogWatchers(false);
		}
	}

	/**
	 * Ease unit test
	 */
	protected EmbeddedServerLauncherHelper getHelper() {
		return EmbeddedServerLauncherHelper.getInstance();
	}

	@Override
	public void notifyProjectChanged(final IProject project, ChangeKind kind) {
		switch (kind) {
		case ADDED:
			if (EmbeddedServerManager.getInstance().isServerProject(project)) {
				boolean isAlreadyInServerList = false;
				for (IDSServer server : EmbeddedServerManager.getInstance().getServers()) {
					if (project.equals(server.getServerProject())) {
						isAlreadyInServerList = true;
						break;
					}
				}
				if (!isAlreadyInServerList) {
					addServer(project);
				}
			}
			break;
		case CHANGED:
			if (EmbeddedServerManager.getInstance().isServerProject(project)) {
				boolean isAlreadyInServerList = false;
				for (IDSServer server : EmbeddedServerManager.getInstance().getServers()) {
					if (project.equals(server.getServerProject())) {
						isAlreadyInServerList = true;
						break;
					}
				}
				if (!isAlreadyInServerList) {
					addServer(project);
				}
			} else {
				IDSServer serverFound = null;
				for (IDSServer server : EmbeddedServerManager.getInstance().getServers()) {
					if (project.equals(server.getServerProject())) {
						serverFound = server;
						break;
					}
				}
				if (serverFound != null) {
					removeServer(project, serverFound);
				}
			}
			break;
		case REMOVED:
			IDSServer serverFound = null;
			for (IDSServer server : EmbeddedServerManager.getInstance().getServers()) {
				if (project.equals(server.getServerProject())) {
					serverFound = server;
					break;
				}
			}
			if (serverFound != null) {
				removeServer(project, serverFound);
			}
			break;
		}
	}

	private void addServer(final IProject project) {
		Job newUpdateServerListJob = new Job("Update embedded servers list") {
			@Override
			public IStatus run(
					IProgressMonitor monitor) {
				// Embedded Server Project opened or created
				IDSServer newServer = EmbeddedServerManager.getInstance().addServer(project);
				notifyServerAddedExternally(newServer);
				return Status.OK_STATUS;
			}
		};
		newUpdateServerListJob.setPriority(Job.SHORT);
		newUpdateServerListJob.schedule();
	}
	
	private void removeServer(IProject project, IDSServer server) {
		EmbeddedServerManager.getInstance().removeServer(project);
		notifyServerRemovedExternally(server);
	}

}
