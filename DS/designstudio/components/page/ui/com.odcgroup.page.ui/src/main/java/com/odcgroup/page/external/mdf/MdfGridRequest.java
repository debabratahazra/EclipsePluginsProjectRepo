package com.odcgroup.page.external.mdf;

import com.odcgroup.page.ui.request.MultipleWidgetCreateRequest;
import com.odcgroup.page.ui.request.PageUIRequestConstants;

/**
 * Request when dropping MDF element in a grid widget
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class MdfGridRequest extends MultipleWidgetCreateRequest {
	
	/**
	 * Creates a new MdfGridRequest.
	 */
	public MdfGridRequest() {
		setType(PageUIRequestConstants.REQ_MULTIPLE_CREATE_IN_GRID);
	}
	
}
