package com.odcgroup.domain.edmx.ui;

import com.odcgroup.workbench.ui.AbstractUIActivator;

/**
 * The activator class controls the plug-in life cycle
 */
public class EDMXUICore extends AbstractUIActivator {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.domain.edmx.ui"; //$NON-NLS-1$

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static EDMXUICore getDefault() {
		return (EDMXUICore) getDefault(EDMXUICore.class);
	}
}
