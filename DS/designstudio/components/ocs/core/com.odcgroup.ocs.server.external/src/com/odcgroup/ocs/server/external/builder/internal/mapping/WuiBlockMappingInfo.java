package com.odcgroup.ocs.server.external.builder.internal.mapping;

import java.io.File;

import com.odcgroup.ocs.server.external.builder.internal.delta.SourceFolderEnum;

/**
 * Mapping class for the wui block source folder 
 */
public class WuiBlockMappingInfo implements MappingInfo {

	private static final String WUI_BLOCK_SUFFIX = SourceFolderEnum.WUI_BLOCK.getGenSuffix();
	
	private String location;

	public WuiBlockMappingInfo(String location) {
		this.location = location;
	}
	
	public File getTarget(String serverFolder, String projectName, String file) {
		return new File(serverFolder + File.separator + 
				this.location + File.separator +
				projectName + WUI_BLOCK_SUFFIX + ".jar" + File.separator  + 
				file);
	}
	
}
