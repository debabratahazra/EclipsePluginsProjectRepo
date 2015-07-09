package com.odcgroup.t24.aa.product.importer;

import java.util.List;
import java.util.Set;

import com.odcgroup.t24.common.importer.IModelSelector;
import com.odcgroup.t24.server.external.model.AAProductsDetails;
import com.odcgroup.t24.server.external.model.internal.T24ServerException;

/**
 * TODO: Document me!
 *
 * @author hdebabrata
 *
 */
public interface IAAProductsSelector extends IModelSelector {
	
	/**
	 * @return
	 */
	IAAProductsFilter getFilter();
	
	/**
	 * @return
	 */
	List<AAProductsDetails> getFilteredModels() throws T24ServerException;
	
	/**
	 * @return
	 */
	Set<AAProductsDetails> getSelectedModels();

}
