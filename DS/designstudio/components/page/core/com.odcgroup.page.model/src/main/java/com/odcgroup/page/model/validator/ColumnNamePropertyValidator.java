package com.odcgroup.page.model.validator;

import com.odcgroup.page.model.Property;

/**
 * @author atr
 * @since DS 1.40.0
 */
public class ColumnNamePropertyValidator extends AbstractPropertyValidator implements PropertyValidator {

	/** valid column name */
	private static final String COLUMN_NAME_PATTERN = "[A-Za-z][A-Za-z0-9_]*";
		
	/*
	 * @see com.odcgroup.page.model.validator.PropertyValidator#isValid(java.lang.Object)
	 */
	public String isValid(Object value) {
		Property p = getProperty();
		if (p != null) {
			String name = (String)value;
			if (!name.matches(COLUMN_NAME_PATTERN)) {
				return "The name of the column is invalid.";
			}
		}
		return null;
	}

}
