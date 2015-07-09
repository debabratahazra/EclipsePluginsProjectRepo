package com.odcgroup.t24.enquiry.editor;

import org.osgi.framework.Bundle;

import com.odcgroup.t24.enquiry.ui.EnquiryExecutableExtensionFactory;

/**
 *
 * @author phanikumark
 *
 */
public class EnquiryEditorExecutableExtensionFactory extends EnquiryExecutableExtensionFactory {
	
	@Override
	protected Bundle getBundle() {
		return Activator.getDefault().getBundle();
	}

}
