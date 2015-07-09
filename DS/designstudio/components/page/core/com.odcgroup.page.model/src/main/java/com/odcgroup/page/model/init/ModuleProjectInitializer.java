package com.odcgroup.page.model.init;

import static com.odcgroup.page.common.PageConstants.MODULE_FILE_EXTENSION;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;


/**
 * Initializes the directory structure for the artifacts specific to modules.
 * 
 * @author Gary Hayes, atr
 */
public class ModuleProjectInitializer extends PageDesignerProjectInitializer {
	
	/**
	 * Creates a new ModuleProjectInitializer. 
	 */	
	public ModuleProjectInitializer() {
		super(MODULE_FILE_EXTENSION);
	}
	
	/**
	 * DS-2711 default packages for module folder Default/module 
	 * @see com.odcgroup.workbench.core.init.DefaultModelProjectInitializer#updateConfiguration(org.eclipse.core.resources.IProject, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void updateConfiguration(IProject project, IProgressMonitor monitor) throws CoreException {
		super.updateConfiguration(project, monitor);
		IFolder pageFolder = project.getFolder(MODULE_FILE_EXTENSION);
		IFolder defaultFolder = pageFolder.getFolder("Default");
		if(!defaultFolder.exists()) defaultFolder.create(true, true, monitor);
		IFolder module = defaultFolder.getFolder("module");
		if(!module.exists()) module.create(true, true, monitor);
	}

}
