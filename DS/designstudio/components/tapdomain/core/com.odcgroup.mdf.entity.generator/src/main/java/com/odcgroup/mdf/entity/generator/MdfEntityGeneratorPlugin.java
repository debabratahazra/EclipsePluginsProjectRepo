package com.odcgroup.mdf.entity.generator;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class MdfEntityGeneratorPlugin extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.mdf.entity.generator";

	// The shared instance
	private static MdfEntityGeneratorPlugin plugin;
	
	/**
	 * The constructor
	 */
	public MdfEntityGeneratorPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
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
	public static MdfEntityGeneratorPlugin getDefault() {
		return plugin;
	}

}
