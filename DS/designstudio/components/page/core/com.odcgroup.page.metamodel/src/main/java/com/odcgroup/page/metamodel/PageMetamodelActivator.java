package com.odcgroup.page.metamodel;

import org.osgi.framework.BundleContext;

import com.odcgroup.page.metamodel.display.DisplayFormatUtils;
import com.odcgroup.workbench.core.AbstractActivator;

/**
 * The MetaModelPlugin class controls the plug-in life cycle.
 * 
 * @author Gary Hayes
 */
public class PageMetamodelActivator extends AbstractActivator {

	/** The plug-in ID */
	public static final String PLUGIN_ID = "com.odcgroup.page.metamodel";	

	/**
	 * Called when the Plugin is started.
	 * 
	 * @param context The BundleContext
	 * @throws Exception Thrown if an error occurs
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		DisplayFormatUtils.startDisplayFormat(getBundle());
	}

	/**
	 * Called when the Plugin is stopped.
	 * 
	 * @param context The BundleContext
	 * @throws Exception Thrown if an error occurs
	 */
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		DisplayFormatUtils.stopDisplayFormat();
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static PageMetamodelActivator getDefault() {
		return (PageMetamodelActivator) getDefault(PageMetamodelActivator.class);
	}
	
	

}