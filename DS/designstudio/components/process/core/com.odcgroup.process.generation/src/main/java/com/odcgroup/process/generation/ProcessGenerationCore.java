package com.odcgroup.process.generation;

import com.odcgroup.workbench.core.AbstractActivator;

public class ProcessGenerationCore extends AbstractActivator {
	// the messages resource bundle
    private static final String BUNDLE_NAME = "com.odcgroup.process.generation.messages"; //$NON-NLS-1$

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.process.generation";

	@Override
	protected String getResourceBundleName() {
		return BUNDLE_NAME;
	}
	
	public static ProcessGenerationCore getDefault() {
		ProcessGenerationCore act = (ProcessGenerationCore) getDefault(ProcessGenerationCore.class);
		return act;
	}
}
