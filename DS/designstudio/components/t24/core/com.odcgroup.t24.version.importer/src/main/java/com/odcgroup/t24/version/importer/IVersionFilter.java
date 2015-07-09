package com.odcgroup.t24.version.importer;

import com.odcgroup.t24.common.importer.IModelFilter;
import com.odcgroup.t24.server.external.model.VersionDetail;

public interface IVersionFilter extends IModelFilter<VersionDetail>{

	/**
	 * @return
	 */
	String getApplication();

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
	
	void setDescription(String description);
	/**
	 * @return description
	 */
	String getDescription();
}
