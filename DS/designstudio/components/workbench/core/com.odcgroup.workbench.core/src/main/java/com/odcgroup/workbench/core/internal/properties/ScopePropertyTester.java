package com.odcgroup.workbench.core.internal.properties;

import org.eclipse.core.expressions.PropertyTester;

import com.odcgroup.workbench.core.IOfsModelPackage;
import com.odcgroup.workbench.core.IOfsModelResource;

/**
 * This class determines the scope of a model package or resource
 * It is used by the Eclipse extension org.eclipse.core.expressions.propertyTesters.
 * 
 * @author Kai Kreuzer
 *
 */
public class ScopePropertyTester extends PropertyTester {
    public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
    	if(receiver instanceof IOfsModelPackage) {
    		IOfsModelPackage modelPackage = (IOfsModelPackage) receiver;
	        if ("scope".equals(property)) { 
	            return expectedValue == null
	            	? false
	            	: (((Integer)expectedValue).intValue() & modelPackage.getScope()) > 0;
	        }
    	}
    	if(receiver instanceof IOfsModelResource) {
    		IOfsModelResource modelResource = (IOfsModelResource) receiver;
	        if ("scope".equals(property)) { 
	            return expectedValue == null
	            	? false
	    	       	: (((Integer)expectedValue).intValue() & modelResource.getScope()) > 0;
	        }
    	}
    	return false;
    }
}
