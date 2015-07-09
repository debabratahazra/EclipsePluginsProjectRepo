package com.odcgroup.ocs.server.external.builder.internal.mapping;

import java.io.File;

/**
 * Mapping class for the config source folder which support customization.<p>
 * If the same config is in the custom folder, deploys it to the custom folder 
 * instead of using the standard location.
 */
public class CustomizableConfigMappingInfo implements MappingInfo {

	private final static String CUSTOM_FOLDER = "custom";
	
	private String location;

	public CustomizableConfigMappingInfo(String location) {
		this.location = location;
	}
	
	public File getTarget(String serverFolder, String projectName, String file) {
		File customizedFile = new File(serverFolder + File.separator + 
				CUSTOM_FOLDER + File.separator +
				location + File.separator +
				file);
		if (customizedFile.exists()) {
			return customizedFile;
		} else {
			return new File(serverFolder + File.separator + 
					location + File.separator +
					file);
		}
	}

}
