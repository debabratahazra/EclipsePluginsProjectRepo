package com.odcgroup.t24.enquiry.properties.descriptors;

import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.odcgroup.t24.enquiry.enquiry.Conversion;
import com.odcgroup.t24.enquiry.enquiry.Field;
import com.odcgroup.t24.enquiry.properties.util.ConversionEnum;

/**
 * @author satishnangi
 *
 */
public class FieldConversionPropertyDescriptor extends PropertyDescriptor {
	 
	public FieldConversionPropertyDescriptor(Field field,Conversion model){
		super(model,ConversionEnum.getConversionEnum(model).getDisplay());
	}

}
