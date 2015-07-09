package com.odcgroup.t24.enquiry.properties.filter;

import java.util.ArrayList;
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
public abstract class AbstractPropertyFilter implements IPropertyFilter {
	
	public final IPropertyDescriptor[] getFilteredPropertyDescriptors(EObject model, IPropertySource source) {
		List<EStructuralFeature> filterProperties = getFilterProperties(model);
		if (filterProperties != null && !filterProperties.isEmpty()) {
			IPropertyDescriptor[] descs = source.getPropertyDescriptors();
			List<IPropertyDescriptor> filtered = new ArrayList<IPropertyDescriptor>();
			for(EStructuralFeature feature: filterProperties){
				for(IPropertyDescriptor desc : descs){
					if(desc.getId().equals(feature.getName())) {
						filtered.add(desc);
						break;
					}
				}
			}

			return customPropertyDecriptors(filtered.toArray(new IPropertyDescriptor[filtered.size()]),model);
		}
		return customPropertyDecriptors(source.getPropertyDescriptors(),model);
	}
    /**
     * 
     * @param propertyDescriptors
     * @param model
     * @return propertyWrapper class if any label or cell editor changes
     */
	protected IPropertyDescriptor[] customPropertyDecriptors(IPropertyDescriptor[] propertyDescriptors,EObject model){
		  return propertyDescriptors;
	}
}
