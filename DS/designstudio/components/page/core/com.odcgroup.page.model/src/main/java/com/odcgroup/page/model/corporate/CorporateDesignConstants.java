package com.odcgroup.page.model.corporate;

import com.odcgroup.page.model.PageModelCore;

/**
 * TODO: Document me!
 *
 * @author atr
 * @since DS 1.40.0
 */
public interface CorporateDesignConstants {
	
	/** The store id must be equals to the plugin id, otherwise it won't work*/
	static final String PROPERTY_STORE_ID = PageModelCore.PLUGIN_ID;
	
	/** */
	static final String PROPERTY_LABEL_HORIZONTAL_ALIGNMENT = "labelHorizontalAlignment";	

	/** */
	static final String PROPERTY_FIELD_HORIZONTAL_ALIGNMENT = "fieldHorizontalAlignment";

	/** */
	static final String PROPERTY_TABLE_PAGE_SIZE = "tablePageSize";

	/** The Lead alignment. */
	static final String ALIGN_LEAD_NAME = "Lead";

	/** */
	static final String ALIGN_LEAD_VALUE = "lead";

	/** The Trail alignment. */
	static final String ALIGN_TRAIL_NAME = "Trail";
	
	/** */
	static final String ALIGN_TRAIL_VALUE = "trail";
	
	/** The Center alignment. */
	static final String ALIGN_CENTER_NAME = "Center";
	/** */
	
	static final String ALIGN_CENTER_VALUE = "center";
	
	/** */
	static final String ALIGN_LABEL_DEFAULT_VALUE = ALIGN_LEAD_VALUE;

	/** */
	static final String ALIGN_FIELD_DEFAULT_VALUE = ALIGN_LEAD_VALUE;

	/** the default table/tree page size */
	static final int TABLE_PAGE_SIZE_DEFAULT_VALUE = 20;

}
