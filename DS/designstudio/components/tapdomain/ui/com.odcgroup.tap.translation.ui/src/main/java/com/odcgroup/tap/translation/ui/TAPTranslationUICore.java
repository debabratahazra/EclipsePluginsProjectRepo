package com.odcgroup.tap.translation.ui;

import org.eclipse.jface.resource.ImageDescriptor;
import org.osgi.framework.BundleContext;

import com.odcgroup.workbench.ui.AbstractUIActivator;

/**
 * The activator class controls the plug-in life cycle
 * 
 * @author atr
 */
public class TAPTranslationUICore extends AbstractUIActivator {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.tap.translation.ui";
	

	// The shared instance
	private static TAPTranslationUICore plugin;

	/**
	 * The constructor
	 */
	public TAPTranslationUICore() {
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static TAPTranslationUICore getDefault() {
		return plugin;
	}

	/**
	 * Retrieves a Boolean value indicating whether tracing is enabled for the
	 * specified debug option.
	 * 
	 * @return Whether tracing is enabled for the debug option of the plug-in.
	 * @param option The debug option for which to determine trace enablement.
	 * 
	 */
	 public static boolean shouldTrace(String option) {
		boolean trace = false;
		if(getDefault().isDebugging()) {
			trace = isTraceOptionEnabled(option);
		}
		return trace;
	}		
	
	/**
	 * Returns an image descriptor for the image file at the given plug-in
	 * relative path
	 * 
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

}
