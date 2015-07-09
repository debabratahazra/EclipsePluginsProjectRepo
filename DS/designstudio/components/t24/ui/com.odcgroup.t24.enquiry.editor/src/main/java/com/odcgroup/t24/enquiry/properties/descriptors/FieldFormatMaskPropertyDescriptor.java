package com.odcgroup.t24.enquiry.properties.descriptors;

import org.eclipse.ui.views.properties.IPropertyDescriptor;

/**
 * TODO: Document me!
 *
 * @author mumesh
 *
 */
public class FieldFormatMaskPropertyDescriptor extends AbstractPropertyDescriptorWrapper {


	public static String FORMAT_MASK = "Format Mask";

	/**
	 * @param descriptor
	 */
	public FieldFormatMaskPropertyDescriptor(IPropertyDescriptor descriptor) {
		super(descriptor);

	}

	@Override
	public String getDisplayName() {
		return FORMAT_MASK;
	}

}
