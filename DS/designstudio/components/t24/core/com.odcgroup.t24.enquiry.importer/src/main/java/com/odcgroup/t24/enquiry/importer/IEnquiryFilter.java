package com.odcgroup.t24.enquiry.importer;

import com.odcgroup.t24.common.importer.IModelFilter;
import com.odcgroup.t24.server.external.model.EnquiryDetail;

public interface IEnquiryFilter extends IModelFilter<EnquiryDetail>{

	/**
	 * @return
	 */
	//String getApplication();

	/**
	 * @param application
	 */
	void setApplication(String application);
	
	/**
	 * @return
	 */
	String getComponent();

	/**
	 * @param component
	 */
	void setComponent(String component);
	
	/**
	 * @return
	 */
	String getModule();

	/**
	 * @param module
	 */
	void setModule(String module);
	
	/**
	 * @return
	 */
	String getName();

	/**
	 * @param name
	 */
	void setName(String name);
	
	
	public String getDescription();
	
	public void setDescription(String description);
}
