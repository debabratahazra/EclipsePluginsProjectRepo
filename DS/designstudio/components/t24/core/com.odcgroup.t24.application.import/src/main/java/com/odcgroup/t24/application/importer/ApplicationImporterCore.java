package com.odcgroup.t24.application.importer;

import com.odcgroup.workbench.core.AbstractActivator;

/**
 * The activator class controls the plug-in life cycle
 */
public class ApplicationImporterCore extends AbstractActivator {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.t24.applicationImporterCore.importer"; //$NON-NLS-1$

	public static ApplicationImporterCore getDefault() {
		return (ApplicationImporterCore) getDefault(ApplicationImporterCore.class);
	}
	
}
