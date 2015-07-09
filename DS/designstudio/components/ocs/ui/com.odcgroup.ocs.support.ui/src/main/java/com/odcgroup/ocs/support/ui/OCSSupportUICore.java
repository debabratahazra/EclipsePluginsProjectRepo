package com.odcgroup.ocs.support.ui;

import org.osgi.framework.BundleContext;

import com.odcgroup.workbench.ui.AbstractUIActivator;
import com.odcgroup.workbench.ui.maven.M2EclipseIntegrationFacade;

/**
 * The activator class controls the plug-in life cycle
 */
public class OCSSupportUICore extends AbstractUIActivator {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.ocs.support.ui";
	
	// the messages resource bundle
    private static final String BUNDLE_NAME = "com.odcgroup.ocs.support.ui.messages";
    
    /*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		
		// DS-5824 Opening DS launches a "Build workspace" automatically.
		// Check if the m2eclipse classpath containers are uptodate
		M2EclipseIntegrationFacade.instance().checkIfClasspathContainerAreUpToDate();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
	}
	
	/**
	 * @return
	 */
	public static OCSSupportUICore getDefault() {
		return (OCSSupportUICore) getDefault(OCSSupportUICore.class);
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.ui.AbstractUIActivator#getResourceBundleName()
	 */
	@Override
	protected String getResourceBundleName() {
		return BUNDLE_NAME;
	}
	
}
