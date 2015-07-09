package com.odcgroup.process.model;

import com.odcgroup.workbench.core.AbstractActivator;

public class Activator extends AbstractActivator {
	// the messages resource bundle
    private static final String BUNDLE_NAME = "com.odcgroup.process.model.messages"; //$NON-NLS-1$

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.process.model";

	// OCS-24647 (Change extension process to workflow)
	public static final String MODEL_NAME = "workflow";
	
	@Override
	protected String getResourceBundleName() {
		return BUNDLE_NAME;
	}
}
