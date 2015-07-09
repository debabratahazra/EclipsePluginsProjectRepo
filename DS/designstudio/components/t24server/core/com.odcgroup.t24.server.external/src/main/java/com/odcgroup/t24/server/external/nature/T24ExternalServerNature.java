package com.odcgroup.t24.server.external.nature;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

public class T24ExternalServerNature implements IProjectNature {

	public static final String NATURE_ID = "com.odcgroup.t24.server.t24server";
	
	@Override
	public void configure() throws CoreException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deconfigure() throws CoreException {
		// TODO Auto-generated method stub

	}

	@Override
	public IProject getProject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setProject(IProject project) {
		// TODO Auto-generated method stub

	}

}
