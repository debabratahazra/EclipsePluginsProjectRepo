package com.odcgroup.ocs.server.external.ui.builder;

import java.util.Collections;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;

import com.odcgroup.ocs.server.external.builder.internal.mapping.TargetMapper;
import com.odcgroup.ocs.server.external.ui.builder.internal.mapping.TargetMapperHelper;

public class UndeployJob extends WorkspaceJob {

	public static final Object UNDEPLOY_JOB_FAMILY = new Object();

	private IProject project;

	public UndeployJob(IProject project) {
		super("Undeploying Project: " + project.getName());
		this.project = project;
	}

	@Override
	public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
		TargetMapper targetMapper = TargetMapperHelper.getTargetMapper(project);
		targetMapper.configure(Collections.<String,String>emptyMap());
		IStatus status = targetMapper.undeployDestinations();
		return status;
	}

	@Override
	public boolean belongsTo(Object family) {
		return family == UNDEPLOY_JOB_FAMILY;
	}

}
