package com.odcgroup.workbench.ui.internal.properties;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.ui.PlatformUI;

public class PerspectivePropertyTester extends PropertyTester {

	public PerspectivePropertyTester() {
	}

	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		try {
			String perspectiveId = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getPerspective().getId();
			return expectedValue.equals(perspectiveId);
		} catch(NullPointerException npe) {
			return false;
		}
	}
}
