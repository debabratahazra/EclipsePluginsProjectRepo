package com.odcgroup.t24.enquiry.importer;

import com.odcgroup.t24.common.importer.IT24ImportModel;

/**
 */
public interface IEnquiryImporter extends IT24ImportModel {
	
	IEnquirySelector getEnquirySelector();

}
