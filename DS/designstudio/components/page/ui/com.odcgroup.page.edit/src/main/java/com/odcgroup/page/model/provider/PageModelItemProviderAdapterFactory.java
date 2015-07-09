package com.odcgroup.page.model.provider;


import org.eclipse.emf.common.notify.Adapter;

/**
 * This AdapterFactory overrides the created one for use within the Page Designer.
 * We do not touch the original version since this may be useful for editing files.
 * 
 * @author Gary Hayes
 */
public class PageModelItemProviderAdapterFactory extends ModelItemProviderAdapterFactory {

	/**
	 * Creates the ModelAdapter.
	 * 
	 * @return PageModelItemProvider
	 */
	public Adapter createModelAdapter() {
		if (modelItemProvider == null) {
			modelItemProvider = new PageModelItemProvider(this);
		}
		return modelItemProvider;
	}

	/**
	 * Creates the ParameterAdapter.
	 * 
	 * @return PageParameterItemProvider
	 */
	public Adapter createParameterAdapter() {
		if (parameterItemProvider == null) {
			parameterItemProvider = new PageParameterItemProvider(this);
		}
		return parameterItemProvider;
	}	
	
	/**
	 * Creates the PropertyAdapter.
	 * 
	 * @return PagePropertyItemProvider
	 */	
	public Adapter createPropertyAdapter() {
		if (propertyItemProvider == null) {
			propertyItemProvider = new PagePropertyItemProvider(this);
		}
		return propertyItemProvider;
	}
	
	/**
	 * Creates the WidgetAdapter.
	 * 
	 * @return PageWidgetItemProvider
	 */
	public Adapter createWidgetAdapter() {
		if (widgetItemProvider == null) {
			widgetItemProvider = new PageWidgetItemProvider(this);
		}
		return widgetItemProvider;
	}	
}
