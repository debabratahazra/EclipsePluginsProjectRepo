package com.odcgroup.t24.enquiry.importer;

import com.odcgroup.workbench.core.AbstractActivator;

/**
 * The activator class controls the plug-in life cycle
 */
public class EnquiryImporterCore extends AbstractActivator {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.t24.enquiry.importer"; //$NON-NLS-1$

	public static EnquiryImporterCore getDefault() {
		return (EnquiryImporterCore) getDefault(EnquiryImporterCore.class);
	}
	
}
