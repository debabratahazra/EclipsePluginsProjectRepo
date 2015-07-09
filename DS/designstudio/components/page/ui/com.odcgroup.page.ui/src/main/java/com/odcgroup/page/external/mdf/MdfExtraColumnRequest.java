package com.odcgroup.page.external.mdf;

import com.odcgroup.page.ui.request.MultipleWidgetCreateRequest;
import com.odcgroup.page.ui.request.PageUIRequestConstants;

/**
 *
 * @author pkk
 *
 */
public class MdfExtraColumnRequest extends MultipleWidgetCreateRequest {
	
	/**
	 * Creates a new MdfExtraColumnRequest.
	 */
	public MdfExtraColumnRequest() {
		setType(PageUIRequestConstants.REQ_MULTIPLE_CREATE_IN_MATRIXEXTRA);
	}

}
