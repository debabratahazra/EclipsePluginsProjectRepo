package com.odcgroup.workbench.core.internal.builder;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.URI;

import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.init.ProjectInitializer;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.core.repository.ModelURIConverter;

public class OfsBuilder extends IncrementalProjectBuilder {
	
//	private class ResourceDeltaVisitor implements IResourceDeltaVisitor {
//		private void dump(IResourceDelta delta) {
//			int kind = delta.getKind();
//			int flags = delta.getFlags();
//			StringBuffer   sb = new StringBuffer  ();
//	     
//			sb.append(delta.getResource().getFullPath());
//	     
//			switch(kind) {
//			case IResourceDelta.NO_CHANGE:
//				sb.append(" NO_CHANGE");
//				break;
//			case IResourceDelta.ADDED:
//	         sb.append(" ADDED");
//	         break;
//	       case IResourceDelta.REMOVED:
//	         sb.append(" REMOVED");
//	         break;
//	       case IResourceDelta.CHANGED:
//	         sb.append(" CHANGED");
//	         break;
//	       case IResourceDelta.ADDED_PHANTOM:
//	         sb.append(" ADDED_PHANTOM");
//	         break;
//	       case IResourceDelta.REMOVED_PHANTOM:
//	         sb.append(" REMOVED_PHANTOM");
//	         break;
//	     }
//	     
//	     if((flags & IResourceDelta.CONTENT) != 0) {
//	       sb.append(" CONTENT");
//	     }
//	     if((flags & IResourceDelta.MOVED_FROM) != 0) {
//	       sb.append(" MOVED_FROM");
//	     }
//	     if((flags & IResourceDelta.MOVED_TO) != 0) {
//	       sb.append(" MOVED_TO");
//	     }
//	     if((flags & IResourceDelta.OPEN) != 0) {
//	       sb.append(" OPEN");
//	     }
//	     if((flags & IResourceDelta.TYPE) != 0) {
//	       sb.append(" TYPE");
//	     }
//	     if((flags & IResourceDelta.SYNC) != 0) {
//	       sb.append(" SYNC");
//	     }
//	     if((flags & IResourceDelta.MARKERS) != 0) {
//	       sb.append(" MARKERS");
//	     }
//	     if((flags & IResourceDelta.REPLACED) != 0) {
//	       sb.append(" REPLACED");
//	     }
//	     if((flags & IResourceDelta.DESCRIPTION) != 0) {
//	       sb.append(" DESCRIPTION");
//	     }
//	     if((flags & IResourceDelta.ENCODING) != 0) {
//	       sb.append(" ENCODING");
//	     }
//	     
//	     System.out.println(sb.toString());
//	   }
//		    public boolean visit(final IResourceDelta delta) throws CoreException {
//	    	dump(delta);
//	    	return true;
//	    }    
//	}

	
	private void addMarker(final IProject project, final String message, final int severity, final String markerId) throws CoreException {
		IMarker marker = project.createMarker(markerId);
		marker.setAttribute(IMarker.MESSAGE, message);
		marker.setAttribute(IMarker.SEVERITY, severity);
	}
 
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.internal.events.InternalBuilder#build(int,
	 *      java.util.Map, org.eclipse.core.runtime.IProgressMonitor)
	 */
	protected IProject[] build(int kind, Map args, IProgressMonitor monitor)
			throws CoreException {
		
		IResourceDelta delta = getDelta(getProject());
//		if (delta != null) {
//			delta.accept(new ResourceDeltaVisitor());
//		}
		
		ProjectInitializer[] initializers = OfsCore.getProjectInitializers(getProject(), true);

		if (kind == FULL_BUILD) {
			fullBuild(initializers, monitor);
		} else {
			if (delta == null) {
				fullBuild(initializers, monitor);
			} else {
				incrementalBuild(initializers, delta, monitor);
			}
		}

		Set<IProject> watchProjects = new HashSet<IProject>();
		for(ProjectInitializer initializer : initializers) {
			CollectionUtils.addAll(watchProjects, initializer.getProjectsToWatch(getProject()));
		}
		return watchProjects.toArray(new IProject[watchProjects.size()]);
	}

	private void deleteMarkers(IProject project, String markerId) {
		try {
			project.deleteMarkers(markerId, false, IResource.DEPTH_ZERO);
		} catch (CoreException ce) {
			OfsCore.getDefault().logWarning("Could not delete markers of project " + project.getName(), ce);
		}
	}

	protected void fullBuild(ProjectInitializer[] initializers, final IProgressMonitor monitor)
			throws CoreException {
		checkProjectConfiguration(initializers, monitor);
	}

	protected void incrementalBuild(ProjectInitializer[] initializers, IResourceDelta delta,
			IProgressMonitor monitor) throws CoreException {
		checkProjectConfiguration(initializers, delta, monitor);
	}
	
	protected void checkProjectConfiguration(final ProjectInitializer[] initializers, IProgressMonitor monitor)  throws CoreException {
		checkProjectConfiguration(initializers, null, monitor);
	}
	
	protected void checkProjectConfiguration(final ProjectInitializer[] initializers, 
			final IResourceDelta delta, IProgressMonitor monitor) throws CoreException {
		
		final IProject project = getProject();
		
		ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) throws CoreException {
				
				// all project initializers
				// ProjectInitializer[] initializers = OfsCore.getProjectInitializers(getProject(), true);

				Set<String> markerIds = new HashSet<String>();
				for(ProjectInitializer initializer : initializers) {
					if (!initializer.getMarkerId().equals("com.odcgroup.workbench.migration.ProblemMarker")) {
						markerIds.add(initializer.getMarkerId());
					}
				}
				for(String markerId : markerIds) {
					deleteMarkers(project, markerId);
				}
				
				// run all project initializers
				monitor.beginTask("Checking project configuration...", initializers.length);
				for(ProjectInitializer initializer : initializers) {
					monitor.subTask("(" + initializer.getClass().getSimpleName() + ")");
					IStatus multiStatus = initializer.checkConfiguration(project, delta);
					for(IStatus status : multiStatus.getChildren()) {
						if (initializer.getMarkerId() != null) {
							addMarker(project, "Design Studio project configuration: " + status.getMessage(), mapSeverity(status.getSeverity()), initializer.getMarkerId());
						} else {
							OfsCore.getDefault().logWarning("Design Studio project configuration: "+project.getName()+". Status has no marker id: " +status, null);
						}
					}
					monitor.worked(1);
				}
				monitor.done();
			}
		}, project, IWorkspace.AVOID_UPDATE, monitor);
		
	}
	
	private int mapSeverity(int severity) {
		switch(severity) {
			case IStatus.ERROR   : return IMarker.SEVERITY_ERROR;
			case IStatus.WARNING : return IMarker.SEVERITY_WARNING;
			case IStatus.INFO    : return IMarker.SEVERITY_INFO;
		}
		return IMarker.SEVERITY_INFO;
	}
}
