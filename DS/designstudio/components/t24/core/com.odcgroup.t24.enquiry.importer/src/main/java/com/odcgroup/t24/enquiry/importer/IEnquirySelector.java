package com.odcgroup.t24.enquiry.importer;

import java.util.List;
import java.util.Set;

import com.odcgroup.t24.common.importer.IModelSelector;
import com.odcgroup.t24.server.external.model.EnquiryDetail;
import com.odcgroup.t24.server.external.model.internal.T24ServerException;

public interface IEnquirySelector extends IModelSelector {

	/**
	 * @return
	 */
	List<String> getModules();
	
	/**
	 * @return
	 */
	List<String> getApplications();
	
	/**
	 * @return
	 */
	List<String> getComponents();
	
	/**
	 * @return
	 */
	IEnquiryFilter getFilter();
	
	/**
	 * @return
	 */
	List<EnquiryDetail> getFilteredModels() throws T24ServerException;
	
	/**
	 * @return
	 */
	Set<EnquiryDetail> getSelectedModels();
	
	List<String> getDescriptions();
}
