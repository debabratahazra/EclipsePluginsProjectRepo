
package com.odcgroup.page;

import org.eclipse.emf.ecore.resource.Resource;

import com.google.inject.Injector;

/**
 * Initialization support for running Xtext languages 
 * without equinox extension registry
 */
public class PageStandaloneSetup extends PageStandaloneSetupGenerated{

	static private Injector injector = null;
	
	static public Injector getInjector() {
		return injector;
	}

	public static void doSetup() {
		new PageStandaloneSetup().createInjectorAndDoEMFRegistration();
	}
	
	@Override
	public Injector createInjector() {
		injector = super.createInjector();
		return injector;
	}

	@Override
	public void register(Injector injector) {
		org.eclipse.xtext.resource.IResourceFactory resourceFactory = injector.getInstance(org.eclipse.xtext.resource.IResourceFactory.class);
		org.eclipse.xtext.resource.IResourceServiceProvider serviceProvider = injector.getInstance(org.eclipse.xtext.resource.IResourceServiceProvider.class);
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("page", resourceFactory);
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("module", resourceFactory);
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("fragment", resourceFactory);
		org.eclipse.xtext.resource.IResourceServiceProvider.Registry.INSTANCE.getExtensionToFactoryMap().put("page", serviceProvider);
		org.eclipse.xtext.resource.IResourceServiceProvider.Registry.INSTANCE.getExtensionToFactoryMap().put("module", serviceProvider);
		org.eclipse.xtext.resource.IResourceServiceProvider.Registry.INSTANCE.getExtensionToFactoryMap().put("fragment", serviceProvider);
	}
}

