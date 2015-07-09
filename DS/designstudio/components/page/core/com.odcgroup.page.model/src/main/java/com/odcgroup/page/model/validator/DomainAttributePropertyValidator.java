package com.odcgroup.page.model.validator;

import static com.odcgroup.page.metamodel.PropertyTypeConstants.BEAN_PROPERTY;
import static com.odcgroup.page.metamodel.PropertyTypeConstants.FOR;

import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;

/**
 * @author atr
 */
public class DomainAttributePropertyValidator extends AbstractPropertyValidator implements PropertyValidator {

	/*
	 * @see com.odcgroup.page.model.validator.PropertyValidator#isValid(java.lang.Object)
	 */
	public String isValid(Object value) {
		Property p = getProperty();
		if (p != null) {
			Widget aw = p.getWidget();
			aw.setID((String)value);
			aw.setPropertyValue(BEAN_PROPERTY, (String)value);
			aw.setPropertyValue(FOR, (String)value);
		}
		return null;
	}

}
