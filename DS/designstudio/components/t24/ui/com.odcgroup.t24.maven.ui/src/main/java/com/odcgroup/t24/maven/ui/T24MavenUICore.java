package com.odcgroup.t24.maven.ui;

import org.osgi.framework.BundleContext;

import com.odcgroup.workbench.core.AbstractActivator;

public class T24MavenUICore extends AbstractActivator {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.t24.maven.ui";
	
	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static T24MavenUICore getDefault() {
		return (T24MavenUICore) getDefault(T24MavenUICore.class);
	}


	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.core.AbstractActivator#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
	}


	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.core.AbstractActivator#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
	}
	
}
