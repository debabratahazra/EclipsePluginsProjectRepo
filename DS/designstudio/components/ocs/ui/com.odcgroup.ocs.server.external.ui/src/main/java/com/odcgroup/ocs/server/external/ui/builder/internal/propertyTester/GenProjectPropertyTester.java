package com.odcgroup.ocs.server.external.ui.builder.internal.propertyTester;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IProject;

public class GenProjectPropertyTester extends PropertyTester {

	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if (receiver instanceof IProject) {
			IProject project = (IProject)receiver;
			boolean isGenProject = project.getName().endsWith("-gen");   //$NON-NLS-1$
			if ("true".equals(expectedValue.toString())) {   //$NON-NLS-1$
				return isGenProject;
			} else {
				return !isGenProject;
			}
		} else {
			return false;
		}
	}

}
