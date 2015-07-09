package com.odcgroup.t24.enquiry.importer.ui;

import org.osgi.framework.BundleContext;

import com.odcgroup.workbench.ui.AbstractUIActivator;

/**
 * The activator class controls the plug-in life cycle
 */
public class EnquiryImporterUICore extends AbstractUIActivator {

	// The shared instance
	private static EnquiryImporterUICore plugin;

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.t24.enquiry.importer.ui"; //$NON-NLS-1$

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static EnquiryImporterUICore getDefault() {
		return plugin;
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * The constructor
	 */
	public EnquiryImporterUICore() {
	}
}
