package com.odcgroup.page.model.converter;

/**
 * @author atr
 */
public class BooleanValueConverter extends AbstractPropertyValueConverter {

	/**
	 * @param value
	 * @return Object
	 */
	public Object toObject(String value) {
		return Boolean.valueOf(value);
	}

	/**
	 * @param value
	 * @return String
	 */
	public String toString(Object value) {
		return String.valueOf(((Boolean)value).booleanValue());
	}
	
	/**
	 * Constructor
	 */
	public BooleanValueConverter() {
		super();
	}

}
