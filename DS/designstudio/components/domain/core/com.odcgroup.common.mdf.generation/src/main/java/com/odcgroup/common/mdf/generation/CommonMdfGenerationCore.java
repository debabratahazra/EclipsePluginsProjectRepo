package com.odcgroup.common.mdf.generation;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class CommonMdfGenerationCore implements BundleActivator {

	public static final String PLUGIN_ID = "com.odcgroup.common.mdf.generation";
	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		CommonMdfGenerationCore.context = bundleContext;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		CommonMdfGenerationCore.context = null;
	}

}
