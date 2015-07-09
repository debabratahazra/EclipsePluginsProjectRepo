package com.odcgroup.page.transformmodel.ui;

import com.odcgroup.workbench.ui.AbstractUIActivator;

/**
 * The activator class controls the plug-in life cycle
 */
public class PageTransformModelUICore extends AbstractUIActivator {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.page.transformmodel.ui";

	
	/**
	 * @return
	 */
	public static PageTransformModelUICore getDefault() {
		return (PageTransformModelUICore) getDefault(PageTransformModelUICore.class);
	}

}
