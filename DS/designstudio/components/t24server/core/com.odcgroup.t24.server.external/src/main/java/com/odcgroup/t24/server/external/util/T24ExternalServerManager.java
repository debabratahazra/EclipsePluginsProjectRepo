package com.odcgroup.t24.server.external.util;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

import com.odcgroup.server.model.IDSProject;
import com.odcgroup.server.model.IDSServer;
import com.odcgroup.server.model.IDSServerStates;
import com.odcgroup.server.model.impl.DSProject;
import com.odcgroup.server.util.AbstractServerManager;
import com.odcgroup.t24.server.external.model.IExternalServer;
import com.odcgroup.t24.server.external.model.internal.ExternalT24Server;
import com.odcgroup.t24.server.external.nature.T24ExternalServerNature;

/**
 * Manage of external server
 */
public class T24ExternalServerManager extends AbstractServerManager implements IExternalServerManager  {
	
	
	static T24ExternalServerManager t24ExternalServerManager = new T24ExternalServerManager();
	
	public static IExternalServerManager getInstance() {
		return t24ExternalServerManager;
	}
	
	protected T24ExternalServerManager() {
	}

	private static List<IProject> projectsToBeRemovedWhenServerStopped = new ArrayList<IProject>();

	public static void initiliazeExternalServer() {
		if (T24ExternalServerManager.getInstance().getExternalServers()!=null) {
			for (IExternalServer iExternalServer : T24ExternalServerManager.getInstance().getExternalServers()) {
				List<IDSProject> projects = iExternalServer.getDeployedProjects();
				for (IDSProject project : projects) {
					iExternalServer.addDsProject(project);
				}
			}
		}
	}

	/**
	 * @param preferenceManager
	 * @return
	 */
	public static IExternalServer getExternalServer() {
		for (IExternalServer iExternalServer : T24ExternalServerManager
				.getInstance().getExternalServers()) {
			if (iExternalServer.getState() == IDSServerStates.STATE_ACTIVE
					|| iExternalServer.getState() == IDSServerStates.STATE_ACTIVE_IN_DEBUG) {
				return iExternalServer;
			}
		}
		return null;
	}

	public static void registerProjectForUndeployment(IProject project) {
		getProjectsToBeRemovedWhenServerStopped().add(project);
	}

	public static void processLateUndeployment() {
		getProjectsToBeRemovedWhenServerStopped().clear();
	}

	/**
	 * @return the projectsToBeRemovedWhenServerStopped
	 */
	protected static List<IProject> getProjectsToBeRemovedWhenServerStopped() {
		return projectsToBeRemovedWhenServerStopped;
	}

	public static void resestProjectsToBeRemovedWhenServerStopped() {
		projectsToBeRemovedWhenServerStopped.clear();
	}
	
	public IDSServer newServer(IProject project) {
		Object[] projArg = {project.getName()};
		MessageFormat serverId = new MessageFormat("com.odcgroup.t24.server.{0}" );
		MessageFormat serverProjectName = new MessageFormat("T24 Connection ( {0} )" );
		return new ExternalT24Server(serverId.format(projArg), serverProjectName.format(projArg), project);
	}
	
	public boolean isServerProject(IProject project) {
		boolean hasServerNature;
		try {
			hasServerNature = project.hasNature(T24ExternalServerNature.NATURE_ID);
		} catch (CoreException e) {
			hasServerNature = false;
		}
		return hasServerNature;
	}

	@Override
	public List<IExternalServer> getExternalServers() {
		synchronized (T24ExternalServerManager.class) {
			List<IExternalServer> externalT24Servers = new ArrayList<IExternalServer>();
			for (IDSServer server : getServers()) {
				externalT24Servers.add((IExternalServer)server);
			}
			return externalT24Servers;
		}
	}

	@Override
	public void createDsProjects(IDSServer server) {

		List<IDSProject> oldDsProjects = new ArrayList<IDSProject>(server.getDsProjects());
		if (!oldDsProjects.isEmpty() && oldDsProjects.size() > 0) {
			server.updateProjectList(oldDsProjects
					.toArray(new DSProject[oldDsProjects.size()]));
		}
	
	}

}
