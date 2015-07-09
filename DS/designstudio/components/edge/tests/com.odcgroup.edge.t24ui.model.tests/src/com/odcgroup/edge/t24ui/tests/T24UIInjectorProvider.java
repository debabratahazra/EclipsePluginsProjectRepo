package com.odcgroup.edge.t24ui.tests;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.resource.XtextResourceSet;

import com.google.inject.Injector;
import com.odcgroup.domain.DomainStandaloneSetup;
import com.odcgroup.edge.t24ui.model.T24UIStandaloneSetup;
import com.odcgroup.t24.enquiry.EnquiryStandaloneSetup;
import com.odcgroup.t24.version.VersionDSLStandaloneSetup;
import com.odcgroup.workbench.generation.cartridge.ng.di.GenerationRuntimeModule;

/**
 * IInjectorProvider for tests of the model defined in com.odcgroup.edge.t24ui.model
 *
 * @author Michael Vorburger
 */
public class T24UIInjectorProvider extends EFactoryInjectorProvider {

	@Override
	protected Injector internalCreateInjector() {
		T24UIStandaloneSetup.doSetup();
		DomainStandaloneSetup.doSetup();
		VersionDSLStandaloneSetup.doSetup();
		EnquiryStandaloneSetup.doSetup();			

		return super.internalCreateInjector();
	}
	
	class GenerationRuntimeModuleTest extends GenerationRuntimeModule {
		protected void configure() {
			super.configure();
			// need this otherwise some class cannot be properly instantiated by Guice.
			bind(ResourceSet.class).to(XtextResourceSet.class);
		}
	}
}
