package com.odcgroup.t24.application.localrefapplication;

import java.util.List;
import java.util.Set;

import com.odcgroup.t24.common.importer.IModelSelector;
import com.odcgroup.t24.server.external.model.LocalRefApplicationDetail;
import com.odcgroup.t24.server.external.model.internal.T24ServerException;

/**
 * TODO: Document me!
 *
 * @author hdebabrata
 *
 */
public interface ILocalRefApplicationSelector extends IModelSelector {

	/**
	 * @return
	 */
	ILocalRefApplicationFilter getFilter();
	
	/**
	 * @return
	 */
	List<LocalRefApplicationDetail> getFilteredModels() throws T24ServerException;
	
	/**
	 * @return
	 */
	Set<LocalRefApplicationDetail> getSelectedModels();
	
}
