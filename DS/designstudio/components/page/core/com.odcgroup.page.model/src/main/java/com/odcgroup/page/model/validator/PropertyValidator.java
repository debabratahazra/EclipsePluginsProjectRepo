package com.odcgroup.page.model.validator;

/**
 * Validates that the Property is correct.
 * 
 * @author Gary Hayes
 */
public interface PropertyValidator {

    /**
     * Returns a String if the value is not valid. Returns null if
     * the value is valid.
     * 
     * @param value the value to be validated
     * @return the error message, or <code>null</code> indicating
     *	that the value is valid
     */
    public String isValid(Object value);
}
