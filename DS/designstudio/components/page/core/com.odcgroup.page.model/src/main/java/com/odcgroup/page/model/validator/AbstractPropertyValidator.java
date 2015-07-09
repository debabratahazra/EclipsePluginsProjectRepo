package com.odcgroup.page.model.validator;

import com.odcgroup.page.model.Property;

/**
 * @author atr
 */
public class AbstractPropertyValidator {
	
	/**
	 * 
	 */
	private Property property;
	
	/**
	 * @return Property
	 */
	protected final Property getProperty() {
		return property;
	}
	
	/**
	 * @param property
	 */
	public final void setProperty(Property property) {
		this.property = property;
	}

	/**
	 * 
	 */
	protected AbstractPropertyValidator() {
		
	}

}
