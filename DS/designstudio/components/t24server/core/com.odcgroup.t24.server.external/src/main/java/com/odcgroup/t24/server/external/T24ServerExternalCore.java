package com.odcgroup.t24.server.external;

import org.osgi.framework.BundleContext;

import com.odcgroup.workbench.core.AbstractActivator;

public class T24ServerExternalCore extends AbstractActivator {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.t24.server.external";
	
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
	}

	public static T24ServerExternalCore getDefault() {
		return (T24ServerExternalCore) getDefault(T24ServerExternalCore.class);
	}

}
