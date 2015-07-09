package com.odcgroup.workbench.integration.tests;

import org.eclipse.core.runtime.Plugin;

public class Activator extends Plugin {
	
	static private Activator plugin;

	public Activator() {
		plugin = this;
	}

	static public Activator getDefault() {
		if(plugin==null) plugin = new Activator();
		return plugin;
	}

}
