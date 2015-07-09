package com.odcgroup.ocs.packager.nature;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;


public class PackagerProjectNature implements IProjectNature {

	public static final String NATURE_ID =  "com.odcgroup.ocs.packager";

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
