package com.odcgroup.page.external.mdf;

import com.odcgroup.page.ui.request.MultipleWidgetCreateRequest;
import com.odcgroup.page.ui.request.PageUIRequestConstants;

/**
 * Request when dropping MDF element in a Table/Tree
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class MdfTableRequest extends MultipleWidgetCreateRequest {
	
	/**
	 * Creates a new MdfTableRequest.
	 */
	public MdfTableRequest() {
		setType(PageUIRequestConstants.REQ_MULTIPLE_CREATE_IN_TABLE);
	}
	
}
