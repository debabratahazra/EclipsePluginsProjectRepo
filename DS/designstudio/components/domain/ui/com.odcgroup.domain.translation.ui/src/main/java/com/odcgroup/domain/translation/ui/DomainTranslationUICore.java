package com.odcgroup.domain.translation.ui;

import com.odcgroup.workbench.ui.AbstractUIActivator;

/**
 * The activator class controls the plug-in life cycle
 */
public class DomainTranslationUICore extends AbstractUIActivator {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.domain.translation.ui";


	/**
	 * @return
	 */
	public static DomainTranslationUICore getDefault() {
		return (DomainTranslationUICore) getDefault(DomainTranslationUICore.class);
	}

}
