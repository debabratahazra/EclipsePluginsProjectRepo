
package com.odcgroup.menu.model;

import com.google.inject.Injector;

/**
 * Initialization support for running Xtext languages 
 * without equinox extension registry
 */
public class MenuStandaloneSetup extends MenuStandaloneSetupGenerated{
	
	static private Injector injector = null;

	static public Injector getInjector() {
		return injector;
	}

	public static void doSetup() {
		new MenuStandaloneSetup().createInjectorAndDoEMFRegistration();
	}
	
	@Override
	public Injector createInjector() {
		injector = super.createInjector();
		return injector;
	}
}

