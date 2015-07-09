package com.odcgroup.page.model.corporate;

import com.odcgroup.page.model.PageModelCore;

/**
 * TODO: Document me!
 *
 * @author atr
 * @since DS 1.40.0
 */
public interface CorporateImagesConstants {
	
	/** The store id must be equals to the plugin id, otherwise it won't work*/
	static final String PROPERTY_STORE_ID = PageModelCore.PLUGIN_ID;
	
	/** the property that contains all image descriptors */
	static final String PROPERTY_IMAGE_DESCRIPTORS = "image_descriptors";
	
}
