package com.odcgroup.page.model.init;

import static com.odcgroup.page.common.PageConstants.PAGE_FILE_EXTENSION;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;


/**
 * Initializes the directory structure for the artifacts specific to pages.
 * 
 * @author atr
 */
public class PageProjectInitializer extends PageDesignerProjectInitializer {

	/**
	 * Creates a new PageProjectInitializer. 
	 */
	public PageProjectInitializer() {
		super(PAGE_FILE_EXTENSION);
	}
	
//	/**
//	 * @see com.odcgroup.page.ui.workbench.PageDesignerProjectInitializer#checkConfiguration(org.eclipse.core.resources.IProject)
//	 */
//	public IStatus checkConfiguration(IProject project) {
//		
//		final IOfsProject ofsProject = OfsCore.getOfsProjectManager().getOfsProject(project);
//
//		if(ofsProject==null) return Status.OK_STATUS;
//
//		return super.checkConfiguration(project);
//	}
	
	/**
	 * DS-2711 default packages for page folder Default/activity
	 * @see com.odcgroup.workbench.core.init.DefaultModelProjectInitializer#updateConfiguration(org.eclipse.core.resources.IProject, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void updateConfiguration(IProject project, IProgressMonitor monitor) throws CoreException {
		super.updateConfiguration(project, monitor);
		IFolder pageFolder = project.getFolder(PAGE_FILE_EXTENSION);
		IFolder defaultFolder = pageFolder.getFolder("Default");
		if(!defaultFolder.exists()) defaultFolder.create(true, true, monitor);
		IFolder activity = defaultFolder.getFolder("activity");
		if(!activity.exists()) activity.create(true, true, monitor);
	}
	
}
