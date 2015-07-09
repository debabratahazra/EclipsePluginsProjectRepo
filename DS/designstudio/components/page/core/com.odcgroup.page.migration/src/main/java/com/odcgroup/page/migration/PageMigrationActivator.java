package com.odcgroup.page.migration;

import org.osgi.framework.BundleContext;

import com.odcgroup.workbench.core.AbstractActivator;

/**
 * The activator class controls the plug-in life cycle
 */
public class PageMigrationActivator extends AbstractActivator {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.page.migration";

	// The shared instance
	private static PageMigrationActivator plugin;
	
	/**
	 * The constructor
	 */
	public PageMigrationActivator() {
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.ui.AbstractUIActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.ui.AbstractUIActivator#stop(org.osgi.framework.BundleContext)
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
	public static PageMigrationActivator getDefault() {
		return plugin;
	}

}
