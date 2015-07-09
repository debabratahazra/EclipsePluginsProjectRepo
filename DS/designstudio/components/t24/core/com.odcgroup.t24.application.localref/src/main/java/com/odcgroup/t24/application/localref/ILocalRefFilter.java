package com.odcgroup.t24.application.localref;

import com.odcgroup.t24.common.importer.IModelFilter;
import com.odcgroup.t24.server.external.model.LocalRefDetail;

/**
 * @author ssreekanth
 *
 */
public interface ILocalRefFilter extends IModelFilter<LocalRefDetail>{

	/**
	 * @return
	 */
	String getName();

	/**
	 * @param name
	 */
	void setName(String name);
}
