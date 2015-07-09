package com.odcgroup.ocs.server.external;

import org.osgi.framework.BundleContext;

import com.odcgroup.workbench.core.AbstractActivator;

public class OcsServerExternalCore extends AbstractActivator {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.ocs.server.external";  //$NON-NLS-1$

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
	}

	public static OcsServerExternalCore getDefault() {
		return (OcsServerExternalCore) getDefault(OcsServerExternalCore.class);
	}

}
