package com.odcgroup.t24.common.importer;

import com.odcgroup.workbench.core.AbstractActivator;

/**
 * The activator class controls the plug-in life cycle
 */
public class ImporterCore extends AbstractActivator {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.t24.common.importer"; //$NON-NLS-1$

	public static ImporterCore getDefault() {
		return (ImporterCore) getDefault(ImporterCore.class);
	}
	
}
