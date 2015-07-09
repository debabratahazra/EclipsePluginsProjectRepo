package com.odcgroup.t24.enquiry.properties.descriptors;

import org.eclipse.ui.views.properties.IPropertyDescriptor;

/**
 * @author satishnangi
 *
 */
public class EnquiryPropertyDescriptorWrapper extends AbstractPropertyDescriptorWrapper {
    private String displayName;
	/**
	 * @param descriptor
	 */
	public EnquiryPropertyDescriptorWrapper(IPropertyDescriptor descriptor ,String displayName) {
		super(descriptor);
		this.displayName = displayName;
	}
  
	@Override
	public String getDisplayName() {
		if(displayName !=null){
			return displayName;
		}
		return super.getDisplayName();
	}
}
