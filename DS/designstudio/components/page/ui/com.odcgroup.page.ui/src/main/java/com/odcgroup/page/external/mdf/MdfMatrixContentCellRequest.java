package com.odcgroup.page.external.mdf;

import com.odcgroup.page.ui.request.MultipleWidgetCreateRequest;
import com.odcgroup.page.ui.request.PageUIRequestConstants;

/**
 *
 * @author pkk
 *
 */
public class MdfMatrixContentCellRequest extends MultipleWidgetCreateRequest {
	
	/**
	 * Creates a new MdfMatrixContentCellRequest.
	 */
	public MdfMatrixContentCellRequest() {
		setType(PageUIRequestConstants.REQ_MULTIPLE_CREATE_IN_MATRIXCELL);
	}

}
