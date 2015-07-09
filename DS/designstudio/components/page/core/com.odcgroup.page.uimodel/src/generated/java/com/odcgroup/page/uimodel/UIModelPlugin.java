package com.odcgroup.page.uimodel;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class UIModelPlugin extends Plugin {

	/** The plug-in ID. */
	public static final String PLUGIN_ID = "com.odcgroup.page.uimodel";

	/** The shared instance. */
	private static UIModelPlugin plugin;
	
	/**
	 * The constructor
	 */
	public UIModelPlugin() {
		plugin = this;
	}
	
	/**
	 * Called when the Plugin is started.
	 * 
	 * @param context The BundleContext
	 * @throws Exception Thrown if an error occurs
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
	}

	/**
	 * Called when the Plugin is stopped.
	 * 
	 * @param context The BundleContext
	 * @throws Exception Thrown if an error occurs
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
	public static UIModelPlugin getDefault() {
		return plugin;
	}

}
