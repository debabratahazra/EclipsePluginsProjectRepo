package com.odcgroup.t24.enquiry.properties.filter;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

import com.odcgroup.t24.enquiry.enquiry.Field;
import com.odcgroup.t24.enquiry.properties.descriptors.FieldOperationPropertyDescriptor;

/**
 * TODO: Document me!
 *
 * @author mumesh
 *
 */
public class OperationSectionFilter extends AbstractPropertyFilter {

	@Override
	public List<EStructuralFeature> getFilterProperties(EObject eObject) {
		List<EStructuralFeature> list = new ArrayList<EStructuralFeature>();
		return list;
	}

	@Override
	protected IPropertyDescriptor[] customPropertyDecriptors(IPropertyDescriptor[] propertyDescriptors, EObject model) {
		List<IPropertyDescriptor> custompropertyList = new ArrayList<IPropertyDescriptor>();
		if (model instanceof Field) {
			custompropertyList.add(new FieldOperationPropertyDescriptor(model));
		}
		return custompropertyList.toArray(new IPropertyDescriptor[0]);
	}
}
