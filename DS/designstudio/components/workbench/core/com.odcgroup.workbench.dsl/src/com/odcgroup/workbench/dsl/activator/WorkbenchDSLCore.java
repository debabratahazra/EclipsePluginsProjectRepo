package com.odcgroup.workbench.dsl.activator;

import org.osgi.framework.BundleContext;

import com.odcgroup.workbench.core.AbstractActivator;

public class WorkbenchDSLCore extends AbstractActivator {
	
	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.workbench.dsl";


	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
	}
	
	public static WorkbenchDSLCore getDefault() {
		return (WorkbenchDSLCore) getDefault(WorkbenchDSLCore.class);
	}

}
