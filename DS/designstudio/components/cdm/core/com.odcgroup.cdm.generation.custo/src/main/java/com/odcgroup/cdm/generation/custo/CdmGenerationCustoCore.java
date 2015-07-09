package com.odcgroup.cdm.generation.custo;

import com.odcgroup.workbench.core.AbstractActivator;

/**
 * The activator class controls the plug-in life cycle
 */
public class CdmGenerationCustoCore extends AbstractActivator {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.cdm.generation.custo";


	/**
	 * @return
	 */
	public static CdmGenerationCustoCore getDefault() {
		return (CdmGenerationCustoCore) getDefault(CdmGenerationCustoCore.class);
	}

}
