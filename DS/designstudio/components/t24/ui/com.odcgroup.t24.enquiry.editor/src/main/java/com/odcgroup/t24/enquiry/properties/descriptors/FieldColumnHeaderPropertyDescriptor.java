package com.odcgroup.t24.enquiry.properties.descriptors;

import org.eclipse.ui.views.properties.IPropertyDescriptor;

/**
 * TODO: Document me!
 *
 * @author mumesh
 *
 */
public class FieldColumnHeaderPropertyDescriptor extends AbstractPropertyDescriptorWrapper {


	public static String COLUMN_HEADER = "Header";

	/**
	 * @param descriptor
	 */
	public FieldColumnHeaderPropertyDescriptor(IPropertyDescriptor descriptor) {
		super(descriptor);

	}

	@Override
	public String getDisplayName() {
		return COLUMN_HEADER;
	}

}
