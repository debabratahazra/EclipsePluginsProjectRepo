package com.odcgroup.pageflow.model;

import com.odcgroup.workbench.core.AbstractActivator;

public class Activator extends AbstractActivator {
	
	// the messages resource bundle
    private static final String BUNDLE_NAME = "com.odcgroup.pageflow.model.messages"; //$NON-NLS-1$

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.pageflow.model";

	public static final String MODEL_NAME = "pageflow";
	
	@Override
	protected String getResourceBundleName() {
		return BUNDLE_NAME;
	}
}
