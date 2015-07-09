package com.odcgroup.page.model.converter;

import org.apache.commons.lang.StringUtils;

/**
 * @author atr
 */
public class IntegerValueConverter extends AbstractPropertyValueConverter {

	/**
	 * @param value
	 * @return Object
	 */
	public Object toObject(String value) {
		if (StringUtils.isEmpty(value)) {
			return new Integer(0);
		}
		return Integer.valueOf(value);
	}

	/**
	 * @param value
	 * @return String
	 */
	public String toString(Object value) {
		return ((Integer)value).toString();
	}
	
	/**
	 * Constructor
	 */
	public IntegerValueConverter() {
		super();
	}

}
