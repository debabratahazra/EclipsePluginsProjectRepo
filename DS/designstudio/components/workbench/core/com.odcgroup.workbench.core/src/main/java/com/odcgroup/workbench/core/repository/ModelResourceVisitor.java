package com.odcgroup.workbench.core.repository;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;

import com.odcgroup.workbench.core.IOfsElement;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;


/**
 * Filter based on the classifier of an object, it will collect all of the
 * object that belong to this classifier.
 */
public class ModelResourceVisitor implements IModelVisitor {

	private int scope = IOfsProject.SCOPE_ALL;
	
    private final String[] acceptExtensions;
    private final Set<IOfsModelResource> matchedResources = new HashSet<IOfsModelResource>();

    /**
     * Build a filter that will accept only model files with the given
     * extensions and objects with the given classifier.Once an object is
     * matched the visit in finished
     * 
     * @param acceptExtensions extensions of the model file to accept.
     * @param acceptClassifier classifier of the object to accept.
     */
    public ModelResourceVisitor(String[] acceptExtensions) {
        // TODO: Workaround as long as the domain models still have mml extension
        if(ArrayUtils.contains(acceptExtensions, "domain")) acceptExtensions = (String[]) ArrayUtils.add(acceptExtensions, "mml");
        this.acceptExtensions = acceptExtensions==null? null : acceptExtensions.clone();
    }

    /**
     * Build a filter that will accept only model files with the given
     * extensions and objects with the given classifier.Once an object is
     * matched the visit in finished
     * 
     * @param acceptExtensions extensions of the model file to accept.
     * @param acceptClassifier classifier of the object to accept.
     */
    public ModelResourceVisitor(String[] acceptExtensions, int scope) {
    	this(acceptExtensions);
    	this.scope = scope;
    }

    /**
     * @return the list of matched resources.
     */
    public Set<IOfsModelResource> getMatchedResources() {
        return matchedResources;
    }
    
    /**
     * @param scope the scope to filter the resources for
     * @return the list of matched resources of a given scope.     * 
     */
    public Set<IOfsModelResource> getMatchedResources(int scope) {
        Set<IOfsModelResource> filteredResources = new HashSet<IOfsModelResource>();
        for(IOfsModelResource res : getMatchedResources()) {
        	if(res.hasScope(scope)) filteredResources.add(res);
        }
        return filteredResources;
    }
    
    public boolean enter(Object model) {
        if (model instanceof IOfsModelResource) {
        	IOfsModelResource modelResource = (IOfsModelResource) model;

            String ext = modelResource.getURI().fileExtension();
            if (ext != null) {
            	if(acceptExtensions==null) {
            		matchedResources.add(modelResource);
            	} else {
	                for (int i = 0; i < acceptExtensions.length; i++) {
	                    if (ext.equals(acceptExtensions[i])) {
	                    	matchedResources.add(modelResource);
	                    	return false;
	                    }
	                }
            	}
            }
        	// do not traverse into the file
        	return false;
        }

        if(model instanceof IOfsElement) {
        	// continue traversing on model folders and packages
        	return true;
        } else {
        	// stop on anything else
        	return false;
        }
    }

    public void leave(Object model) {
    }

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.core.repository.IModelVisitor#getScope()
	 */
	public int getScope() {
		return scope;
	}

}
