package com.odcgroup.iris.importer;

import com.odcgroup.t24.application.importer.IApplicationSelector;
import com.odcgroup.t24.common.importer.IT24ImportModel;
import com.odcgroup.t24.enquiry.importer.IEnquirySelector;
import com.odcgroup.t24.version.importer.IVersionSelector;

/**
 */
public interface IIRISImporter extends IT24ImportModel {
	
	IApplicationSelector getApplicationSelector();
	
	IEnquirySelector getEnquirySelector();
	
	IVersionSelector getVersionSelector();
	
	boolean isSelectionEmpty();
}
