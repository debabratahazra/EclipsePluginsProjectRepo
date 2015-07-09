package com.odcgroup.t24.common.importer.tests;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.JavaCore;

import com.odcgroup.t24.server.external.model.internal.ExternalT24Server;
import com.odcgroup.t24.server.external.nature.T24ExternalServerNature;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

public abstract class T24ImporterTest extends AbstractDSUnitTest {

	protected IProject createProject(String projectName, String... additionalNatures) throws CoreException {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		project.create(null);
		project.open(null);
		addProjectNatures(project, additionalNatures);
		return project;
	}

	protected IProject createServerProject(String name) throws CoreException {
		IProject serverProject = createProject(name, T24ExternalServerNature.NATURE_ID);
		return serverProject;
	}
	
	protected void createServerConfig(IProject serverProject, boolean web) throws CoreException {
		mkdirs(serverProject.getFolder(new Path("/config")));
		IFile file = serverProject.getFile(ExternalT24Server.SERVER_PROPERTIES_PATH);

		StringBuilder sb = new StringBuilder();
		if (web) {
			// t24 web services
			sb.append("host=vmcmtdst24.oams.com\n");
			sb.append("username=INPUTT\n");
			sb.append("password=123456\n");
			sb.append("ws.port=9089\n");
			sb.append("protocol=ws\n");
		} else {
			// standard t24 server
			sb.append("host=vmcmtdst24.oams.com\n");
			sb.append("username=INPUTT\n");
			sb.append("password=123456\n");
			sb.append("agent.port=21001\n");
			sb.append("t24username=INPUTT\n");
			sb.append("t24password=123456\n");
			sb.append("deployed.projects=default-models-gen\n");
			sb.append("t24catalogserviceversion=2");

		}
		InputStream source = new ByteArrayInputStream(sb.toString().getBytes());
		file.create(source, true, null);
	}
	
	public void addProjectNatures(IProject project ,String... additionalNatures) throws CoreException{
		IProjectDescription description = project.getDescription();
		String[] natures = description.getNatureIds();
		String[] newNatures = new String[natures.length + additionalNatures.length + 1];
		System.arraycopy(natures, 0, newNatures, 0, natures.length);
		System.arraycopy(additionalNatures, 0, newNatures, natures.length, additionalNatures.length);
		newNatures[newNatures.length - 1] = JavaCore.NATURE_ID;
		description.setNatureIds(newNatures);
		project.setDescription(description, null);
	}

}
