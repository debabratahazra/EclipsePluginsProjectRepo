package com.odcgroup.t24.enquiry.editor.request;

import org.eclipse.gef.Request;

import com.odcgroup.t24.enquiry.enquiry.Enquiry;

/**
 *
 * @author phanikumark
 *
 */
public class CreateFixedSelectionRequest extends Request {
	
	private Enquiry enquiry;
	public static final String CREATE_FIXEDSELECTION = "createFixedSelection" ;
	
	public CreateFixedSelectionRequest(Enquiry enquiry) {
		this.enquiry = enquiry;
		setType(CREATE_FIXEDSELECTION);
	}
	
	public Enquiry getEnquiry() {
		return enquiry;
	}

}
