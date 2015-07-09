package com.odcgroup.workbench.core.internal.properties;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;

import com.odcgroup.workbench.core.IOfsElement;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;

/**
 * This class checks if a folder is an OfsModelFolder and compares the model name of it with an expected value
 * It is used by the Eclipse extension org.eclipse.core.expressions.propertyTesters.
 * 
 * @author Kai Kreuzer
 *
 */
public class NaturePropertyTester extends PropertyTester {

	public NaturePropertyTester() {
	}

    public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {

    	if(receiver instanceof IAdaptable) {
    		IProject project = null;
    		Object adapter = ((IAdaptable) receiver).getAdapter(IOfsElement.class);
    		if(adapter!=null) {
    			if (adapter instanceof IOfsElement) {
        			IOfsElement element = (IOfsElement) adapter;
        			project = element.getOfsProject().getProject();    				
    			} else if (adapter instanceof IOfsProject) {
    				
    				project = ((IOfsProject)adapter).getProject();
    			}
    		}
    		adapter = ((IAdaptable) receiver).getAdapter(IOfsProject.class);
    		if(adapter!=null) {
    			IOfsProject ofsProject = (IOfsProject) adapter;
    			project = ofsProject.getProject();
    		}
    		if(project==null || !project.isOpen()) return false;
    	        
	        if ("nature".equals(property)) { 
	            try {
					return expectedValue == null
						? false
						: project.getNature((String)expectedValue)!=null;
				} catch (CoreException e) {
					OfsCore.getDefault().logError("Error in accessing project nature", e);
				}
	        }
    	}
    	
        return false;
    }
}
