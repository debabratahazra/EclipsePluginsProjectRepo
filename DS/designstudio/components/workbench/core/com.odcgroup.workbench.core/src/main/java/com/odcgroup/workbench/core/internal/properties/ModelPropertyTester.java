package com.odcgroup.workbench.core.internal.properties;

import org.eclipse.core.expressions.PropertyTester;

import com.odcgroup.workbench.core.IOfsModelFolder;
import com.odcgroup.workbench.core.IOfsModelPackage;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;

/**
 * This class checks if a folder is an OfsModelFolder and compares the model name of it with an expected value
 * It is used by the Eclipse extension org.eclipse.core.expressions.propertyTesters.
 * 
 * @author Kai Kreuzer
 *
 */
public class ModelPropertyTester extends PropertyTester {
    public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
    	if(receiver instanceof IOfsModelFolder) {
    		IOfsModelFolder modelFolder = (IOfsModelFolder) receiver;
	        if ("modelType".equals(property)) { 
	            return expectedValue == null
	            	? true
	            	: modelFolder.acceptFileExtension((String)expectedValue);
	        }
    	}

    	if(receiver instanceof IOfsModelPackage) {
    		if ("modelType".equals(property)) { 
	    		IOfsModelPackage modelPackage = (IOfsModelPackage) receiver;
	    		Object parent = modelPackage.getParent();
	    		while(!(parent instanceof IOfsModelFolder)) {
	    			parent = ((IOfsModelPackage) parent).getParent();
	    		}
        		IOfsModelFolder modelFolder = (IOfsModelFolder) parent;
	            return expectedValue == null
	            	? true
	            	: modelFolder.acceptFileExtension((String)expectedValue);
    		}
    	}

    	if(receiver instanceof IOfsModelResource) {
    		if ("modelType".equals(property)) { 
    			IOfsModelResource modelResource = (IOfsModelResource) receiver;
	    		
    			if (IOfsProject.SCOPE_DEPENDENCY == modelResource.getScope())
    				return true;
    			
	            return expectedValue == null
	            	? false
	            	: ((String)expectedValue).endsWith(modelResource.getURI().fileExtension());
    		}
    	}
    	return false;
    }
}
