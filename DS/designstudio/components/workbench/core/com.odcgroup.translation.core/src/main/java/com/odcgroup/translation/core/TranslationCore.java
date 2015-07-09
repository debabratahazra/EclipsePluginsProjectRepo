package com.odcgroup.translation.core;

import org.eclipse.core.resources.IProject;
import org.osgi.framework.BundleContext;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import com.odcgroup.translation.core.internal.TranslationManagerRuntime;
import com.odcgroup.workbench.core.AbstractActivator;
import com.odcgroup.workbench.core.logging.LoggingConstants;

/**
 * The activator class controls the plug-in life cycle
 */
public class TranslationCore extends AbstractActivator {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.translation.core";

	// The shared instance
	private static TranslationCore plugin;
	
	// marker used for SLF4J logging to identify the plugin
    public static final Marker LOG_MARKER = createBundleMarker();	
	
    private static final Marker createBundleMarker() {
    	Marker bundleMarker = MarkerFactory.getMarker(PLUGIN_ID);
    	bundleMarker.add(MarkerFactory.getMarker(LoggingConstants.IS_BUNDLE_MARKER));
    	return bundleMarker;
    }    
    
	/**
	 * The constructor
	 */
	public TranslationCore() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		TranslationManagerRuntime.install();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		TranslationManagerRuntime.uninstall();
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static TranslationCore getDefault() {
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
	 * @returns the global translation manager   
	 * 
	 */
	public static final ITranslationManager getTranslationManager(IProject project) {
		return TranslationManagerRuntime.getManager(project);
	}	

}
