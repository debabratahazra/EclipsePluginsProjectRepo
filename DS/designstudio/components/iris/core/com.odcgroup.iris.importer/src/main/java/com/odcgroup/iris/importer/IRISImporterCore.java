package com.odcgroup.iris.importer;

import com.odcgroup.workbench.core.AbstractActivator;

/**
 * The activator class controls the plug-in life cycle
 */
public class IRISImporterCore extends AbstractActivator {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.iris.importer"; //$NON-NLS-1$

	public static IRISImporterCore getDefault() {
		return (IRISImporterCore) getDefault(IRISImporterCore.class);
	}
	
}
