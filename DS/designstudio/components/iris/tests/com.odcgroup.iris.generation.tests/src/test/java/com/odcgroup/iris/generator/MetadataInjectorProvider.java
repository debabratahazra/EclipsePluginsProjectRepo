package com.odcgroup.iris.generator;

import org.eclipse.xtext.junit4.GlobalRegistries;
import org.eclipse.xtext.junit4.GlobalRegistries.GlobalStateMemento;
import org.eclipse.xtext.junit4.IInjectorProvider;
import org.eclipse.xtext.junit4.IRegistryConfigurator;

import com.google.inject.Injector;
import com.odcgroup.domain.DomainStandaloneSetup;
import com.temenos.interaction.rimdsl.RIMDslStandaloneSetup;

public class MetadataInjectorProvider implements IInjectorProvider, IRegistryConfigurator {
	
    protected GlobalStateMemento stateBeforeInjectorCreation;
	protected GlobalStateMemento stateAfterInjectorCreation;
	protected Injector metadataInjector;

	static {
		GlobalRegistries.initializeDefaults();
	}

	public Injector getInjector()
	{
		if (metadataInjector == null) {
			stateBeforeInjectorCreation = GlobalRegistries.makeCopyOfGlobalState();
			this.metadataInjector = new RIMDslStandaloneSetup().createInjectorAndDoEMFRegistration();
			new DomainStandaloneSetup().createInjectorAndDoEMFRegistration();
			stateAfterInjectorCreation = GlobalRegistries.makeCopyOfGlobalState();
		}
		return metadataInjector;
	}
	
	public void restoreRegistry() {
		stateBeforeInjectorCreation.restoreGlobalState();
	}

	public void setupRegistry() {
		getInjector();
		stateAfterInjectorCreation.restoreGlobalState();
	}
}
