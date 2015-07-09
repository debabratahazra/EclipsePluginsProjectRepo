package com.odcgroup.t24.enquiry.properties.filter;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.odcgroup.t24.enquiry.enquiry.Conversion;
import com.odcgroup.t24.enquiry.enquiry.Field;
import com.odcgroup.t24.enquiry.properties.util.ConversionEnum;

/**
 * @author satishnangi
 *
 */
public class ConversionSectionFilter extends AbstractPropertyFilter {
	
	@Override
	public List<EStructuralFeature> getFilterProperties(EObject eObject) {
		List<EStructuralFeature> list = new ArrayList<EStructuralFeature>();
		return list;
	}
	
	@Override
	protected IPropertyDescriptor[] customPropertyDecriptors(IPropertyDescriptor[] propertyDescriptors, EObject model) {
		List<IPropertyDescriptor> custompropertyList = new ArrayList<IPropertyDescriptor>();
		if (model instanceof Field) {
			EList<Conversion> conversions = ((Field)model).getConversion();
			for(Conversion conversion : conversions){
				custompropertyList.add(new PropertyDescriptor(conversion,ConversionEnum.getConversionEnum(conversion).getDisplay()));
			}
		}
		return custompropertyList.toArray(new IPropertyDescriptor[0]);
	}
}
