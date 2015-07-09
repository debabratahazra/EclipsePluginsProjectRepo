package com.odcgroup.workbench.generation.cartridge.ng.di;

import com.google.inject.Injector;
import com.google.inject.Module;
import com.odcgroup.workbench.core.di.ILangSpecificProviderImpl;

public class ILangSpecificProviderImplPlusGeneration<T> extends ILangSpecificProviderImpl<T> {

	private final Module generationModule;

	public ILangSpecificProviderImplPlusGeneration(Class<T> klass, Module generationModule) {
		super(klass);
		this.generationModule = generationModule;
	}

	@Override
	protected Injector getExtendedInjector(Injector injector) {
		return injector.createChildInjector(generationModule);
	}

}
