package com.odcgroup.page.model.validator;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.eclipse.core.runtime.Assert;

public class UrlPropertyValidator extends AbstractPropertyValidator implements PropertyValidator {

	/**
     * Returns a String if the value is not valid. Returns null if
     * the value is valid.
     * 
     * Validates that the url is a correctly formed url
     * 
     * @param value the value to be validated
     * @return the error message, or <code>null</code> indicating
     *	that the value is valid
     */
    public String isValid(Object value) {
    	
    	Assert.isTrue(value instanceof String);
    	
    	try {
    		/** TODO handle the Dynamic url */
    		String urlString = (String) value;
    		
    		if(urlString.length() > 0) {
    			URL url = new URL(urlString);
    			URLConnection urlConnection = url.openConnection();
    			urlConnection.connect();
    		}
    	} catch (MalformedURLException e) {
    	    return "Url is format is not correct";
    	} catch (IOException e) {
    		//Do not check if resource exists
    	    return null;
    	}
    	
    	return null;
    }
}
