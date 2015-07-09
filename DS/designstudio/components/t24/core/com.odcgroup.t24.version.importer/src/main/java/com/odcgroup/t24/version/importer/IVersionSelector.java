package com.odcgroup.t24.version.importer;

import java.util.List;
import java.util.Set;

import com.odcgroup.t24.common.importer.IModelSelector;
import com.odcgroup.t24.server.external.model.VersionDetail;
import com.odcgroup.t24.server.external.model.internal.T24ServerException;

public interface IVersionSelector extends IModelSelector {

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
	IVersionFilter getFilter();
	
	/**
	 * @return
	 */
	List<VersionDetail> getFilteredModels() throws T24ServerException;
	
	/**
	 * @return
	 */
	Set<VersionDetail> getSelectedModels();
	
	List<String> getDescriptions();
}
