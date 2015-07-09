package com.odcgroup.pageflow.generation;

import com.odcgroup.workbench.core.AbstractActivator;

public class PageflowGenerationCore extends AbstractActivator {

	public static final String PAGEFLOW_MODEL_CARTRIDGE_EXTENSION_ID = "com.odcgroup.pageflow.generation.model";
	public static final String PAGEFLOW_CODE_CARTRIDGE_EXTENSION_ID = "com.odcgroup.pageflow.generation.code";

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.pageflow.generation";
	
	
	public static PageflowGenerationCore getDefault() {
		return (PageflowGenerationCore) getDefault(PageflowGenerationCore.class);
	}
}
