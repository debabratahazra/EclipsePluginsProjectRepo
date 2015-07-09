package com.odcgroup.tap.mdf.validation;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.tap.mdf.validation"; //$NON-NLS-1$


	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}
	
    public static IStatus newStatus(String message, int severity) {
        return new Status(severity, PLUGIN_ID, -1, message, null);
    }

    public static IStatus newStatus(String message, Throwable t) {
        return new Status(IStatus.ERROR, PLUGIN_ID, -1, message, t);
    }

    public static IStatus newStatus(Throwable t) {
        return newStatus(t.toString(), t);
    }

}
