package com.odcgroup.workbench.editors.properties;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class OFSPropertyPlugIn extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.workbench.editors.gmf.properties";

	// The shared instance
	private static OFSPropertyPlugIn plugin;

	/**
	 * The constructor
	 */
	public OFSPropertyPlugIn() {
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
	}

	/*
	 * (non-Javadoc)
	 * 
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
	public static OFSPropertyPlugIn getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in
	 * relative path.
	 * 
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getBundledImageDescriptor(String path) {
		return AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

	/**
	 * 
	 */
	public void logError(String error) {
		logError(error, null);
	}

	/**
	 * @param throwable
	 *            actual error or null could be passed
	 */
	public void logError(String error, Throwable throwable) {
		if (error == null && throwable != null) {
			error = throwable.getMessage();
		}
		getLog().log(
				new Status(IStatus.ERROR, OFSPropertyPlugIn.PLUGIN_ID,
						IStatus.OK, error, throwable));
		debug(error, throwable);
	}

	/**
	 * 
	 */
	public void logInfo(String message) {
		logInfo(message, null);
	}

	/**
	 * @param throwable
	 *            actual error or null could be passed
	 */
	public void logInfo(String message, Throwable throwable) {
		if (message == null && message != null) {
			message = throwable.getMessage();
		}
		getLog().log(
				new Status(IStatus.INFO, OFSPropertyPlugIn.PLUGIN_ID,
						IStatus.OK, message, throwable));
		debug(message, throwable);
	}

	/**
	 * 
	 */
	private void debug(String message, Throwable throwable) {
		if (!isDebugging()) {
			return;
		}
		if (message != null) {
			System.err.println(message);
		}
		if (throwable != null) {
			throwable.printStackTrace();
		}
	}

}
