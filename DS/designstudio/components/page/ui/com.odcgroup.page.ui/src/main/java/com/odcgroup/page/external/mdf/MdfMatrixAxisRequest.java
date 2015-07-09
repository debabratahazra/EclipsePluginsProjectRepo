package com.odcgroup.page.external.mdf;

import com.odcgroup.page.ui.request.MultipleWidgetCreateRequest;
import com.odcgroup.page.ui.request.PageUIRequestConstants;

/**
 *
 * @author pkk
 *
 */
public class MdfMatrixAxisRequest extends MultipleWidgetCreateRequest {
	
	/**
	 * Creates a new MdfMatrixAxisRequest.
	 */
	public MdfMatrixAxisRequest() {
		setType(PageUIRequestConstants.REQ_MULTIPLE_CREATE_IN_MATRIXAXIS);
	}

}
