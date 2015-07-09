package com.odcgroup.workbench.migration.ui;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class MigrationUICore extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.workbench.migration.ui";

	// The shared instance
	private static MigrationUICore plugin;
	
	/**
	 * The constructor
	 */
	public MigrationUICore() {
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
	public static MigrationUICore getDefault() {
		return plugin;
	}

}
