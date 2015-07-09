package com.odcgroup.ds.t24.packager.helper;

/**
 * T24EnvironmentConfiguration is used by Packager to obtain the 
 * environment variables set whereever it is needed. Any Environment
 * added in future must be accessed using this class only.
 *
 * @author mumesh
 *
 */
public class T24EnvironmentConfiguration {

	public static String getJbasePath(){
		return System.getenv("JBASE_PATH");
	}
	
	public static String getT24Home(){
		return System.getenv("HOME");
	}
	
	public static String getTafcHome(){
		return System.getenv("TAFC_HOME");
	}
}
