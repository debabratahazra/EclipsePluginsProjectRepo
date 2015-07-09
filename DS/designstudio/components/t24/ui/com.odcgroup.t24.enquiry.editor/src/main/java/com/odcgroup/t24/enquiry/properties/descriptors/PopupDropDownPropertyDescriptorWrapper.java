package com.odcgroup.t24.enquiry.properties.descriptors;

import org.eclipse.ui.views.properties.IPropertyDescriptor;

/**
 * TODO: Document me!
 *
 * @author satishnangi
 *
 */
public class PopupDropDownPropertyDescriptorWrapper extends AbstractPropertyDescriptorWrapper {
    public static String POPUP_DROPDOWN_DISPLAY_NAME = "Close" ; 
	/**
	 * @param descriptor
	 */
	public PopupDropDownPropertyDescriptorWrapper(IPropertyDescriptor descriptor) {
		super(descriptor);
	}
	@Override
	public String getDisplayName() {
		return POPUP_DROPDOWN_DISPLAY_NAME;
	}
}
