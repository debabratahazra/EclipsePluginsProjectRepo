package com.odcgroup.ocs.support.m2eclipse.migration;

import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import com.odcgroup.workbench.tap.validation.ValidationCore;

public class OldM2EclipseMigrationBuilder extends IncrementalProjectBuilder {
	
	public static final String MARKER_ID = "com.odcgroup.ocs.support.ui.mavenProblem";
	
	@Override
	protected IProject[] build(int kind, Map<String, String> args, IProgressMonitor monitor) {
        IMarker marker = null;
        try {
        	if (getProject().findMarkers(MARKER_ID, false, IResource.DEPTH_INFINITE).length == 0) {
	            marker = getProject().createMarker(MARKER_ID);
	
	            if (marker.exists()) {
	                marker.setAttribute(IMarker.MESSAGE, "Project requires m2eclipse migration.");
	                marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
	                marker.setAttribute(IMarker.LOCATION, getProject().getFile(".project").getFullPath().toString().substring(1));
	            }
    		}
        } catch (CoreException e) {
        	ValidationCore.getDefault().logError(e.getMessage(), e);
        }

		return null;
	}

	@Override
	protected void clean(IProgressMonitor monitor) {
        try {
	    	for (IMarker marker : getProject().findMarkers(MARKER_ID, false, IResource.DEPTH_INFINITE)) {
	    		marker.delete();
	    	}
        } catch (CoreException e) {
        	ValidationCore.getDefault().logError(e.getMessage(), e);
        }
	}
	
}
