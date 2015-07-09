package com.odcgroup.pageflow.docgen;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class PageflowDocGenerationCore extends AbstractUIPlugin {

	public PageflowDocGenerationCore() {
		// TODO Auto-generated constructor stub
	}

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.pageflow.docgen";

	// The shared instance
	private static PageflowDocGenerationCore plugin;
	


	/**
	 * @see org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
	}

	/**
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
	public static PageflowDocGenerationCore getDefault() {
		return plugin;
	}
}
