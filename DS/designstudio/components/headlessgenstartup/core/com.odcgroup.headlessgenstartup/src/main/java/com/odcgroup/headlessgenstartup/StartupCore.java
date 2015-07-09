package com.odcgroup.headlessgenstartup;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

import ch.vorburger.el.ELStandaloneSetup;

import com.odcgroup.domain.DomainStandaloneSetup;
import com.odcgroup.menu.model.MenuStandaloneSetup;
import com.odcgroup.page.PageStandaloneSetup;
import com.odcgroup.workbench.el.DSELStandaloneSetup;

/**
 * The activator class controls the plug-in life cycle
 */
public class StartupCore extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.headlessgenstartup"; //$NON-NLS-1$

	// The shared instance
	private static StartupCore plugin;
	
	/**
	 * The constructor
	 */
	public StartupCore() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		DomainStandaloneSetup.doSetup();
		MenuStandaloneSetup.doSetup();
		PageStandaloneSetup.doSetup();
		ELStandaloneSetup.doSetup();
		DSELStandaloneSetup.doSetup();
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
	public static StartupCore getDefault() {
		return plugin;
	}

}
