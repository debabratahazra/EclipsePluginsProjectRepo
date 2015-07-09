package com.odcgroup.t24.server.iris.nature;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

public class IrisEmbeddedServerNature implements IProjectNature {
	
	public static final String NATURE_ID = "com.odcgroup.t24.server.iris.embedded";
	private IProject project;

	@Override
	public void configure() throws CoreException {
	}

	@Override
	public void deconfigure() throws CoreException {
	}

	@Override
	public IProject getProject() {
		return project;
	}

	@Override
	public void setProject(IProject project) {
		this.project = project;
	}
}
