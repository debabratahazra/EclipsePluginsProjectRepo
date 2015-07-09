
package com.odcgroup.t24.enquiry;

import org.eclipse.emf.ecore.resource.Resource;

import com.google.inject.Injector;
import com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl;
import com.odcgroup.workbench.core.di.StandaloneSetupChecker;

/**
 * Initialization support for running Xtext languages 
 * without equinox extension registry
 */
public class EnquiryStandaloneSetup extends EnquiryStandaloneSetupGenerated{

	static private Injector injector = null;
	
	static public Injector getInjector() {
		return injector;
	}
	
	public static void doSetup() {
		EnquiryPackageImpl.init();
		new EnquiryStandaloneSetup().createInjectorAndDoEMFRegistration();
	}
	
	@Override
	public Injector createInjectorAndDoEMFRegistration() {
		// DS-7720
		if (!Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().containsKey("xtextbin"))
			Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(
					"xtextbin", new org.eclipse.xtext.resource.impl.BinaryGrammarResourceFactoryImpl());
		
		return super.createInjectorAndDoEMFRegistration();
	}
	
	@Override
	public Injector createInjector() {
		StandaloneSetupChecker.abortIfAlreadyRegistered("enquiry");
		injector = super.createInjector();
		return injector;
	}

}

