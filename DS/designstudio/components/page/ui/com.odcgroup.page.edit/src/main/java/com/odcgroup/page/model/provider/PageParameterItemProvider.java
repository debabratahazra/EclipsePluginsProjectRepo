package com.odcgroup.page.model.provider;

import org.eclipse.emf.common.notify.AdapterFactory;

import com.odcgroup.page.model.Parameter;

/**
 * A Property provider used within the Page Designer. We do not touch the generated
 * version since it might be useful in other situations, for example, for editing files.
 * 
 * @author Gary Hayes
 */
public class PageParameterItemProvider extends ParameterItemProvider {
	
	/**
	 * Creates a new PageParameterItemProvider.
	 * 
	 * @param adapterFactory The AdapterFactory
	 */
	public PageParameterItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}	
	
	/**
	 * Gets the text of the Property.
	 * 
	 * @param object The Property
	 * @return String The text
	 */
	public String getText(Object object) {
		Parameter p = (Parameter) object;
		
		String name = p.getName();
		String value = p.getValue() == null ? "" : p.getValue();
		
		return name + " = " + value;
	}	
}
