package com.odcgroup.ocs.support.preferences;

public interface OCSRuntimePreference {

    static final String PREFIX = "com.odcgroup.ocs.runtime";
    
    static final String INSTALL_DIR = PREFIX + ".installDir";

    static final String EAR_DIR = PREFIX + ".earDir";

    static final String OCS_SOURCES_JAR = PREFIX + ".sources";

    static final String DISTRIBUTION_PACKAGE = PREFIX + ".distribution";

	static final String HOTFIXES_DIR = PREFIX + ".hotfixes";

	static final String CUSTO_DIR = PREFIX + ".custo";
	
	static final String AUTO_UPDATE_MAVEN = PREFIX + ".auto.update.m2eclipse";

}
