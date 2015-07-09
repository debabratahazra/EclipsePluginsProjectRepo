package com.odcgroup.t24.mdf.generation.tests;

import com.google.inject.Injector;
import com.odcgroup.domain.DomainInjectorProvider;
import com.odcgroup.t24.localReferenceApplication.impl.LocalReferenceApplicationPackageImpl;
import com.odcgroup.translation.translationDsl.impl.TranslationDslPackageImpl;

public class DomainWithLocalRefDependenciesInjectorProvider extends DomainInjectorProvider {

	@Override
	protected Injector internalCreateInjector() {
		TranslationDslPackageImpl.init();
		LocalReferenceApplicationPackageImpl.init();
		return super.internalCreateInjector();
	}
	
}
