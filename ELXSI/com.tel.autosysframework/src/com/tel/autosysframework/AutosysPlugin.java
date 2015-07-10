package com.tel.autosysframework;


import org.eclipse.jface.resource.ImageDescriptor;

import org.osgi.framework.BundleContext;



public class AutosysPlugin
extends org.eclipse.ui.plugin.AbstractUIPlugin
{
	public static final String PLUGIN_ID = "com.tel.autosysframework.plugin";

	private static AutosysPlugin singleton;
	
	/**
	 * Constructor
	 */
	public AutosysPlugin(){
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		singleton = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		singleton = null;
		super.stop(context);
	}
	public static AutosysPlugin getDefault(){
		return singleton;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

}
