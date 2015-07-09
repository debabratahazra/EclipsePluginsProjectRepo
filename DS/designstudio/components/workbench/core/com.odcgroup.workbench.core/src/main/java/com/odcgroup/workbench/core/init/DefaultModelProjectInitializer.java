package com.odcgroup.workbench.core.init;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;

import com.odcgroup.workbench.core.OfsCore;

public class DefaultModelProjectInitializer extends AbstractProjectInitializer {
	
	private String modelName;
	
	public DefaultModelProjectInitializer(String modelName) {
		assert modelName != null;
		assert modelName.length()>0;
		
		this.modelName = modelName;
	}

	public void initialize(IProject project, boolean force, IProgressMonitor monitor) throws CoreException {
		assert project!=null;

		if(!force 
			&& project.getFile(modelName).exists()) {
				throw new CoreException(
						new Status(IStatus.ERROR, OfsCore.PLUGIN_ID,
								"Folder '" + modelName + "' already exists."));
		} else {
			updateConfiguration(project, null);
		}
	}

	public IStatus checkConfiguration(IProject project) {
		assert project!=null;
		
		MultiStatus status = new MultiStatus(OfsCore.PLUGIN_ID, IStatus.OK, "Errors in configuration", null);
		if(!project.getFolder(modelName).exists()) {
			status.add(
					new Status(IStatus.ERROR, OfsCore.PLUGIN_ID, 
							"Model folder '" + modelName + "' does not exist."));
		}
		
		return status;
	}

	public void updateConfiguration(IProject project, IProgressMonitor monitor) throws CoreException {
		assert project!=null;

		if(modelName != null && modelName.length()>0) {
			IFolder folder = project.getFolder(modelName);
			if(!folder.exists()) folder.create(true, true, monitor);
		}
	}

}
