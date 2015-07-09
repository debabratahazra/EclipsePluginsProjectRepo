package com.odcgroup.cdm.generation;

import com.odcgroup.workbench.core.AbstractActivator;

/**
 * The activator class controls the plug-in life cycle
 */
public class CdmGenerationCore extends AbstractActivator {

	/** The plug-in ID. */
	public static final String PLUGIN_ID = "com.odcgroup.cdm.generation";	

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static CdmGenerationCore getDefault() {
		return (CdmGenerationCore) getDefault(CdmGenerationCore.class);
	}
	

}
