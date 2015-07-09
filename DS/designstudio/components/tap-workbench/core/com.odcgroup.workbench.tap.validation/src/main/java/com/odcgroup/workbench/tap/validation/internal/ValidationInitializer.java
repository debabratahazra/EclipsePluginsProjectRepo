package com.odcgroup.workbench.tap.validation.internal;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;

import com.odcgroup.workbench.core.init.AbstractProjectInitializer;
import com.odcgroup.workbench.tap.validation.ValidationCore;

public class ValidationInitializer extends AbstractProjectInitializer {

	public ValidationInitializer() {
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.core.init.AbstractProjectInitializer#updateConfiguration(org.eclipse.core.resources.IProject, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void updateConfiguration(IProject project, IProgressMonitor monitor) throws CoreException {
		if(!ValidationCore.isBuilderEnabled(project)) ValidationCore.enableBuilder(project);
	}

	@Override
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.core.init.AbstractProjectInitializer#checkConfiguration(org.eclipse.core.resources.IProject)
	 */
	public IStatus checkConfiguration(IProject project, IResourceDelta delta) {
		MultiStatus status = (MultiStatus) super.checkConfiguration(project, delta);
		
		if(!ValidationCore.isBuilderEnabled(project)) {
			IStatus error = new Status(IStatus.ERROR, ValidationCore.PLUGIN_ID, 
					"Validation builder is not activated on this project.");
			status.add(error);
		}
		
		return status;
	}
}
