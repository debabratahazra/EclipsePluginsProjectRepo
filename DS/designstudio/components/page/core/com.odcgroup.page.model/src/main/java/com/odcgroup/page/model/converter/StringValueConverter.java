package com.odcgroup.page.model.converter;


/**
 * @author atr
 */
public class StringValueConverter extends AbstractPropertyValueConverter {

	/**
	 * @param value
	 * @return Object
	 */
	public Object toObject(String value) {
		return (value != null) ? (String)value : "";
	}

	/**
	 * @param value
	 * @return String
	 */
	public String toString(Object value) {
		return (value != null) ? value.toString() : null; 
		
	}
	
	/**
	 * Constructor
	 */
	public StringValueConverter() {
		super();
	}

}
