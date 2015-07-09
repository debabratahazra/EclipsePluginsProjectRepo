package com.odcgroup.t24.enquiry.properties.descriptors;

import org.eclipse.ui.views.properties.IPropertyDescriptor;


/**
 * @author satishnangi
 *
 */
public class FieldMultiLinePropertyDescriptorWrapper extends AbstractPropertyDescriptorWrapper {
     public static String SINGLE_MULTI_LINE = "Multi line" ;
	/**
	 * @param descriptor
	 */
	public FieldMultiLinePropertyDescriptorWrapper(IPropertyDescriptor descriptor) {
		super(descriptor);
	}
	
     @Override
    public String getDisplayName() {
    	
    	return SINGLE_MULTI_LINE;
    }
	
}
