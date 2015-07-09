package com.odcgroup.t24.mdf.editor.ui.core;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * @author ssreekanth
 * The activator class controls the plug-in life cycle
 */
public class T24MdfEditorCore extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.t24.mdf.editor"; //$NON-NLS-1$

	// The shared instance
	private static T24MdfEditorCore plugin;
	
	/**
	 * The constructor
	 */
	public T24MdfEditorCore() {
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
	public static T24MdfEditorCore getDefault() {
		return plugin;
	}

}
