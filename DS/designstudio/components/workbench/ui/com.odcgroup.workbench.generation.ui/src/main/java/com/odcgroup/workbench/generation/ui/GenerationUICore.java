package com.odcgroup.workbench.generation.ui;

import org.osgi.framework.BundleContext;

import com.odcgroup.workbench.ui.AbstractUIActivator;

/**
 * The activator class controls the plug-in life cycle
 */
public class GenerationUICore extends AbstractUIActivator {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.workbench.generation.ui";

	// the messages resource bundle
    private static final String BUNDLE_NAME = "com.odcgroup.workbench.generation.ui.messages"; //$NON-NLS-1$

	// The shared instance
	private static GenerationUICore plugin;
	
	/**
	 * The constructor
	 */
	public GenerationUICore() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
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
	public static GenerationUICore getDefault() {
		return plugin;
	}

	@Override
	protected String getResourceBundleName() {
		return BUNDLE_NAME;
	}

}
