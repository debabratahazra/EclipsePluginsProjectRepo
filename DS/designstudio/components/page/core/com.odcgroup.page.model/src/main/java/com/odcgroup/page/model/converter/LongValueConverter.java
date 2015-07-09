package com.odcgroup.page.model.converter;

import org.apache.commons.lang.StringUtils;

/**
 * @author atr
 */
public class LongValueConverter extends AbstractPropertyValueConverter {

	/**
	 * @param value
	 * @return Object
	 */
	public Object toObject(String value) {
		if (StringUtils.isEmpty(value)) {
			return new Long(0);
		}
		return Long.valueOf(value);
	}

	/**
	 * @param value
	 * @return String
	 */
	public String toString(Object value) {
		return ((Long)value).toString();
	}
	
	/**
	 * Constructor
	 */
	public LongValueConverter() {
		super();
	}

}
