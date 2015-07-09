package com.odcgroup.iris.generator;
import com.odcgroup.workbench.core.AbstractActivator;


public class IRISGenerationCore extends AbstractActivator {


	public static final String MENU_CODE_CARTRIDGE_EXTENSION_ID = "com.odcgroup.iris.metadata.generation";

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.iris.metadata.generation";
	
	
	/**
	 * @return
	 */
	public static IRISGenerationCore getDefault() {
		return (IRISGenerationCore) getDefault(IRISGenerationCore.class);
	}
}
