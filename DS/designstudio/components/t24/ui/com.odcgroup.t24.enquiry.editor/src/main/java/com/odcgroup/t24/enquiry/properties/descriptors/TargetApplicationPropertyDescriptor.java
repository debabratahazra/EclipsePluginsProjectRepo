package com.odcgroup.t24.enquiry.properties.descriptors;

import org.eclipse.ui.views.properties.IPropertyDescriptor;

/**
 *
 * @author mumesh
 *
 */
public class TargetApplicationPropertyDescriptor extends FileNamePropertyDescriptorWrapper {

	/**
	 * @param descriptor
	 * @param model
	 * @param eObjectSearch
	 * @param globalDescriptionLabelProvider
	 */
	public TargetApplicationPropertyDescriptor(IPropertyDescriptor descriptor) {
		super(descriptor);
	}

	protected boolean selectApplicationWithFullName() {
		return false;
	}

}