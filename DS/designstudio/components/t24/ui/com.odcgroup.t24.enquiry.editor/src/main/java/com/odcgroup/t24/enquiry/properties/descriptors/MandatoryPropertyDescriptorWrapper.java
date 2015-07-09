package com.odcgroup.t24.enquiry.properties.descriptors;

import org.eclipse.ui.views.properties.IPropertyDescriptor;

/**
 * TODO: Document me!
 *
 * @author satishnangi
 *
 */
public class MandatoryPropertyDescriptorWrapper extends AbstractPropertyDescriptorWrapper {
    public static String SELECTION_MANDATORY_PROPERTY = "Required";
	/**
	 * @param descriptor
	 */
	public MandatoryPropertyDescriptorWrapper(IPropertyDescriptor descriptor) {
		super(descriptor);
		
	}
    @Override
    public String getDisplayName() {
    	return SELECTION_MANDATORY_PROPERTY;
    }
}
