package com.odcgroup.t24.version.importer;

import com.odcgroup.workbench.core.AbstractActivator;

/**
 * The activator class controls the plug-in life cycle
 */
public class VersionImporterCore extends AbstractActivator {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.t24.version.importer"; //$NON-NLS-1$

	public static VersionImporterCore getDefault() {
		return (VersionImporterCore) getDefault(VersionImporterCore.class);
	}
	
}
