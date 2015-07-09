package com.odcgroup.documentation.generation.ui;

import com.odcgroup.workbench.ui.AbstractUIActivator;

/**
 * The activator class controls the plug-in life cycle
 */
public class DocumentationUICore extends AbstractUIActivator {
	
	// the messages resource bundle
    private static final String BUNDLE_NAME = "com.odcgroup.documentation.generation.ui.messages"; //$NON-NLS-1$	

   

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.documentation.generation.ui";

	
	/**
	 * @return
	 */
	public static DocumentationUICore getDefault() {
		return (DocumentationUICore) getDefault(DocumentationUICore.class);
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.ui.AbstractUIActivator#getResourceBundleName()
	 */
	@Override
	protected String getResourceBundleName() {
		return BUNDLE_NAME;
	}
	
	

}
