package com.odcgroup.t24.application.localrefapplication;

import com.odcgroup.t24.common.importer.IModelFilter;
import com.odcgroup.t24.server.external.model.LocalRefApplicationDetail;

/**
 * TODO: Document me!
 *
 * @author hdebabrata
 *
 */
public interface ILocalRefApplicationFilter extends IModelFilter<LocalRefApplicationDetail>{

	/**
	 * @return
	 */
	String getName();

	/**
	 * @param name
	 */
	void setName(String name);

}
