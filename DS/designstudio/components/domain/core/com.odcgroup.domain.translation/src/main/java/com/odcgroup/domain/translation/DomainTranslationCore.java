package com.odcgroup.domain.translation;

import com.odcgroup.workbench.core.AbstractActivator;

/**
 * The activator class controls the plug-in life cycle
 */
public class DomainTranslationCore extends AbstractActivator {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.domain.translation";


	public static DomainTranslationCore getDefault() {
		return (DomainTranslationCore) getDefault(DomainTranslationCore.class);
	}

}
