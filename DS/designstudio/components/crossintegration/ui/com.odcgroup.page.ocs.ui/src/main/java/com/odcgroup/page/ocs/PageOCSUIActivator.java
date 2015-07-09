package com.odcgroup.page.ocs;
import org.eclipse.jface.resource.ImageDescriptor;

import com.odcgroup.workbench.ui.AbstractUIActivator;

/**
 * The activator class controls the plug-in life cycle
 */
public class PageOCSUIActivator extends AbstractUIActivator {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.page.ocs.ui";

	
	/**
	 * @return MessageUICore
	 */
	public static PageOCSUIActivator getDefault() {
		return (PageOCSUIActivator) getDefault(PageOCSUIActivator.class);
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in
	 * relative path
	 * 
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

}
