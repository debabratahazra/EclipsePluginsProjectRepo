package com.odcgroup.workbench.editors.ui;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;

/**
 * @author pkk
 * 
 */
public class ResourceUtil {

	/**
	 * @param eObject
	 * @return
	 */
	public static final IFile getFile(EObject eObject) {
		Resource resource = eObject.eResource();
		if (resource != null)
			try {
				return getFile(resource);
			} catch (IllegalAccessException e) {
				return null;
			}
		else
			return null;
	}
	
	/**
	 * @param resource
	 * @return
	 */
	public static URIConverter getURIConverter(Resource resource) {
		return resource.getResourceSet().getURIConverter();
	}

	/**
	 * @param eResource
	 * @return
	 */
	public static final IFile getFile(Resource eResource) throws IllegalAccessException{
		if (eResource == null || eResource.getURI() == null)
			return null;
		String uri;
		// normalize the URI before parsing the URI string
		URI resUri = getURIConverter(eResource).normalize(eResource.getURI());
		IFile files[];
		uri = resUri.toString();
		if (uri.indexOf("/resource/") != -1) {
			uri = uri.substring(uri.indexOf("/resource/")
					+ "/resource/".length());
		}
		if (uri.startsWith("file:///")) {
			String cutString = uri.substring("file:///".length());
			files = ResourcesPlugin.getWorkspace().getRoot()
					.findFilesForLocation(new Path(cutString));
			if (files.length > 0)
				return files[0];
		}
		if (uri.indexOf("/") == -1){
			throw new IllegalAccessException("File does not exists!!!");
		}
		return ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(uri));

	}

	/**
	 * @param file
	 * @param resourceSet
	 * @return
	 */
	static public Resource openFile(final IFile file, final ResourceSet resourceSet)
    {
        if(file == null)
            throw new NullPointerException("Resource is null.");
        if(resourceSet == null)
            throw new NullPointerException("ResourceSet is null.");
        if(!file.exists())
            throw new IllegalArgumentException((new StringBuilder("The file ")).append(file.getFullPath().toString()).append(" does not exists.").toString());
        
        final URI fileURI = URI.createFileURI(file.getLocation().toFile().getAbsolutePath());
    	
        final Resource r = resourceSet.getResource(fileURI, true);	       
        try {
			r.load(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return r;
    }
}
