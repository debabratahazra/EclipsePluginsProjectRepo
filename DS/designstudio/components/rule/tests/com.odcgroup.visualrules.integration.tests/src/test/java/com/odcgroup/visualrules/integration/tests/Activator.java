package com.odcgroup.visualrules.integration.tests;

import org.eclipse.core.runtime.Plugin;

public class Activator extends Plugin {

	private static Plugin plugin;
	
	public Activator() {
		super();
		plugin = this;
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Plugin getDefault() {
		if(plugin==null) {
			plugin = new Activator();
		}
		return plugin;
	}

}
