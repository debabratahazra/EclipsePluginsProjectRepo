package com.odcgroup.menu.model.init;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import com.odcgroup.workbench.core.init.DefaultModelProjectInitializer;

/**
 * @author scn
 *
 */
public class MenuModelProjectInitializer extends DefaultModelProjectInitializer {

	private static String MENU_FILE_EXTENSION = "menu";

	/**
	 * 
	 */
	public MenuModelProjectInitializer() {
		super(MENU_FILE_EXTENSION);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.core.init.DefaultModelProjectInitializer#updateConfiguration(org.eclipse.core.resources.IProject, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void updateConfiguration(IProject project, IProgressMonitor monitor)
			throws CoreException {
		super.updateConfiguration(project, monitor);
		IFolder menuFolder = project.getFolder(MENU_FILE_EXTENSION);
		IFolder defaultFolder = menuFolder.getFolder("Default");
		if (!defaultFolder.exists())
			defaultFolder.create(true, true, monitor);
	}

}

