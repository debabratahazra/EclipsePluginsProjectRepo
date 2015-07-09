package com.odcgroup.t24.enquiry.properties.section;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;
import com.odcgroup.t24.enquiry.enquiry.Field;
import com.odcgroup.t24.enquiry.properties.descriptors.EnquiryFieldPropertyDescriptorWrapper;
import com.odcgroup.t24.enquiry.properties.descriptors.FieldColumnHeaderPropertyDescriptor;
import com.odcgroup.t24.enquiry.properties.descriptors.FieldColumnLabelPropertyDescriptor;
import com.odcgroup.t24.enquiry.properties.descriptors.FieldDisplayTypePropertyDescriptor;
import com.odcgroup.t24.enquiry.properties.descriptors.FieldMultiLinePropertyDescriptorWrapper;
import com.odcgroup.t24.enquiry.properties.descriptors.FieldPositionColumnPropertyDescriptor;
import com.odcgroup.t24.enquiry.properties.descriptors.FieldPositionLinePropertyDescriptor;
import com.odcgroup.t24.enquiry.properties.descriptors.FieldPositionPageThrowPropertyDescriptor;
import com.odcgroup.t24.enquiry.properties.descriptors.FieldPositionRelativeLinePropertyDescriptor;
import com.odcgroup.t24.enquiry.properties.descriptors.FieldPositionRelativeMultiLinePropertyDescriptor;
import com.odcgroup.t24.enquiry.properties.filter.AbstractPropertyFilter;

/**
 * TODO: Document me!
 *
 * @author satishnangi
 *
 */
public class EnquiryPresentationSectionFilter extends AbstractPropertyFilter {
	EnquiryPackage pkg = EnquiryPackage.eINSTANCE;
	@Override
	public List<EStructuralFeature> getFilterProperties(EObject eObject) {
		List<EStructuralFeature> list = new ArrayList<EStructuralFeature>();
		if (eObject instanceof Enquiry) {
			list.add(pkg.getEnquiry_Toolbar());
			list.add(pkg.getEnquiry_StartLine());
			list.add(pkg.getEnquiry_EndLine());
			list.add(pkg.getEnquiry_NoSelection());
			list.add(pkg.getEnquiry_ZeroRecordsDisplay());
			list.add(pkg.getEnquiry_AccountField());
			list.add(pkg.getEnquiry_CustomerField());
		}
		if(eObject instanceof Field){
			list.add(pkg.getField_SingleMulti());
			list.add(pkg.getField_ColumnWidth());
			list.add(pkg.getField_DisplayType());
			list.add(pkg.getField_DisplaySection());
			list.add(pkg.getField_NoColumnLabel());
			list.add(pkg.getField_NoHeader());
			list.add(pkg.getField_Hidden());
			list.add(pkg.getFieldPosition_PageThrow());
		}
		return list;
	}

	@Override
	protected IPropertyDescriptor[] customPropertyDecriptors(IPropertyDescriptor[] propertyDescriptors, EObject model) {
		List<IPropertyDescriptor> propertyList = Arrays.asList(propertyDescriptors);
		List<IPropertyDescriptor> custompropertyList = new ArrayList<IPropertyDescriptor>();
		if (model instanceof Enquiry) {
			for (IPropertyDescriptor descriptor : propertyList) {
				IPropertyDescriptor wrapperDescriprot = null;
				if (descriptor.getId().equals(pkg.getEnquiry_AccountField().getName())||descriptor.getId().equals(pkg.getEnquiry_CustomerField().getName())) {
					wrapperDescriprot = new EnquiryFieldPropertyDescriptorWrapper(descriptor,model);
					custompropertyList.add(wrapperDescriprot);
				}else {
					custompropertyList.add(descriptor);
				}
			}
			custompropertyList.toArray(new IPropertyDescriptor[0]);
		} else if (model instanceof Field) {
			custompropertyList.add(new FieldPositionColumnPropertyDescriptor());
			custompropertyList.add(new FieldPositionRelativeLinePropertyDescriptor());
			custompropertyList.add(new FieldPositionLinePropertyDescriptor());
			for (IPropertyDescriptor descriptor : propertyList) {
				IPropertyDescriptor wrapperDescriprot = null;
				if (descriptor.getId().equals(pkg.getField_NoColumnLabel().getName())) {
					wrapperDescriprot = new FieldColumnLabelPropertyDescriptor(descriptor);
					custompropertyList.add(wrapperDescriprot);
				} else if (descriptor.getId().equals(pkg.getField_NoHeader().getName())) {
					wrapperDescriprot = new FieldColumnHeaderPropertyDescriptor(descriptor);
					custompropertyList.add(wrapperDescriprot);
				}else if (descriptor.getId().equals(pkg.getField_SingleMulti().getName())) {
					wrapperDescriprot = new FieldMultiLinePropertyDescriptorWrapper(descriptor);
					custompropertyList.add(wrapperDescriprot);
				}else if (descriptor.getId().equals(pkg.getField_DisplayType().getName())) {
					wrapperDescriprot = new FieldDisplayTypePropertyDescriptor(descriptor,model.eContainer());
					custompropertyList.add(wrapperDescriprot);
				}else {
					custompropertyList.add(descriptor);
				}
			}
			custompropertyList.add(7,new FieldPositionRelativeMultiLinePropertyDescriptor());
			custompropertyList.add(11,new FieldPositionPageThrowPropertyDescriptor());
		} else{
			custompropertyList.addAll(propertyList);
		}
         
		return custompropertyList.toArray(new IPropertyDescriptor[0]);
		
	}
}
