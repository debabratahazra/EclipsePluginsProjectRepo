package com.odcgroup.workbench.el.engine;


import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.resource.IResourceServiceProvider;

import ch.vorburger.el.engine.Expression;
import ch.vorburger.el.engine.ExpressionFactory;

import com.google.inject.Injector;

/**
 * Expression Factory which can create new Expression objects from some textual representation.
 * 
 * Initialization of this thing is probably expensive, so create only one and keep it around (Singleton) !
 *  
 * @see Expression
 * @author Kai Kreuzer
 */
public class DSExpressionFactory extends ExpressionFactory {

	private static final URI dsexURI = URI.createURI("DSExpressionFactory.dsex");

	public DSExpressionFactory() {
		// DS-7397 (DS-5853) NOT this.guiceInjector = DSELStandaloneSetup.getInjector(); // NOT new ELStandaloneSetup().createInjectorAndDoEMFRegistration();
		IResourceServiceProvider rsp = IResourceServiceProvider.Registry.INSTANCE.getResourceServiceProvider(dsexURI);
		this.guiceInjector = rsp.get(Injector.class);
	}
	
	@Override
	protected String getFileExtension() {
		return "dsex";
	}

}
