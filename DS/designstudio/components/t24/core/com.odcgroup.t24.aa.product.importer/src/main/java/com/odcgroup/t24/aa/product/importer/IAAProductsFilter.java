package com.odcgroup.t24.aa.product.importer;

import com.odcgroup.t24.common.importer.IModelFilter;
import com.odcgroup.t24.server.external.model.AAProductsDetails;

/**
 * TODO: Document me!
 *
 * @author hdebabrata
 *
 */
public interface IAAProductsFilter extends IModelFilter<AAProductsDetails> {
	
	/**
	 * @return
	 */
	String getName();

	/**
	 * @param name
	 */
	void setName(String name);

}
