package com.odcgroup.t24.enquiry.editor.request;

import org.eclipse.gef.Request;

import com.odcgroup.t24.enquiry.enquiry.Enquiry;

/**
 *
 * @author phanikumark
 *
 */
public class ManageFieldsRequest extends Request {
	
	private Enquiry enquiry;
	public static final String MANAGE_FIELDS = "manageFields" ;
	
	public ManageFieldsRequest(Enquiry enquiry) {
		this.enquiry = enquiry;
		setType(MANAGE_FIELDS);
	}
	
	public Enquiry getEnquiry() {
		return enquiry;
	}

}
