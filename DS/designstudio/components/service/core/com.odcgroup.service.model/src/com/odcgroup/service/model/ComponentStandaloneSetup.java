
package com.odcgroup.service.model;

/**
 * Initialization support for running Xtext languages 
 * without equinox extension registry
 */
public class ComponentStandaloneSetup extends ComponentStandaloneSetupGenerated{

	public static void doSetup() {
		new ComponentStandaloneSetup().createInjectorAndDoEMFRegistration();
	}
}

