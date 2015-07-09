
package com.odcgroup.domain;

import org.eclipse.xtext.xbase.XbasePackage;

import com.google.inject.Injector;
import com.odcgroup.mdf.ecore.MdfPackage;
import com.odcgroup.workbench.core.di.StandaloneSetupChecker;

/**
 * Initialization support for running Xtext languages 
 * without equinox extension registry
 */
public class DomainStandaloneSetup extends DomainStandaloneSetupGenerated {

	static private Injector injector = null;
	
	static public Injector getInjector() {
		return injector;
	}

	public static void doSetup() {
		new DomainStandaloneSetup().createInjectorAndDoEMFRegistration();
	}
	
	@Override
	public Injector createInjectorAndDoEMFRegistration() {
		// This is needed to register the EPackage in standalone scenarios
		MdfPackage.eINSTANCE.getMdfDomain();
		XbasePackage.eINSTANCE.getXAbstractFeatureCall();
		return super.createInjectorAndDoEMFRegistration();
	}
	
	@Override
	public Injector createInjector() {
		StandaloneSetupChecker.abortIfAlreadyRegistered("domain");
		injector = super.createInjector();
		return injector;
	}
	
	@Override
	public void register(Injector injector) {
		super.register(injector);
	}

}

