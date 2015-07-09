package com.odcgroup.t24.enquiry.properties.descriptors;

import org.eclipse.ui.views.properties.IPropertyDescriptor;

/**
 * TODO: Document me!
 *
 * @author mumesh
 *
 */
public class FieldColumnLabelPropertyDescriptor extends AbstractPropertyDescriptorWrapper {


	public static String COLUMN_LABEL = "Column Label";

	/**
	 * @param descriptor
	 */
	public FieldColumnLabelPropertyDescriptor(IPropertyDescriptor descriptor) {
		super(descriptor);

	}

	@Override
	public String getDisplayName() {
		return COLUMN_LABEL;
	}

}
