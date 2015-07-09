package com.odcgroup.page.docgen;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class PageDocGenerationCore extends AbstractUIPlugin {

	public PageDocGenerationCore() {
		// TODO Auto-generated constructor stub
	}

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.page.docgen";

	// The shared instance
	private static PageDocGenerationCore plugin;
	


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
	public static PageDocGenerationCore getDefault() {
		return plugin;
	}
}
