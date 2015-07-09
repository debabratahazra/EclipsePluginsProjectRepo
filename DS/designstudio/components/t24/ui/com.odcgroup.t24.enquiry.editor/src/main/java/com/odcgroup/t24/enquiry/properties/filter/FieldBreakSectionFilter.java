package com.odcgroup.t24.enquiry.properties.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;
import com.odcgroup.t24.enquiry.enquiry.Field;
import com.odcgroup.t24.enquiry.properties.descriptors.FieldBreakFieldPropertyDescriptor;
import com.odcgroup.t24.enquiry.properties.descriptors.FieldBreakTypePropertyDescriptor;

/**
 * TODO: Document me!
 *
 * @author mumesh
 *
 */
public class FieldBreakSectionFilter extends AbstractPropertyFilter {

	private EnquiryPackage pkg = EnquiryPackage.eINSTANCE;

	@Override
	public List<EStructuralFeature> getFilterProperties(EObject eObject) {
		List<EStructuralFeature> list = new ArrayList<EStructuralFeature>();
		if (eObject instanceof Field) {
			list.add(pkg.getField_SpoolBreak());
		}
		return list;
	}

	@Override
	protected IPropertyDescriptor[] customPropertyDecriptors(IPropertyDescriptor[] propertyDescriptors, EObject model) {
		List<IPropertyDescriptor> propertyList = Arrays.asList(propertyDescriptors);
		List<IPropertyDescriptor> custompropertyList = new ArrayList<IPropertyDescriptor>();
		if (model instanceof Field) {
			custompropertyList.add(new FieldBreakTypePropertyDescriptor());
			custompropertyList.add(new FieldBreakFieldPropertyDescriptor(model));
			for (IPropertyDescriptor descriptor : propertyList) {
				if (descriptor.getId().equals(pkg.getField_SpoolBreak().getName())) {
					custompropertyList.add(descriptor);
				}
			}
		}          
		return custompropertyList.toArray(new IPropertyDescriptor[0]);
		
	}
}
