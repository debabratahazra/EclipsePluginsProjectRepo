package com.odcgroup.page.model.provider;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;


/**
 * The AdapterProviderFactory for Models.
 * 
 * @author Gary Hayes
 */
public class PageModelAdapterFactoryProvider {

	/** The adapter factory. */
	private static ComposedAdapterFactory adapterFactory;

	/**
	 * Gets the AdapterFactory.
	 * 
	 * @return AdapterFactory
	 */
	public final static AdapterFactory getAdapterFactory() {
		if (adapterFactory == null)
			adapterFactory = new ComposedAdapterFactory(createFactoryList());
		return adapterFactory;
	}
	
	/**
	 * Creates the List of factories.
	 * 
	 * @return List of AdpaterFactories
	 */
	private final static List<AdapterFactory> createFactoryList() {
		List<AdapterFactory> factories = new ArrayList<AdapterFactory>();
		// this is my provider generated in the .Edit plugin for me. Replace
		// with yours.
		factories.add(new PageModelItemProviderAdapterFactory());
		// these are EMF generic providers
		factories.add(new ResourceItemProviderAdapterFactory());
		factories.add(new ReflectiveItemProviderAdapterFactory());
		// ... add other provider adapter factories for your model as needed
		// if you don't know what to add, look at the creation of the adapter
		// factory
		// in your generated editor
		return factories;
	}	

}
