package com.odcgroup.page.transformmodel.util;

import org.apache.commons.lang.StringUtils;

import com.odcgroup.page.model.Property;

public class PropertyUtils {
	/**
	 * @param property
	 * @param propertyName 
	 * @return
	 */
	public static  String getSiblingPropertyValue(Property property, String propertyName) {
		Property siblingProperty = property.getWidget().findProperty(propertyName);
		String value = siblingProperty.getValue();
		return value;
	}
	
	public static boolean isNumericButNotEmpty(String value) {
		return StringUtils.isNumeric(value) && StringUtils.isNotBlank(value);
	}

}
