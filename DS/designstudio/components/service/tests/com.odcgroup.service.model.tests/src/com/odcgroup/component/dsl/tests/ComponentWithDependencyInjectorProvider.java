package com.odcgroup.component.dsl.tests;

import com.google.inject.Injector;
import com.odcgroup.domain.DomainStandaloneSetup;
import com.odcgroup.service.model.ComponentInjectorProvider;

public class ComponentWithDependencyInjectorProvider extends
		ComponentInjectorProvider {
	
    @Override
    public Injector internalCreateInjector() {
    	DomainStandaloneSetup.doSetup();
        return super.internalCreateInjector();
    }

}
