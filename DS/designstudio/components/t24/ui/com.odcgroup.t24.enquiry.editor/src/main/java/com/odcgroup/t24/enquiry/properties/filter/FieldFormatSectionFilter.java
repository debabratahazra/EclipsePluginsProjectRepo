package com.odcgroup.t24.enquiry.properties.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;
import com.odcgroup.t24.enquiry.enquiry.Field;
import com.odcgroup.t24.enquiry.properties.descriptors.FieldFormatCurrencyPatternDescriptor;
import com.odcgroup.t24.enquiry.properties.descriptors.FieldFormatFieldPropertyDescriptor;
import com.odcgroup.t24.enquiry.properties.descriptors.FieldFormatMaskPropertyDescriptor;
import com.odcgroup.t24.enquiry.properties.descriptors.FieldFormatTypePropertyDescriptor;

/**
 * TODO: Document me!
 *
 * @author mumesh
 *
 */
public class FieldFormatSectionFilter extends AbstractPropertyFilter {

	private EnquiryPackage pkg = EnquiryPackage.eINSTANCE;

	@Override
	public List<EStructuralFeature> getFilterProperties(EObject eObject) {
		List<EStructuralFeature> list = new ArrayList<EStructuralFeature>();
		if (eObject instanceof Field) {
			list.add(pkg.getField_FmtMask());
			list.add(pkg.getField_NumberOfDecimals());
			list.add(pkg.getField_EscapeSequence());
			list.add(pkg.getField_CommaSeparator());
			list.add(pkg.getField_Length());
			list.add(pkg.getField_Alignment());
		}
		return list;
	}

	@Override
	protected IPropertyDescriptor[] customPropertyDecriptors(IPropertyDescriptor[] propertyDescriptors, EObject model) {
		List<IPropertyDescriptor> propertyList = Arrays.asList(propertyDescriptors);
		List<IPropertyDescriptor> custompropertyList = new ArrayList<IPropertyDescriptor>();
		if (model instanceof Field) {
			IPropertyDescriptor wrapperDescriprot = null;
			for (IPropertyDescriptor descriptor : propertyList) {
				if (descriptor.getId().equals(pkg.getField_FmtMask().getName())) {
					wrapperDescriprot = new FieldFormatMaskPropertyDescriptor(descriptor);
					custompropertyList.add(wrapperDescriprot);
					custompropertyList.add(new FieldFormatTypePropertyDescriptor());
					custompropertyList.add(new FieldFormatFieldPropertyDescriptor(model));
					custompropertyList.add(new FieldFormatCurrencyPatternDescriptor());
				} else {
					custompropertyList.add(descriptor);
				}
			}
			
		}          
		return custompropertyList.toArray(new IPropertyDescriptor[0]);
		
	}
}
