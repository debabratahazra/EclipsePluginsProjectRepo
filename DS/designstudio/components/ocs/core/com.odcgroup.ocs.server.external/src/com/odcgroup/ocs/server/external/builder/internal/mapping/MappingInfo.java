package com.odcgroup.ocs.server.external.builder.internal.mapping;

import java.io.File;

/**
 * Interface of mappers which know where resource should be located in 
 * the server.
 * @author yan
 */
public interface MappingInfo {

	/**
	 * Return the File instance referencing the file on the server. If no
	 * mapping can be found, return null.
	 */
	public File getTarget(String serverFolder, String projectName, String file);
	
}
