package com.odcgroup.workbench.core.init;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;

import com.odcgroup.workbench.core.OfsCore;

abstract public class AbstractProjectInitializer implements ProjectInitializer {

	protected String markerId = OfsCore.MARKER_ID;

	public boolean canBeInitialized(IProject project) {
		return true;
	}

	public IStatus checkConfiguration(IProject project) {
		return checkConfiguration(project, null);
	}

	public IStatus checkConfiguration(IProject project, IResourceDelta delta) {
		return new MultiStatus(OfsCore.PLUGIN_ID, IStatus.OK, "Project initialization validation results", null);
	}

	public void initialize(IProject project, boolean force,
			IProgressMonitor monitor) throws CoreException {
		if(force || canBeInitialized(project)) {
			updateConfiguration(project, monitor);
		}
	}

	abstract public void updateConfiguration(IProject project, IProgressMonitor monitor)
			throws CoreException;
		
	public String getMarkerId() {
		return markerId;
	}

	public void setMarkerId(String markerId) {
		this.markerId = markerId;		
	}	
	
	public IProject[] getProjectsToWatch(IProject project) {
		return new IProject[0];
	}

}
