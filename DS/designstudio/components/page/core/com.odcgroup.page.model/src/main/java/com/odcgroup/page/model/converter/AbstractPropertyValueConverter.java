package com.odcgroup.page.model.converter;

import com.odcgroup.page.metamodel.PropertyValueConverter;

/**
 * This is the base class for all property value converter
 * A Property value converter is mainly used in by a property source
 * (GEF Context).
 * @author atr
 */
public abstract class AbstractPropertyValueConverter implements PropertyValueConverter {

	/**
	 * @return the name of this converter.
	 */
	public final String getName() {
		return getClass().getName();
	}

	/**
	 * @param value
	 * @return Object
	 */
	public abstract Object toObject(String value);

	/**
	 * @param value
	 * @return String
	 */
	public abstract String toString(Object value);
	
	/**
	 * @return String
	 */
	public String toString() {
		return "PropertyValueConverter: " + getName();
	}

	/**
	 * Constructor.
	 */
	public AbstractPropertyValueConverter() {
		super();
	}
}
