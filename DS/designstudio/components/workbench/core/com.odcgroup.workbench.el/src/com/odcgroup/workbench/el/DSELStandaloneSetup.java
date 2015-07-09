
package com.odcgroup.workbench.el;

import com.google.inject.Injector;

/**
 * Initialization support for running Xtext languages 
 * without equinox extension registry
 */
public class DSELStandaloneSetup extends DSELStandaloneSetupGenerated{

	private static Injector injector;

	public static void doSetup() {
		if(injector==null) {
			injector = new DSELStandaloneSetup().createInjectorAndDoEMFRegistration();
		}
	}
	
	public static synchronized Injector getInjector() {
		if(injector==null) {
			doSetup();
		}
		return injector;
	}
}

