package com.odcgroup.page.external.mdf;

import com.odcgroup.page.ui.request.MultipleWidgetCreateRequest;
import com.odcgroup.page.ui.request.PageUIRequestConstants;

/**
 * @author pkk
 *
 */
public class MdfMatrixContentBoxRequest extends MultipleWidgetCreateRequest {
	
	/**
	 * Creates a new MdfMatrixContentCellRequest.
	 */
	public MdfMatrixContentBoxRequest() {
		setType(PageUIRequestConstants.REQ_MULTIPLE_CREATE_IN_MATRIXCELLBOX);
	}

}
