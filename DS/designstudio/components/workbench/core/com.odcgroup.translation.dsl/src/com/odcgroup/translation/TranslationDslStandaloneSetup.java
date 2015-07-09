
package com.odcgroup.translation;

/**
 * Initialization support for running Xtext languages 
 * without equinox extension registry
 */
public class TranslationDslStandaloneSetup extends TranslationDslStandaloneSetupGenerated{

	public static void doSetup() {
		new TranslationDslStandaloneSetup().createInjectorAndDoEMFRegistration();
	}
}

