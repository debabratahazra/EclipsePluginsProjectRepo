package com.odcgroup.page.model.validator;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.Assert;

/**
 * Validator for the positive integer, accept only well formed
 * number greater than zero.
 * 
 * @author can
 * 
 */
public class PositivePropertyBlankValidator extends AbstractPropertyValidator implements PropertyValidator {

	private static String ERROR = "Number must be greater than zero!";
	
	/**
	 * Returns a String if the value is not valid. Returns null if the value is
	 * valid or no value has been entered.
	 * 
	 * @param value
	 *            the value to be validated
	 * @param acceptBlank
	 * 			  determines if should check value if it contains nothing
	 * @return the error message, or <code>null</code> indicating that the
	 *         value is valid
	 */
	public String isValid(Object value) {
		String result = null;
		Assert.isTrue(value instanceof String);
		
		if (StringUtils.isBlank((String) value)) {
			return result;
		} else {
			try {
				int val = Integer.parseInt((String) value);
				if (val <= 0) {
					result = ERROR;
				}
			} catch (NumberFormatException e) {
				result = ERROR;
			}
		}
		return result;
		
	}
}
