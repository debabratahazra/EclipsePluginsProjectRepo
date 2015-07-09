package com.odcgroup.service.gen.t24;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class ServiceGenerationT24Core extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.service.gen.t24"; //$NON-NLS-1$

	// The shared instance
	private static ServiceGenerationT24Core plugin;
	
	/**
	 * The constructor
	 */
	public ServiceGenerationT24Core() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static ServiceGenerationT24Core getDefault() {
		return plugin;
	}

}
