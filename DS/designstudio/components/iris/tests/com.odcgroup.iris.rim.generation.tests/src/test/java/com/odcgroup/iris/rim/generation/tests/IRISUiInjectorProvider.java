package com.odcgroup.iris.rim.generation.tests;

import org.eclipse.xtext.junit4.IInjectorProvider;

import com.google.inject.Injector;
import com.temenos.interaction.rimdsl.ui.internal.RIMDslActivator;

public class IRISUiInjectorProvider implements IInjectorProvider {

	public Injector getInjector() {
        return RIMDslActivator.getInstance().getInjector("com.temenos.interaction.rimdsl.RIMDsl");
	}


}
