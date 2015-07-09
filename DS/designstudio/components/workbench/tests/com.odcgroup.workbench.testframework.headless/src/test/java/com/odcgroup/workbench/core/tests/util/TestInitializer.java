package com.odcgroup.workbench.core.tests.util;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;

import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.init.AbstractProjectInitializer;

public class TestInitializer extends AbstractProjectInitializer {

	public TestInitializer() {
	}

	@Override
	public IStatus checkConfiguration(IProject project, IResourceDelta delta) {
		return new MultiStatus(OfsCore.PLUGIN_ID, IStatus.OK, "no problems", null);
	}

	@Override
	public void updateConfiguration(IProject project, IProgressMonitor monitor)
			throws CoreException {
	}

}
