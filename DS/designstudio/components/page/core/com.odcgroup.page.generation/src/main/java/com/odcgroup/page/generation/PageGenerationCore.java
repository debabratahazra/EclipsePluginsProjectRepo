package com.odcgroup.page.generation;

import com.odcgroup.workbench.core.AbstractActivator;

/**
 * The activator class controls the plug-in life cycle
 */
public class PageGenerationCore extends AbstractActivator {

	/** The plug-in ID. */
	public static final String PLUGIN_ID = "com.odcgroup.page.generation";

	
	/**
	 * @return PageGenerationCore
	 */
	public static PageGenerationCore getDefault() {
		return (PageGenerationCore) getDefault(PageGenerationCore.class);
	}
}
