package com.odcgroup.t24.application.importer;

import com.odcgroup.t24.common.importer.IModelFilter;
import com.odcgroup.t24.server.external.model.ApplicationDetail;

public interface IApplicationFilter extends IModelFilter<ApplicationDetail>{

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
	String getProduct();

	/**
	 * @param product
	 */
	void setProduct(String module);
	
	/**
	 * @return
	 */
	String getName();

	/**
	 * @param name
	 */
	void setName(String name);
	
	void setDescription(String description);
	/**
	 * @return description
	 */
	String getDescription();
}
