package com.odcgroup.page.transformmodel;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

/**
 * The TransformModelPlugin class controls the plug-in life cycle.
 * 
 * @author Alexandre Jaquet
 */
public class TransformModelPlugin extends Plugin {
	/** The plug-in ID */
	public static final String PLUGIN_ID = "com.odcgroup.page.transformmodel";

	/** The shared instance */
	private static TransformModelPlugin plugin;
	/**
	 * The constructor
	 */
	public TransformModelPlugin() {
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
	public static TransformModelPlugin getDefault() {
		return plugin;
	}

}
