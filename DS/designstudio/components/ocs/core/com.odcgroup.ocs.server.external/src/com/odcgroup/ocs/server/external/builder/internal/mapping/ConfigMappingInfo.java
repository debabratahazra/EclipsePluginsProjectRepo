package com.odcgroup.ocs.server.external.builder.internal.mapping;

import java.io.File;

/**
 * Mapping class for the config source folder
 */
public class ConfigMappingInfo implements MappingInfo {

	private String location;

	public ConfigMappingInfo(String location) {
		this.location = location;
	}
	
	public File getTarget(String serverFolder, String projectName, String file) {
		return new File(serverFolder + File.separator + 
				location + File.separator +
				file);
	}

}
