package com.odcgroup.menu.migration;

import org.osgi.framework.BundleContext;

import com.odcgroup.workbench.core.AbstractActivator;

public class MenuMigrationActivator extends AbstractActivator {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.menu.migration";

	// The shared instance
	private static MenuMigrationActivator plugin;
	
	/**
	 * The constructor
	 */
	public MenuMigrationActivator() {
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
	public static MenuMigrationActivator getDefault() {
		return plugin;
	}



}
