package com.odcgroup.page.model.provider;


import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.odcgroup.page.model.Property;

/**
 * A Property provider used within the Page Designer. We do not touch the generated
 * version since it might be useful in other situations, for example, for editing files.
 * 
 * @author Gary Hayes
 */
public class PagePropertyItemProvider extends PropertyItemProvider {
	
	/**
	 * Creates a new PagePropertyItemProvider.
	 * 
	 * @param adapterFactory The AdapterFactory
	 */
	public PagePropertyItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}	
	
	/**
	 * Gets the text of the Property.
	 * 
	 * @param object The Property
	 * @return String The text
	 */
	public String getText(Object object) {
		Property p = (Property) object;
		String name = p.getType().getDisplayName();
		
		String value = "";
		if (p.getModel() != null) {
			URI uri = EcoreUtil.getURI(p.getModel());
			if (uri != null) {
				value = uri.toString();
			}
		} else {
			value = p.getValue() == null ? "" : p.getValue();
		}
		
		return name + " = " + value; //+ localized;
	}	
}
