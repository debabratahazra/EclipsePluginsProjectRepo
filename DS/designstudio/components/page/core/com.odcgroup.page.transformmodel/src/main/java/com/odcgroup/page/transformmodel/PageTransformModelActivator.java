package com.odcgroup.page.transformmodel;

import org.osgi.framework.BundleContext;

import com.odcgroup.page.transformmodel.namespaces.NamespaceFacilityUtils;
import com.odcgroup.workbench.core.AbstractActivator;

/**
 * The activator class controls the plug-in life cycle
 */
public class PageTransformModelActivator extends AbstractActivator {

	/** The plug-in ID. */
	public static final String PLUGIN_ID = "com.odcgroup.page.transformmodel";

	/** The shared instance. */
	private static PageTransformModelActivator plugin;
	
	/**
	 * The constructor
	 */
	public PageTransformModelActivator() {
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
		NamespaceFacilityUtils.startNamespaceFacility();
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
		NamespaceFacilityUtils.stopNamespaceFacility();
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static PageTransformModelActivator getDefault() {
		return plugin;
	}

}