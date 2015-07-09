package com.odcgroup.workbench.compare.gmf;

import org.osgi.framework.BundleContext;

import com.odcgroup.workbench.core.AbstractActivator;

public class WorkbenchGMFCompareActivator extends AbstractActivator {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.workbench.compare.gmf";

	// The shared instance
	private static WorkbenchGMFCompareActivator plugin;
	
	/**
	 * The constructor
	 */
	public WorkbenchGMFCompareActivator() {
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
	public static WorkbenchGMFCompareActivator getDefault() {
		return plugin;
	}

}
