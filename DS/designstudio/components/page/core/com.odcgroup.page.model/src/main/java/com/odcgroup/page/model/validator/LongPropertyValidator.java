package com.odcgroup.page.model.validator;

import org.eclipse.core.runtime.Assert;

/**
 * Validator for the long, accept only digit
 * 
 * @author atr
 */
public class LongPropertyValidator extends AbstractPropertyValidator implements PropertyValidator {

	/**
	 * Returns a String if the value is not valid. Returns null if the value is
	 * valid.
	 * 
	 * @param value
	 *            the value to be validated
	 * @return the error message, or <code>null</code> indicating that the value
	 *         is valid
	 */
	public String isValid(Object value) {
		Assert.isTrue(value instanceof String);
		try {
			Long.parseLong((String) value);
		} catch (NumberFormatException e) {
			return "Not a long integer!";
		}
		return null;
	}
}
