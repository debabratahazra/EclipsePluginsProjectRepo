package com.odcgroup.ocs.server.embedded.nature;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

public class EmbeddedServerNature implements IProjectNature {
	
	public static final String NATURE_ID = "com.odcgroup.ocs.server.embeddedserver";

	private IProject project;

	public void configure() throws CoreException {
	}

	public void deconfigure() throws CoreException {
	}

	public IProject getProject() {
		return project;
	}

	public void setProject(IProject project) {
		this.project = project;
	}

}
