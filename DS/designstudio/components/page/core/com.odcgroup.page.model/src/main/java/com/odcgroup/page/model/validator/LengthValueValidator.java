package com.odcgroup.page.model.validator;

import com.odcgroup.page.model.Property;

/**
 * Validator for the DataType Length.
 * @author atr
 */
public class LengthValueValidator extends AbstractPropertyValidator implements PropertyValidator {

	/** valid column name */
	private static final String LENGTH_PATTERN = "[0-9]*[%]?";
	
	/*
	 * @see com.odcgroup.page.model.validator.PropertyValidator#isValid(java.lang.Object)
	 */
	public String isValid(Object value) {
		Property p = getProperty();
		if (p != null) {
			String text = (String) value;
			if (!text.matches(LENGTH_PATTERN)) {
				return "The value [{0}] must contains only digits and optionally followed by a '%' character";
			}
		}
		return null;
	}

}
