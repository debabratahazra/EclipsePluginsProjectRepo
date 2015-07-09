package com.odcgroup.iris.model;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import com.odcgroup.workbench.core.init.DefaultModelProjectInitializer;


/**
 * Initializes the directory structure for the artifacts specific to rim models.
 */
public class RimProjectInitializer extends DefaultModelProjectInitializer {
	
	public static String RIM_FILE_EXTENSION = "rim";
	
	/**
	 * Creates a new ModuleProjectInitializer. 
	 */	
	public RimProjectInitializer() {
		super(RIM_FILE_EXTENSION);
	}
	
	@Override
	public void updateConfiguration(IProject project, IProgressMonitor monitor) throws CoreException {
		super.updateConfiguration(project, monitor);
		IFolder pageFolder = project.getFolder(RIM_FILE_EXTENSION);
		IFolder defaultFolder = pageFolder.getFolder("rim");
		if(!defaultFolder.exists()) defaultFolder.create(true, true, monitor);
	}

}
