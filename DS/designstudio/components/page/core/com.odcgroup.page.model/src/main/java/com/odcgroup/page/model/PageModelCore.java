package com.odcgroup.page.model;

import org.osgi.framework.BundleContext;

import com.odcgroup.page.model.corporate.CorporateDesignUtils;
import com.odcgroup.page.model.corporate.CorporateImagesUtils;
import com.odcgroup.workbench.core.AbstractActivator;

/**
 * The activator class controls the plug-in life cycle
 */
public class PageModelCore extends AbstractActivator {

	/** The plug-in ID. */
	public static final String PLUGIN_ID = "com.odcgroup.page.model";

	/** The shared instance. */
	private static PageModelCore plugin;
	
	/**
	 * The constructor
	 */
	public PageModelCore() {
		plugin = this;
	}

	/**
	 * Starts the Plugin.
	 * 
	 * @param context The BundleContext
	 * @throws Exception
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		CorporateDesignUtils.installCorporateDesign();
		CorporateImagesUtils.installCorporateImages();
	}

	/**
	 * Stops the Plugin.
	 * 
	 * @param context The BundleContext
	 * @throws Exception
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
		CorporateDesignUtils.uninstallCorporateDesign();
		CorporateImagesUtils.uninstallCorporateImages();
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static PageModelCore getDefault() {
		return plugin;
	}

}