package com.odcgroup.menu.generation.tap;

import org.eclipse.xtext.ISetup;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class MenuConfigGeneratorProvider implements ISetup {

	@Override
	public Injector createInjectorAndDoEMFRegistration() {
		return Guice.createInjector(new MenuConfigGeneratorModule());
	}

}
