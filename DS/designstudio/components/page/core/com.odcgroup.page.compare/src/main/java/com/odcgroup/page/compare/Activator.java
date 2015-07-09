package com.odcgroup.page.compare;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	/** The plug-in ID. */
	public static final String PLUGIN_ID = "com.odcgroup.page.compare";

	/** The shared instance. */
	private static Activator plugin;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	/**
	 * Starts the plugin.
	 * 
	 * @param context the BundleContext
	 * @throws Exception
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/**
	 * Stops the plugin.
	 * 
	 * @param context the BundleContext
	 * @throws Exception
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
	public static Activator getDefault() {
		return plugin;
	}
}