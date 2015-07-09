package com.odcgroup.t24.packager.nature;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

/**
 * TODO: Document me!
 *
 * @author RAMAPRIYAMN
 *
 */
public class TafcPackagerProjectNature implements IProjectNature {

	public static final String NATURE_ID =  "com.odcgroup.tafc.packager";

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
