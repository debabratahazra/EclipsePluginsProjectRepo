package com.odcgroup.workbench.ui.internal.builder;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.xtext.builder.IXtextBuilderParticipant;
import org.eclipse.xtext.ui.XtextProjectHelper;

import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.ui.OfsUICore;

/**
 * Builder participant that checks if the xtext builder/nature is added to the *-models-gen project.
 * 
 * @author vramya
 * 
 */
public class OfsXtextBuilderParticipant implements IXtextBuilderParticipant {

	@Override
	public void build(IBuildContext context, IProgressMonitor monitor) throws CoreException {
		IProject project = context.getBuiltProject();
		String projectName = project.getName();
		if (projectName.endsWith("models-gen")) {
			checkProjectConfigurations(project, monitor);
		}
	}
	
	/**
	 * Method to check if *-models-gen project as xtext builder/nature
	 * 
	 * @param project
	 * @param monitor
	 * @throws CoreException
	 */
	private void checkProjectConfigurations(final IProject project, IProgressMonitor monitor) throws CoreException {
		ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) throws CoreException {
				deleteMarker(project, OfsCore.MARKER_ID);

				monitor.beginTask("Checking project configuration...", 1);
				if (project.hasNature(XtextProjectHelper.NATURE_ID)) {
					IStatus error = new Status(IStatus.ERROR, OfsUICore.PLUGIN_ID,
							"*-models-gen projects must not have Xtext nature.");
					addMarker(project, "Design Studio project configuration: " + error.getMessage(),
							mapSeverity(error.getSeverity()), OfsCore.MARKER_ID);
				}
				monitor.done();
			}

		}, project, IWorkspace.AVOID_UPDATE, monitor);
	}

	/**
	 * Makes an entry in problems view.
	 * 
	 * @param project
	 * @param message
	 * @param severity
	 * @param markerId
	 * @throws CoreException
	 */
	private void addMarker(final IProject project, final String message, final int severity, final String markerId)
			throws CoreException {
		IMarker marker = project.createMarker(markerId);
		marker.setAttribute(IMarker.MESSAGE, message);
		marker.setAttribute(IMarker.SEVERITY, severity);
	}

	/**
	 * Deletes an entry from problems view.
	 * 
	 * @param project
	 * @param markerId
	 */
	private void deleteMarker(IProject project, String markerId) {
		try {
			project.deleteMarkers(markerId, false, IResource.DEPTH_ZERO);
		} catch (CoreException ce) {
			OfsUICore.getDefault().logWarning("Could not delete markers of project " + project.getName(), ce);
		}
	}

	private int mapSeverity(int severity) {
		switch (severity) {
		case IStatus.ERROR:
			return IMarker.SEVERITY_ERROR;
		case IStatus.WARNING:
			return IMarker.SEVERITY_WARNING;
		case IStatus.INFO:
			return IMarker.SEVERITY_INFO;
		}
		return IMarker.SEVERITY_INFO;
	}

}
