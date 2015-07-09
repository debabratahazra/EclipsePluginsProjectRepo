package com.odcgroup.page.ui.properties;

import org.eclipse.jface.viewers.ICellEditorValidator;

import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.validator.AbstractPropertyValidator;
import com.odcgroup.page.model.validator.PropertyValidator;
import com.odcgroup.page.util.ClassUtils;

/**
 * Adapter for the Eclipse ICellEditorValidator.
 * 
 * @author Gary Hayes
 */
public class CellEditorValidator implements ICellEditorValidator { 
	
	/** The PropertyValidator. */
	private PropertyValidator validator;
	
	/**
	 * Creates a new CellEditorValidator.
	 * 
	 * @param validatorName The name of the validator
	 */
	public CellEditorValidator(String validatorName, Property property) {		
		validator = (PropertyValidator) ClassUtils.newInstance(getClass().getClassLoader(), validatorName);
		((AbstractPropertyValidator)validator).setProperty(property);
	}

    /**
     * Returns a string indicating whether the given value is valid;
     * <code>null</code> means valid, and non-<code>null</code> means
     * invalid, with the result being the error message to display
     * to the end user.
     * <p>
     * It is the responsibility of the implementor to fully format the
     * message before returning it.
     * </p>
     * 
     * @param value the value to be validated
     * @return the error message, or <code>null</code> indicating
     *	that the value is valid
     */
    public String isValid(Object value) {
    	return validator.isValid(value);
    }
}
