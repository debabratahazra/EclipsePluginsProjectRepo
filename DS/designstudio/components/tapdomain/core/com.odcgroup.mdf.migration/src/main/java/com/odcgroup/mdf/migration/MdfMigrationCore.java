package com.odcgroup.mdf.migration;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class MdfMigrationCore extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.mdf.migration";

	// The shared instance
	private static MdfMigrationCore plugin;
	
	/**
	 * The constructor
	 */
	public MdfMigrationCore() {
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
	public static MdfMigrationCore getDefault() {
		return plugin;
	}

}
