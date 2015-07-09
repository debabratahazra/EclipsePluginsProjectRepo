package com.odcgroup.t24.server.iris.ui;

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
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.server.model.IDSProject;
import com.odcgroup.server.model.IDSServer;
import com.odcgroup.server.model.IDSServerStates;
import com.odcgroup.server.ui.IServerContributions;
import com.odcgroup.server.ui.IServerExternalChangeListener;
import com.odcgroup.server.ui.UnableToStartServerException;
import com.odcgroup.server.ui.UnableToStopServerException;
import com.odcgroup.server.ui.views.IAddAction;
import com.odcgroup.t24.server.iris.model.IIrisEmbeddedServer;
import com.odcgroup.t24.server.iris.util.IrisServerClasspathHelper;
import com.odcgroup.t24.server.iris.util.IrisServerManager;
import com.odcgroup.workbench.core.OfsCore;


public class IrisServerContributions implements IServerContributions {
	
	private static List<IServerExternalChangeListener> serverAddedRemovedExternallyListeners = new LinkedList<IServerExternalChangeListener>();
	
	private static Logger logger = LoggerFactory.getLogger(IrisServerContributions.class);

	@Override
	public void refreshServers() {
		IrisServerManager.getInstance().refresh();
	}

	@Override
	public List<IDSServer> getServers() {
		List<IDSServer> servers = new ArrayList<IDSServer>();
		servers.addAll(IrisServerManager.getInstance().getIrisServers());
		return servers;
	}

	@Override
	public void notifyProjectChanged(IProject project, ChangeKind kind) {
		switch (kind) {
		case ADDED:
			if (IrisServerManager.getInstance().isServerProject(project)) {
				boolean isAlreadyInServerList = false;
				for (IDSServer server : IrisServerManager.getInstance().getServers()) {
					if (project.equals(server.getServerProject())) {
						isAlreadyInServerList = true;
						break;
					}
				}
				if (!isAlreadyInServerList) {
					addIrisServer(project);
				}
			}
			break;
		case CHANGED:
			if (IrisServerManager.getInstance().isServerProject(project)) {
				boolean isAlreadyInServerList = false;
				for (IDSServer server : IrisServerManager.getInstance().getServers()) {
					if (project.equals(server.getServerProject())) {
						isAlreadyInServerList = true;
						break;
					}
				}
				if (!isAlreadyInServerList) {
					addIrisServer(project);
				}
			} else {
				IDSServer serverFound = null;
				for (IDSServer server : IrisServerManager.getInstance().getServers()) {
					if (project.equals(server.getServerProject())) {
						serverFound = server;
						break;
					}
				}
				if (serverFound != null) {
					removeIrisServer(project, serverFound);
				}
			}
			break;
		case REMOVED:
			IDSServer serverFound = null;
			for (IDSServer server : IrisServerManager.getInstance().getServers()) {
				if (project.equals(server.getServerProject())) {
					serverFound = server;
					break;
				}
			}
			if (serverFound != null) {
				IrisServerManager.getInstance().removeServer(project);
				notifyServerRemovedExternally(serverFound);
			}
			break;
		}

	
		
	}

	/**
	 * @param project
	 * @param serverFound
	 */
	private void removeIrisServer(IProject project, IDSServer server) {
		IrisServerManager.getInstance().removeServer(project);
		notifyServerRemovedExternally(server);
		
	}

	/**
	 * @param serverFound
	 */
	public void notifyServerRemovedExternally(IDSServer server) {
		for (IServerExternalChangeListener listener : serverAddedRemovedExternallyListeners) {
			try {
				listener.serverRemovedExternally(server);
			} catch (RuntimeException e) {
				logger.error("Unexpected error while calling listener", e);
			}
		}
	}

	/**
	 * @param server
	 */
	public void notifyServerAddedExternally(IDSServer server) {
		for (IServerExternalChangeListener listener : serverAddedRemovedExternallyListeners) {
			try {
				listener.serverAddedExternally(server);
			} catch (RuntimeException e) {
				logger.error("Unexpected error while calling listener", e);
			}
		}
	}

	/**
	 * @param project
	 */
	private void addIrisServer(final IProject project) {

		Job newUpdateServerListJob = new Job("Update embedded servers list") {
			@Override
			public IStatus run(
					IProgressMonitor monitor) {
				// Embedded Server Project opened or created
				IDSServer newServer = IrisServerManager.getInstance().addServer(project);
				notifyServerAddedExternally(newServer);
				return Status.OK_STATUS;
			}
		};
		newUpdateServerListJob.setPriority(Job.SHORT);
		newUpdateServerListJob.schedule();
	
		
	}

