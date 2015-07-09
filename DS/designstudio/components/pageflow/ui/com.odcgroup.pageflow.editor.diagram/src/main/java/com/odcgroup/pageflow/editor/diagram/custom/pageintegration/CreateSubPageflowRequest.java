package com.odcgroup.pageflow.editor.diagram.custom.pageintegration;

import org.eclipse.gef.requests.CreateRequest;

import com.odcgroup.pageflow.model.Pageflow;

/**
 * @author pkk
 *
 */
public class CreateSubPageflowRequest extends CreateRequest {
	
	private Pageflow subpageflow;

	/**
	 * @return
	 */
	public Pageflow getSubpageflow() {
		return subpageflow;
	}

	/**
	 * @param subpageflow
	 */
	public void setSubpageflow(Pageflow subpageflow) {
		this.subpageflow = subpageflow;
	}
	
	

}
