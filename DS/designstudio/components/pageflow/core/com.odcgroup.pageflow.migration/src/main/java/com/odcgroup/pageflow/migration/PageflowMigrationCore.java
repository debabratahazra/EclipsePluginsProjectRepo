package com.odcgroup.pageflow.migration;

import com.odcgroup.workbench.core.AbstractActivator;

/**
 * The activator class controls the plug-in life cycle
 */
public class PageflowMigrationCore extends AbstractActivator {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.pageflow.migration";

	
	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static PageflowMigrationCore getDefault() {
		return (PageflowMigrationCore) getDefault(PageflowMigrationCore.class);
	}

}
