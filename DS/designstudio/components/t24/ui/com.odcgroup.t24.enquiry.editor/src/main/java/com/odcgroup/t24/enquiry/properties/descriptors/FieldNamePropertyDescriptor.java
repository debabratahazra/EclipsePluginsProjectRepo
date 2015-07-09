package com.odcgroup.t24.enquiry.properties.descriptors;

import org.eclipse.ui.views.properties.IPropertyDescriptor;

/**
 * TODO: Document me!
 * 
 * @author mumesh
 * 
 */
public class FieldNamePropertyDescriptor extends AbstractPropertyDescriptorWrapper {

	public static String FIELD_NAME = "Field Name";

	/**
	 * @param descriptor
	 */
	public FieldNamePropertyDescriptor(IPropertyDescriptor descriptor) {
		super(descriptor);

	}

	@Override
	public String getDisplayName() {
		return FIELD_NAME;
	}
}
