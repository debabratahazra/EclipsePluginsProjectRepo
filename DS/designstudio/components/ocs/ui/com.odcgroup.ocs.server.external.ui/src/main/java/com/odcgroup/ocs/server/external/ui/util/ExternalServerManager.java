package com.odcgroup.ocs.server.external.ui.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;

import com.odcgroup.ocs.server.ServerCore;
import com.odcgroup.ocs.server.external.model.IExternalServer;
import com.odcgroup.ocs.server.external.model.internal.ExternalServer;
import com.odcgroup.ocs.server.external.ui.builder.PrepareUndeploymentFacade;
import com.odcgroup.server.model.IDSProject;
import com.odcgroup.server.model.IDSServer;
import com.odcgroup.server.model.IDSServerStates;
import com.odcgroup.server.server.IDSServerStateChangeListener;

/**
 * Manage of external server
 */
public class ExternalServerManager {

	private static IExternalServer externalServer;

	private static List<IProject> projectsToBeRemovedWhenServerStopped = new ArrayList<IProject>();

	public static void reset() {
		initiliazeExternalServer();
	}

	public static void initiliazeExternalServer() {
		externalServer = new ExternalServer("com.odcgroup.ocs.server.classical", "External Server (WebLogic)");
		List<IDSProject> projects = ServerCore.getDefault().getOCSServerPreferenceManager().getDeployedProjects();
		for (IDSProject project : projects) {
			externalServer.addDsProject(project);
		};
		externalServer.addServerStateChangeListener(new IDSServerStateChangeListener() {
			@Override
			public void serverStateChanged(IDSServer server) {
				if (server.getState() == IDSServerStates.STATE_STOPPED) {
					processLateUndeployment();
				}
			}
		});
	}

	/**
	 * @param preferenceManager
	 * @return
	 */
	public static IExternalServer getExternalServer() {
		return externalServer;
	}

	public static void registerProjectForUndeployment(IProject project) {
		getProjectsToBeRemovedWhenServerStopped().add(project);
	}

	public static void processLateUndeployment() {
		for (IProject project: getProjectsToBeRemovedWhenServerStopped()) {
			PrepareUndeploymentFacade.undeployProject(project);
		}
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
}
