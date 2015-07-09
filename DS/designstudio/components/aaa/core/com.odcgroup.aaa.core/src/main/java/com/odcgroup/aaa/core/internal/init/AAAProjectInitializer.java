package com.odcgroup.aaa.core.internal.init;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import com.odcgroup.aaa.core.AAACore;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.init.AbstractProjectInitializer;

public class AAAProjectInitializer extends AbstractProjectInitializer {

	public AAAProjectInitializer() {
	}

	@Override
	public void initialize(IProject project, boolean force, IProgressMonitor monitor) throws CoreException {
			OfsCore.addNature(project, AAACore.NATURE_ID);
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.core.init.AbstractProjectInitializer#updateConfiguration(org.eclipse.core.resources.IProject, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void updateConfiguration(IProject project, IProgressMonitor monitor) throws CoreException {
	}
}
