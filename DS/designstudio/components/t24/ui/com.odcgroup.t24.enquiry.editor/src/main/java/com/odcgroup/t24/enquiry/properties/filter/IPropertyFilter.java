package com.odcgroup.t24.enquiry.properties.filter;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 *
 * @author phanikumark
 *
 */
public interface IPropertyFilter {
	/**
	 * returns the list of features 
	 * to be filtered of the give eObject 
	 * 
	 * @param eObject
	 * @return
	 */
	public List<EStructuralFeature> getFilterProperties(EObject eObject);
	
	/**
	 * returns the filtered <IPropertyDescriptor>s
	 * @param model
	 * @return
	 */
	public IPropertyDescriptor[] getFilteredPropertyDescriptors(EObject model,  IPropertySource source);

}
