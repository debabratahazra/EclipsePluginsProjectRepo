package com.odcgroup.workbench.core.di;

import org.eclipse.emf.common.util.URI;

/**
 * Provider for Xtext-language specific implementations.
 * 
 * @see GenerationRuntimeModule re. how to bind() this,
 * so that you can then @Inject ILangSpecificProvider<YourThing>.
 *
 * @author Michael Vorburger
 */
public interface ILangSpecificProvider<T> {

	T get(URI uri);
	
}
