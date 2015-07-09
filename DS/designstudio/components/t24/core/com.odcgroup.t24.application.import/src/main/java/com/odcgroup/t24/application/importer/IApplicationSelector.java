package com.odcgroup.t24.application.importer;

import java.util.List;
import java.util.Set;

import com.odcgroup.t24.common.importer.IModelSelector;
import com.odcgroup.t24.server.external.model.ApplicationDetail;
import com.odcgroup.t24.server.external.model.internal.T24ServerException;

public interface IApplicationSelector extends IModelSelector {

	/**
	 * @return
	 */
	List<String> getProducts();
	
	/**
	 * @return
	 */
	List<String> getComponents();
	
	/**
	 * @return
	 */
	IApplicationFilter getFilter();
	
	/**
	 * @return
	 */
	List<ApplicationDetail> getFilteredModels() throws T24ServerException;
	
	/**
	 * @return
	 */
	Set<ApplicationDetail> getSelectedModels();
	
	List<String> getAppDescriptions();
}
