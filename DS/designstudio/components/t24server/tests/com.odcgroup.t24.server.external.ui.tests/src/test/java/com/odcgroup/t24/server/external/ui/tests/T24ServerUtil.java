package com.odcgroup.t24.server.external.ui.tests;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;

import com.odcgroup.t24.server.external.ui.AddT24ExternalServerAction;

public class T24ServerUtil {

	public static IProject createServer(String serverName) throws Exception {
		IProject serverProject = createServerProject(serverName);
		AddT24ExternalServerAction addExternalServer = new AddT24ExternalServerAction();
		BasicNewProjectResourceWizard wizard = new BasicNewProjectResourceWizard();
		addExternalServer.setServerProject(serverProject);
		addExternalServer.createProject(wizard);
		return serverProject;
	}

	private static IProject createServerProject(String projectName) throws Exception {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		project.create(null);
		project.open(null);
		return project;
	}

}
