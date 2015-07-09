package com.odcgroup.service.model.init;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import com.odcgroup.workbench.core.init.DefaultModelProjectInitializer;

public class ServiceModelProjectInitializer extends DefaultModelProjectInitializer {
	private static String SERVICE_FILE_EXTENSION = "component";

	/**
	 * 
	 */
	public ServiceModelProjectInitializer() {
		super(SERVICE_FILE_EXTENSION);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.core.init.DefaultModelProjectInitializer#updateConfiguration(org.eclipse.core.resources.IProject, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void updateConfiguration(IProject project, IProgressMonitor monitor)
			throws CoreException {
		super.updateConfiguration(project, monitor);
		IFolder serviceFolder = project.getFolder(SERVICE_FILE_EXTENSION);
		IFolder defaultFolder = serviceFolder.getFolder("Default");
		if (!defaultFolder.exists())
			defaultFolder.create(true, true, monitor);
	}
}