	@Override
	public void addListenerServerAddedRemovedExternally(IServerExternalChangeListener listener) {
		serverAddedRemovedExternallyListeners.add(listener);
	}

	@Override
	public void fillAddServerToolbarMenu(IAddAction addAction) {
		addAction.addAction(new AddT24IRISServerAction());
	}

	@Override
	public void fillAddServerContextMenu(IDSServer server, IAddAction addAction) {
		addAction.addAction(new AddT24IRISServerAction());
	}

	@Override
	public void fillConfigureServerContextMenu(IDSServer server, IAddAction addAction) {
		// TODO Auto-generated method stub
	}

	@Override
	public void updateDeployedProjects(IDSServer server, IDSProject[] projects) {

		if (server instanceof IIrisEmbeddedServer) {
			final IIrisEmbeddedServer irisServer = (IIrisEmbeddedServer) server;

			// Compute the list of added/removed project
			List<IDSProject> previouslySelectedProjects = server.getDsProjects();
			List<IDSProject> newlySelectedProjects = Arrays.asList(projects);
			final List<IDSProject> addedDsProjects = new ArrayList<IDSProject>(newlySelectedProjects);
			addedDsProjects.removeAll(previouslySelectedProjects);
			final List<IDSProject> removedDsProjects = new ArrayList<IDSProject>(previouslySelectedProjects);
			removedDsProjects.removeAll(newlySelectedProjects);

			WorkspaceJob job = new WorkspaceJob("Updating iris server build path") {
				@Override
				public IStatus runInWorkspace(IProgressMonitor monitor)
						throws CoreException {
					IrisServerClasspathHelper.getInstance()
							.updateServerBuildPath(irisServer,
									addedDsProjects, removedDsProjects);
					return Status.OK_STATUS;
				}
			};
			job.setSystem(true);
			job.setRule(ResourcesPlugin.getWorkspace().getRoot());
			job.schedule();

			if (irisServer.getState() != IDSServerStates.STATE_STOPPED
					&& irisServer.getState() != IDSServerStates.STATE_NOT_CONFIGURED) {
				StringBuffer projectsList = new StringBuffer();
				for (int i = 0; i < addedDsProjects.size(); i++) {
					projectsList.append(addedDsProjects.get(i).getName());
					if (i < addedDsProjects.size() - 1) {
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
		if (server instanceof IIrisEmbeddedServer) {
			IIrisEmbeddedServer irisServer = (IIrisEmbeddedServer)server;
			if (!getHelper().checkProjectsConfiguration(irisServer)) {
				// Abort the start
				return;
			}
			checkReadyToStart(server);
			
			try {
				server.setState(debug?IDSServer.STATE_STARTING_DEBUG:IDSServer.STATE_STARTING);
				IStatus iStatus = getHelper().start(irisServer, debug);
				if (iStatus == Status.OK_STATUS) {
					server.setState(debug ? IDSServer.STATE_STARTED_DEBUG : IDSServer.STATE_STARTED);
				}
			} catch (UnableToStartServerException e) {
				server.setState(IDSServer.STATE_STOPPED);
				throw e;
			}
		}
	}

	/**
	 * @param server
	 */
	private void checkReadyToStart(IDSServer server) throws UnableToStartServerException {

		List<String> emptyGenProjects = new ArrayList<String>();
		List<String> emptyModelsProjects = new ArrayList<String>();
		for (IDSProject irisProject: server.getDsProjects()) {
			String projectName = irisProject.getName();
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
						"Please generate the code of the following models project before starting the server: " + StringUtils.join(emptyModelsProjects.toArray()));
		}
	
		
	}

	/**
	 * @param javaProject
	 * @return
	 */
	private List<String> getOutputFolder(IJavaProject javaProject) {
		List<String> folders = new ArrayList<String>();

		try {
			if (javaProject.getOutputLocation() != null) {
				folders.add(StringUtils.removeStart(javaProject.getOutputLocation().toFile().toString(), "\\"
						+ javaProject.getProject().getName() + "\\"));
			}

			for (IClasspathEntry entry : javaProject.getResolvedClasspath(true)) {
				if (entry.getEntryKind() == IClasspathEntry.CPE_SOURCE) {
					if (entry.getOutputLocation() != null) {
						folders.add(StringUtils.removeStart(entry.getOutputLocation().toFile().toString(), "\\"
								+ javaProject.getProject().getName() + "\\"));
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
		if (server instanceof IIrisEmbeddedServer) {
			IIrisEmbeddedServer embeddedServer = (IIrisEmbeddedServer)server;
			server.setState(IDSServer.STATE_STOPPED);
			getHelper().stop(embeddedServer);			
		}
		
	}
	
	protected IrisServerLauncherHelper getHelper() {
		return IrisServerLauncherHelper.getInstance();
	}
	
}
