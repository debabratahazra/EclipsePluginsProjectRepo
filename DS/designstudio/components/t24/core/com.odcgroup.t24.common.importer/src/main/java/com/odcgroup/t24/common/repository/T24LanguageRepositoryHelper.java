package com.odcgroup.t24.common.repository;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.eclipse.xtext.resource.IEObjectDescription;

/**
 * TODO: Document me!
 *
 * @author atripod
 *
 */
public final class T24LanguageRepositoryHelper {

	private static final String T24_NAME = "T24Name";
	private static final String T24_COMPONENT_NAME = "T24Component";
	private static final String T24_MODULE_NAME = "T24Component";
	private static final String T24_PRODUCT_NAME= "T24Product";
	private static final String T24_APPLICATION_NAME= "T24Application";
	
	public static void setT24Name(Map<String, String> userData, String t24Name) {
		if (StringUtils.isNotBlank(t24Name)) {
			userData.put(T24_NAME, t24Name);
		}
	}
	
	public static void setT24ComponentName(Map<String, String> userData, String t24Component) {
		if (StringUtils.isNotBlank(t24Component)) {
			userData.put(T24_COMPONENT_NAME, t24Component);
		}
	}

	public static void setT24ModuleName(Map<String, String> userData, String t24Module) {
		if (StringUtils.isNotBlank(t24Module)) {
			userData.put(T24_MODULE_NAME, t24Module);
		}
	}

	public static void setT2ProductName(Map<String, String> userData, String t24module) {
		if (StringUtils.isNotBlank(t24module)) {
			userData.put(T24_PRODUCT_NAME, t24module);
		}
	}

	public static void setT24ApplicationName(Map<String, String> userData, String t24Application) {
		if (StringUtils.isNotBlank(t24Application)) {
			userData.put(T24_APPLICATION_NAME, t24Application);
		}
	}

	public static String getT24Name(IEObjectDescription eObjectDescription) {
		String value = eObjectDescription.getUserData(T24_NAME);
		return (value != null) ? value : ""; 
	}

	public static String getT24ComponentName(IEObjectDescription eObjectDescription) {
		String value = eObjectDescription.getUserData(T24_COMPONENT_NAME);
		return (value != null) ? value : ""; 
	}

	public static String getT24ModuleName(IEObjectDescription eObjectDescription) {
		String value = eObjectDescription.getUserData(T24_MODULE_NAME);
		return (value != null) ? value : ""; 
	}

	public static String getT24ProductName(IEObjectDescription eObjectDescription) {
		String value = eObjectDescription.getUserData(T24_PRODUCT_NAME);
		return (value != null) ? value : ""; 
	}

	public static String getT24ApplicationName(IEObjectDescription eObjectDescription) {
		String value = eObjectDescription.getUserData(T24_APPLICATION_NAME);
		return (value != null) ? value : ""; 
	}

}
