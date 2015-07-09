package com.odcgroup.service.gen.t24.internal.utils;

import org.apache.commons.lang.StringUtils;

/**
 * Helper class to get module name & component name
 * 
 * @author vramya
 * 
 */
public class ComponentNameHelper {
	public static String getModuleName(String name) {
		String moduleName = name;
		if (StringUtils.isNotBlank(name) && name.contains(".")) {
			int firstDotIndex = name.indexOf(".");
			moduleName = name.substring(0, firstDotIndex);
		}
		return moduleName;
	}

	public static String getComponentName(String name) {
		String componentName = name;
		if (StringUtils.isNotBlank(name) && name.contains(".")) {
			int firstDotIndex = name.indexOf(".");
			componentName = name.substring(firstDotIndex + 1);
		}
		return componentName;
	}
}
