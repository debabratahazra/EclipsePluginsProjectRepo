package com.odcgroup.edge.t24ui.model.ui;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import com.odcgroup.workbench.core.init.DefaultModelProjectInitializer;

public class CosProjectInitializer extends DefaultModelProjectInitializer {

	public static final java.lang.String ESON_FILE_EXTENSION = "eson";
	
	/**
	 * @param modelName
	 */
	public CosProjectInitializer() {
		super(ESON_FILE_EXTENSION);
	}
	
	@Override
	public void updateConfiguration(IProject project, IProgressMonitor monitor) throws CoreException {
		super.updateConfiguration(project, monitor);
		IFolder pageFolder = project.getFolder(ESON_FILE_EXTENSION);
		IFolder defaultFolder = pageFolder.getFolder("COS");
		if(!defaultFolder.exists()) defaultFolder.create(true, true, monitor);
		IFolder activity = defaultFolder.getFolder("eson");
		if(!activity.exists()) activity.create(true, true, monitor);
		project.getNature("org.eclipse.xtext.ui.shared.xtextNature");
		
	}

}
