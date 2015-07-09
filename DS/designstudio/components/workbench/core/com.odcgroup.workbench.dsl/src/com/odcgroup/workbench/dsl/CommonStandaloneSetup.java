
package com.odcgroup.workbench.dsl;

/**
 * Initialization support for running Xtext languages 
 * without equinox extension registry
 */
public class CommonStandaloneSetup extends CommonStandaloneSetupGenerated{

	public static void doSetup() {
		new CommonStandaloneSetup().createInjectorAndDoEMFRegistration();
	}
}

