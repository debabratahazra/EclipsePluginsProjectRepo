package com.odcgroup.page.model.validator;

import org.eclipse.core.runtime.Assert;

import com.odcgroup.page.model.Property;

/**
 * This checks the value of a property of type ExpandLevelType (see data-type metamodel).
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class ExpandLevelPropertyValidator extends AbstractPropertyValidator implements PropertyValidator {

	/** Error message */
	private static String ERROR = "The expand level must be either all or a natural number";

	/**
	 * @see com.odcgroup.page.model.validator.PropertyValidator#isValid(java.lang.Object)
	 */
	public String isValid(Object value) {
		String result = null;
		Assert.isTrue(value instanceof String);
		Property p = getProperty();
		if (p != null) {
			String image = (String)value;
			if (! "all".equalsIgnoreCase(image)) {
				// the value is not 'all', check if it is a natural number
				try {
					int val = Integer.parseInt((String) value);
					if (val < 0) {
						result = ERROR;
					}
				} catch (NumberFormatException e) {
					result = ERROR;
				}
			}
		}
		return result;
	}

}
