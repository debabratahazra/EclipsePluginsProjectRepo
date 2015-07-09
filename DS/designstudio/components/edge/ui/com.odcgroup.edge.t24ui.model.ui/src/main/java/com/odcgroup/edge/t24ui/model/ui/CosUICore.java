package com.odcgroup.edge.t24ui.model.ui;

import com.odcgroup.workbench.core.AbstractActivator;

/**
 * The activator class controls the plug-in life cycle
 */
public class CosUICore extends AbstractActivator {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.edge.t24.cos.ui"; //$NON-NLS-1$

	/**
	 * The constructor
	 */
	public CosUICore() {
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static CosUICore getDefault() {
		return (CosUICore) getDefault(CosUICore.class);
	}

}
