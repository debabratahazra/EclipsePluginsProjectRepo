package com.odcgroup.workbench.ui.internal.markers;

import net.sf.eclipsecs.core.nature.CheckstyleNature;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.JavaCore;



import com.odcgroup.workbench.ui.OfsUICore;

/**
 * This resource change listener listens for changes on .project files
 * on Java projects in order to determine whether Checkstyle and FindBugs
 * are activated on this project.
 *
 * @author mmu
 *
 */
public class ProjectResourceListener implements IResourceChangeListener {

	protected static final String FINDBUGS_NATURE_ID = "edu.umd.cs.findbugs.plugin.eclipse.findbugsNature";

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResourceChangeListener#resourceChanged(org.eclipse.core.resources.IResourceChangeEvent)
	 */
	public void resourceChanged(IResourceChangeEvent event) {
		if(event.getDelta()==null) return;
		if(event.getDelta().getResource() instanceof IWorkspaceRoot) {
			for(IResourceDelta delta : event.getDelta().getAffectedChildren()) {
				if(delta.getResource() instanceof IProject) {
					IResourceDelta projectDelta = delta.findMember(new Path(".project"));
					if(projectDelta==null) continue;
					IResource res = projectDelta.getResource();
					if(res==null) return;
					final IProject project = res.getProject();
					
					Job job = new Job("Design Studio Java Project Configuration Check") {
						protected IStatus run(IProgressMonitor monitor) {
							try {
								if(project.isOpen()) {
									if(project.hasNature(JavaCore.NATURE_ID) &&
											!project.getName().endsWith("-models-gen")) {
										project.deleteMarkers(OfsUICore.JAVA_MARKER_ID, false, IResource.DEPTH_ZERO);
										if(!project.hasNature(CheckstyleNature.NATURE_ID)) {
											addMarker(project, "CheckStyle is not activated on this project!");
										}
										if(!project.hasNature(FINDBUGS_NATURE_ID)) {
											addMarker(project, "FindBugs is not activated on this project!");
										}
									}
								}
							} catch (CoreException e) {
								OfsUICore.getDefault().logWarning("Cannot determine natures of project " + project.getName(), e);
							}
							return Status.OK_STATUS;
						}
					};
					job.setPriority(Job.SHORT);
					job.setRule(project);
					job.schedule();
				}
			}
		}
	}

	private void addMarker(IProject project, String message) {
		try {
			IMarker marker = project.createMarker(OfsUICore.JAVA_MARKER_ID);
			marker.setAttribute(IMarker.MESSAGE, message);
			marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_WARNING);
		} catch (CoreException e) {
			OfsUICore.getDefault().logWarning("Marker for project does not exist anymore", e);
		}
	}

}
