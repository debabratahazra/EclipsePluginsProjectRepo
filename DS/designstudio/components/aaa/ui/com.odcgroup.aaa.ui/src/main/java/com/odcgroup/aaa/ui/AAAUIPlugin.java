package com.odcgroup.aaa.ui;

import org.osgi.framework.BundleContext;

import com.odcgroup.workbench.ui.AbstractUIActivator;

/**
 * The activator class controls the plug-in life cycle
 */
public class AAAUIPlugin extends AbstractUIActivator {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.aaa.ui";

	// The shared instance
	private static AAAUIPlugin plugin;
	
	/**
	 * The constructor
	 */
	public AAAUIPlugin() {
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
	public static AAAUIPlugin getDefault() {
		return plugin;
	}

	private static String password = "";
	
	public static String getPassword() {
		return AAAUIPlugin.password;
	}

	public static void setPassword(String value) {
		AAAUIPlugin.password = value;
	}
}
