package com.odcgroup.page.model.validator;

import org.eclipse.core.runtime.Assert;

/**
 * Validator for the natural integer, accept only well formed
 * number greater or equals to zero.
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class NaturalPropertyValidator extends AbstractPropertyValidator implements PropertyValidator {

	/** error message */
	private static String ERROR = "Number must be greater or equals to zero!";
	
	/**
	 * Returns a String if the value is not valid. Returns null if the value is
	 * valid.
	 * 
	 * @param value
	 *            the value to be validated
	 * @return the error message, or <code>null</code> indicating that the
	 *         value is valid
	 */
	public String isValid(Object value) {
		String result = null;
		Assert.isTrue(value instanceof String);
		try {
			int val = Integer.parseInt((String) value);
			if (val < 0) {
				result = ERROR;
			}
		} catch (NumberFormatException e) {
			result = ERROR;
		}
		return result;
	}
}
