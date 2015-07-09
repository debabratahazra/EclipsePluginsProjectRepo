package com.odcgroup.menu.generation;
import com.odcgroup.workbench.core.AbstractActivator;

/**
 * @author pkk
 *
 */
public class MenuGenerationCore extends AbstractActivator {

	public static final String MENU_CODE_CARTRIDGE_EXTENSION_ID = "com.odcgroup.menu.generation.code";

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.menu.generation";
	
	
	/**
	 * @return
	 */
	public static MenuGenerationCore getDefault() {
		return (MenuGenerationCore) getDefault(MenuGenerationCore.class);
	}

}
