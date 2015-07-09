package com.odcgroup.ocs.server.external.ui.builder.internal.propertyTester;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;

import com.odcgroup.ocs.server.external.ui.OCSServerUIExternalCore;
import com.odcgroup.ocs.server.external.ui.builder.DeployBuilder;
import com.odcgroup.server.ui.internal.CommonConsole;

public class HasDeployBuilderPropertyTester extends PropertyTester {

	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if (receiver instanceof IProject) {
			IProject project = (IProject)receiver;
			IProjectDescription description = getProjectDescription(project);
			if (description != null) {
				boolean hasDeployBuilder = hasDeployBuilder(description);
				if ("true".equals(expectedValue.toString())) {   //$NON-NLS-1$
					return hasDeployBuilder;
				} else {
					return !hasDeployBuilder;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	private boolean hasDeployBuilder(IProjectDescription description) {
		// Builder already associated to the project ?
		for (ICommand cmd : description.getBuildSpec()) {
			if (cmd.getBuilderName().equals(DeployBuilder.BUILDER_ID)) {
				return true;
			}
		}
		
		return false;
	}

	private IProjectDescription getProjectDescription(IProject project) {
		CommonConsole deployConsole = OCSServerUIExternalCore.getDefault().getDeployBuilderConsole();

		// Cannot modify closed projects
		if (!project.isOpen()) {
			deployConsole.printWarning("Cannot add the deploy builder to " + project.getName() + " because it is a closed project");   //$NON-NLS-1$
			return null;
		}
		
		// Get the description
		IProjectDescription description;
		try {
			description = project.getDescription();
		} catch (CoreException e) {
			deployConsole.printError("Unable to get project description of " + project.getName());   //$NON-NLS-1$
			return null;
		}
		return description;
	}
}
