package com.odcgroup.workbench.core.di;

import javax.inject.Inject;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.inject.Injector;

/**
 * Provider for Xtext-language specific implementations.
 *
 * @author Michael Vorburger
 */
public class ILangSpecificProviderImpl<T> implements ILangSpecificProvider<T> {
	private static final Logger logger = LoggerFactory.getLogger(ILangSpecificProviderImpl.class);

	private @Inject IResourceServiceProvider.Registry registry;
	protected final Class<T> klass;
	
	public ILangSpecificProviderImpl(Class<T> klass) {
		this.klass = klass;
	}

	protected IResourceServiceProvider.Registry getRegistry() {
		if ( registry == null ) {
			logger.debug("No @Inject'd IResourceServiceProvider.Registr - so using (static) IResourceServiceProvider.Registry.INSTANCE");
			registry = IResourceServiceProvider.Registry.INSTANCE;
		}
		return registry;
	}
	
	/**
	 * Get Instance from a DI container.
	 * 
	 * @param klass the Java Class of which you would like to obtain an instance 
	 * @return instance of type klass
	 * 
	 * @throws IllegalArgumentException if no provider for klass is registered in the respective (language specific) Injector for this URI
	 */
	@Override public T get(URI uri) {
		Preconditions.checkNotNull(uri, "uri == null");
		IResourceServiceProvider resourceServiceProvider = getRegistry().getResourceServiceProvider(uri);
		if (resourceServiceProvider == null)
			throw new IllegalStateException("IResourceServiceProvider.Registry getResourceServiceProvider() returned null for URI: " + uri.toString());
		if (!resourceServiceProvider.canHandle(uri))
			throw new IllegalStateException("Now that's curious, the IResourceServiceProvider.Registry returned an IResourceServiceProvider which !canHandle() this URI: " + uri.toString());

		Injector injector = resourceServiceProvider.get(Injector.class);
		// This is not nice - kinda hack, not sure yet how to do this better later...
		injector = getExtendedInjector(injector);
		try {
			return injector.getInstance(klass);
		} catch (Exception e) {
			throw new IllegalArgumentException("getInstance(" + klass.getName() + " failed for Injector obtained for URI: " + uri.toString(), e);
		}
	}

	// @see ILangSpecificProviderImplPlusGeneration... hm.
	protected Injector getExtendedInjector(Injector injector) {
		return injector;
	}
	
}
