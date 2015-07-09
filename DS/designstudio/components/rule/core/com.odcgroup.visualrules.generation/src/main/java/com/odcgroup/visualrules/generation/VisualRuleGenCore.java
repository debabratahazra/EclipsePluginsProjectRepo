package com.odcgroup.visualrules.generation;

import com.odcgroup.workbench.core.AbstractActivator;

/**
 * The activator class controls the plug-in life cycle
 */
public class VisualRuleGenCore extends AbstractActivator {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.visualrules.generation";	

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static VisualRuleGenCore getDefault() {
		return (VisualRuleGenCore) getDefault(VisualRuleGenCore.class);
	}
}
