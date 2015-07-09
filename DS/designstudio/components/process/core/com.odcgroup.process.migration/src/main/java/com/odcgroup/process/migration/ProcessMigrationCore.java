package com.odcgroup.process.migration;

import com.odcgroup.workbench.core.AbstractActivator;

/**
 * The activator class controls the plug-in life cycle
 */
public class ProcessMigrationCore extends AbstractActivator {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.process.migration";	

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static ProcessMigrationCore getDefault() {
		return (ProcessMigrationCore) getDefault(ProcessMigrationCore.class);
	}

}
