package com.odcgroup.t24.applicationimport;
import org.osgi.framework.BundleContext;

import com.odcgroup.workbench.core.AbstractActivator;


public class ApplicationIntrospectionActivator extends AbstractActivator {
  
	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.t24.application.model";
	
	@SuppressWarnings("unused")
	private ApplicationIntrospectionActivator plugin;
	
	public void start(BundleContext context) throws Exception {
		plugin = this;
		super.start(context);
	}
	
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}
  
}
