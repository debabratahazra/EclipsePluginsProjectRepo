package com.odcgroup.pageflow.editor.diagram.custom.request;

import org.eclipse.gef.Request;

import com.odcgroup.pageflow.editor.diagram.custom.util.PageflowRequestConstants;

public class ToggleDescriptionLabelRequest extends Request {
	
	/**
	 * 
	 */
	public ToggleDescriptionLabelRequest(){
		super(PageflowRequestConstants.REQ_TOGGLE_DESCRIPTION);		
	}

}
