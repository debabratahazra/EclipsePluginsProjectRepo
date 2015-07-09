package com.odcgroup.ocs.server;

import org.osgi.framework.BundleContext;

import com.odcgroup.ocs.server.preferences.DSServerPreferenceManager;
import com.odcgroup.workbench.core.AbstractActivator;
import com.odcgroup.workbench.core.preferences.ProjectPreferences;

/**
 * The activator class controls the plug-in life cycle
 */
public class ServerCore extends AbstractActivator {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.ocs.server";  //$NON-NLS-1$

	// The shared instance
	private static ServerCore plugin;

	// Server preference manager
	private DSServerPreferenceManager ocsServerPreferenceManager;

	/**
	 * The constructor
	 */
	public ServerCore() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		ocsServerPreferenceManager = new DSServerPreferenceManager();
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
	public static ServerCore getDefault() {
		return plugin;
	}

	/**
	 * @return the preference manager to access preference values
	 * and register to preference changes
	 */
	public DSServerPreferenceManager getOCSServerPreferenceManager() {
		return ocsServerPreferenceManager;
	}

	/**
	 * @return the prefs
	 */
	public ProjectPreferences getProjectPreferences() {
		return new ProjectPreferences(null, PLUGIN_ID);
	}

}
