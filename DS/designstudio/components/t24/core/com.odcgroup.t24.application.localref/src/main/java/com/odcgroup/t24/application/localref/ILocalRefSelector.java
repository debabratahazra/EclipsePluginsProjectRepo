package com.odcgroup.t24.application.localref;

import java.util.List;
import java.util.Set;

import com.odcgroup.t24.common.importer.IModelSelector;
import com.odcgroup.t24.server.external.model.LocalRefDetail;
import com.odcgroup.t24.server.external.model.internal.T24ServerException;

/**
 * @author ssreekanth
 *
 */
public interface ILocalRefSelector extends IModelSelector {

	/**
	 * @return
	 */
	ILocalRefFilter getFilter();
	
	/**
	 * @return
	 */
	List<LocalRefDetail> getFilteredModels() throws T24ServerException;
	
	/**
	 * @return
	 */
	Set<LocalRefDetail> getSelectedModels();
}
