package com.odcgroup.ocs.server.external.builder.internal.mapping;

import java.io.File;

/**
 * Simple mapping class for mapping in jar files
 * @author yan
 */
public class SimpleMappingInfo implements MappingInfo {
	
	private static final String PROJECT_EXTENSION = ".jar";   //$NON-NLS-1$
	
	private String postfix;
	private String location;
	
	/**
	 * Use this constructor if the gen project should be split when
	 * deployed on the server per source folder (-api-domain, 
	 * -impl-domain, ...)   
	 */
	public SimpleMappingInfo(String location, String postfix) {
		this.location = location;
		this.postfix = postfix;
	}
	
	/**
	 * Define the target according the information provided in the
	 * constructor and the current context.
	 */
	public File getTarget(String serverFolder, String projectName, String file) {
		return new File(serverFolder + File.separator + 
				location +
				projectName + postfix + PROJECT_EXTENSION + 
				file);
	}

}
