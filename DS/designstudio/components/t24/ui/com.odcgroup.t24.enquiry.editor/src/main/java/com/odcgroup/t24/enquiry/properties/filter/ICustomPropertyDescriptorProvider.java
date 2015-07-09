package com.odcgroup.t24.enquiry.properties.filter;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

/**
 *
 * @author phanikumark
 *
 */
public interface ICustomPropertyDescriptorProvider {
	
	/**
	 * returns the custom property descriptors for the default descriptors by model type
	 * 
	 * @param propertyDescriptors
	 * @param model
	 * @return
	 */
	List<IPropertyDescriptor> getCustomPropertyDescritpors(List<IPropertyDescriptor> propertyDescriptors, EObject model);

}
