package com.odcgroup.ocs.packager.ui;

import org.osgi.framework.BundleContext;

import com.odcgroup.workbench.ui.AbstractUIActivator;

public class OcsPackagerUICore extends AbstractUIActivator {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.ocs.packager.ui";

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
	}

	/**
	 * @return
	 */
	public static OcsPackagerUICore getDefault() {
		return (OcsPackagerUICore) getDefault(OcsPackagerUICore.class);
	}
	

}
