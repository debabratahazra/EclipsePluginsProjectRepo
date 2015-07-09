package com.odcgroup.workbench.generation.init;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;

import com.odcgroup.workbench.core.init.AbstractProjectInitializer;
import com.odcgroup.workbench.generation.GenerationCore;

public class CheckForDuplicatePathInitializer extends
		AbstractProjectInitializer {

	@Override
	public IStatus checkConfiguration(IProject project, IResourceDelta delta) {
		MultiStatus status = (MultiStatus) super.checkConfiguration(project, delta);

		checkUniqueInWorkspace(project, status);

		// check the gen projects
		for(String projectName : GenerationCore.getJavaProjectNames(project)) {
			IProject javaProject = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
			if(javaProject!=null) {
				checkUniqueInWorkspace(javaProject, status);
			}
		}
		return status;
	}

	private void checkUniqueInWorkspace(IProject project, MultiStatus status) {
		if(project.getLocationURI()!=null) {
			IContainer[] containers = ResourcesPlugin.getWorkspace().getRoot().findContainersForLocationURI(project.getLocationURI());
			if(containers.length>1) {
				IPath path = containers[0].getLocation().equals(project.getLocation()) ? containers[1].getFullPath() : containers[0].getFullPath();
				IStatus error = new Status(IStatus.ERROR, GenerationCore.PLUGIN_ID, 
						"Project '" + project.getName() + "' is also available as '" + path.toString() + "' in the workspace; this occurrance must be removed.");
				status.add(error);
			}
		}
	}

	@Override
	public void updateConfiguration(IProject project, IProgressMonitor monitor)
			throws CoreException {
		// there is nothing we can do to fix the problem
	}

}
