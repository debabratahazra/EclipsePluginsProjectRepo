package com.odcgroup.workbench.core.repository;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

import com.odcgroup.workbench.core.IOfsElement;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;


/**
 * Filter based on the classifier of an object, it will collect all of the
 * object that belong to this classifier.
 */
public class ECoreModelLookup implements IModelVisitor, IResourceVisitor {

    private final String[] acceptExtensions;
    private final ModelMatcher matcher;
    private final List matchedObjects = new ArrayList();

    /**
     * Build a filter that will accept only model files with the given
     * extensions and objects with the given classifier.Once an object is
     * matched the visit in finished
     * 
     * @param acceptExtensions extensions of the model file to accept.
     * @param acceptClassifier classifier of the object to accept.
     */
    public ECoreModelLookup(String[] acceptExtensions, ModelMatcher matcher) {
        this.acceptExtensions = acceptExtensions.clone();
        this.matcher = matcher;
    }

    /**
     * @return the list of matched objects.
     */
    public List getMatchedObjects() {
        return matchedObjects;
    }

    public boolean enter(Object model) {
        if (model instanceof IOfsModelResource) {
        	IOfsModelResource modelResource = (IOfsModelResource) model;

            String ext = modelResource.getURI().fileExtension();
            return isAcceptedExtension(ext);
        } else if (model instanceof EObject) {
            if (matcher.match(model)) {
                matchedObjects.add(model);
                return false;
            }
            return true;
        }

        if(model instanceof IOfsElement) {
        	// continue traversing on model folders and packages
        	return true;
        } else {
        	// stop on anything else
        	return false;
        }        
    }

	private boolean isAcceptedExtension(String ext) {
		if (ext != null) {
		    for (int i = 0; i < acceptExtensions.length; i++) {
		        if (ext.equals(acceptExtensions[i])) {
		            return true;
		        }
		    }
		}
		return false;
	}

    public void leave(Object model) {
    }

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResourceVisitor#visit(org.eclipse.core.resources.IResource)
	 */
	public boolean visit(IResource resource) throws CoreException {
        if(resource instanceof IProject || resource instanceof IFolder) {
        	// continue traversing into projects and folders
        	return true;
        }
        
        if(resource instanceof IFile) {
        	IFile file = (IFile) resource;
        	if(isAcceptedExtension(file.getFileExtension())) {
	        	// load the model if possible and traverse into it
	        	IOfsProject ofsProject = OfsCore.getOfsProjectManager().getOfsProject(file.getProject());
	        	ResourceSet rs = ofsProject.getModelResourceSet();
	        	URI fileURI = URI.createFileURI(file.getLocation().toString());
	        	Resource res = rs.getResource(fileURI, true);
	        	if(res.getContents().size()>0) {
	        		return enter(res.getContents().get(0));
	        	}
        	}
        }
        
		return false;
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.core.repository.IModelVisitor#getScope()
	 */
	public int getScope() {
		return IOfsProject.SCOPE_ALL;
	}

}
