package com.odcgroup.t24.version.model.translation.tests;

import com.google.inject.Injector;
import com.odcgroup.domain.DomainStandaloneSetup;
import com.odcgroup.t24.version.VersionDSLInjectorProvider;

public class VersionWithDependencyInjectorProvider extends VersionDSLInjectorProvider {

	@Override
	protected Injector internalCreateInjector() {
		DomainStandaloneSetup.doSetup();
		return super.internalCreateInjector();
	}

}