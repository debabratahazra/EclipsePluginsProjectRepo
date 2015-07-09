package com.odcgroup.ocs.server.external.builder.internal.mapping;

import java.io.File;

/**
 * Mapping class for the translation without wui block 
 */
public class TranslationOnlyMappingInfo implements MappingInfo {

	private String location;

	public TranslationOnlyMappingInfo(String location) {
		this.location = location;
	}
	
	@Override
	public File getTarget(String serverFolder, String projectName, String file) {
		if (file.contains("/messages") && file.endsWith(".xml")) {
			return new File(serverFolder + File.separator + 
					this.location + File.separator +
					"nls-ds" + File.separator + file);
		} else if (file.endsWith(".nlspatch")){
			return NotDeployedFile.INSTANCE;
		} else {
			return null;
		}
	}
	
}
