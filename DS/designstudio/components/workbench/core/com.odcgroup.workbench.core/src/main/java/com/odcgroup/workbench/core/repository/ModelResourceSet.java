package com.odcgroup.workbench.core.repository;

import java.util.Set;

import org.eclipse.core.resources.IStorage;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

import com.odcgroup.workbench.core.IOfsModelResource;

/**
 * This interface is an extension to the standard EMF ResourceSet interface and
 * brings new methods that allow dealing with IOfsModelResources.
 * 
 * @author Kai Kreuzer
 * 
 */
public interface ModelResourceSet extends ResourceSet {

	/**
	 * This is the standard createResource method with a new "scope" parameter
	 * added.
	 * 
	 * @param uri
	 *            the URI of the resource to create.
	 * @param scope
	 *            a scope where this resource belongs to (only one bit of the
	 *            bitmask should be set here)
	 * 
	 * @return a new resource, or <code>null</code> if no factory is registered.
	 */
	public Resource createResource(URI uri, int scope);

	/**
	 * This is the standard createResource method with a new "scope" parameter
	 * added.
	 * 
	 * @param uri
	 *            the URI of the resource to create.
	 * @param contentType
	 *            the {@link ContentHandler#CONTENT_TYPE_PROPERTY content type
	 *            identifier} of the URI, or <code>null</code> if no content
	 *            type should be used during lookup.
	 * @param scope
	 *            a scope where this resource belongs to (only one bit of the
	 *            bitmask should be set here)
	 * 
	 * @return a new resource, or <code>null</code> if no factory is registered.
	 */
	public Resource createResource(URI uri, String contentType, int scope);

	/**
	 * This is the standard getResource method with a new "scope" parameter
	 * added.
	 * 
	 * @param uri
	 *            the URI to resolve.
	 * @param scope
	 *            a scope where this resource belongs to (only one bit of the
	 *            bitmask should be set here)
	 * @return the resource resolved by the URI, or <code>null</code> if there
	 *         isn't one and it's not being demand loaded.
	 * @throws RuntimeException
	 *             if a resource can't be demand created.
	 * @throws org.eclipse.emf.common.util.WrappedException
	 *             if a problem occurs during demand load.
	 */
	public Resource getResource(URI uri, int scope);

	/**
	 * This method creates a new IOfsModelResource instance for a given storage in
	 * a given scope.
	 * 
	 * 
	 * 
	 * @param storage an IStorage instance from where the model data can be loaded
	 * @param scope
	 *            a scope where this resource belongs to (only one bit of the
	 *            bitmask should be set here)
	 * @return a new IOfsModelResource instance for the storage
	 */
	public IOfsModelResource createOfsModelResource(IStorage storage, int scope);

	/**
	 * This method creates a new IOfsModelResource instance for a given storage in
	 * a given scope.
	 * 
	 * @param uri the URI to use for this model resource
	 * @param storage an IStorage instance from where the model data can be loaded
	 * @param scope
	 *            a scope where this resource belongs to (only one bit of the
	 *            bitmask should be set here)
	 * @return a new IOfsModelResource instance for the storage
	 */
	public IOfsModelResource createOfsModelResource(URI uri, IStorage storage, int scope);

	/**
	 * This method returns all model resources of given types for a given scope.
	 * If resources exist in different scopes,
	 * the resource of the scope of the highest priority will be chosen and its scope bitmask will
	 * encode in which scopes this URI actually exist.
	 * 
	 * @param modelTypes a String array of model types that should be included in the result
	 * @param scope the scope where resources should be searched in.
	 * @return a set of all model resources that were found in the resource set for the given constraints.
	 */
	public Set<IOfsModelResource> getAllOfsModelResources(String[] modelTypes, int scope);

	/**
	 * This method returns all model resources contained in the resource set
	 * 
	 * @return a set of all model resources that were found in the resource set
	 */
	public Set<IOfsModelResource> getAllOfsModelResources();

	/**
	 * This method returns the model resource for a given URI. If resources exist in different scopes,
	 * the resource of the scope of the highest priority will be chosen and its scope bitmask will
	 * encode in which scopes this URI actually exist.
	 *  
	 * @param uri the URI of the model resource to get
	 * @return the model resource for the given URI
	 */
	public IOfsModelResource getOfsModelResource(URI uri);

	/**
	 * This method returns the model resource for a given URI and scope.
	 *  
	 * @param uri the URI of the model resource to get
	 * @param scope the scope to search the model resource in (a single bit should be set in the bitmask)
	 * @return the model resource for the given URI
	 */
	public IOfsModelResource getOfsModelResource(URI uri, int scope);
	
	/**
	 * This method removes a model resource with a given URI and scope from the resource set.
	 *  
	 * @param uri the URI of the model resource to remove
	 * @param scope the scope of the model resource to remove (a single bit should be set in the bitmask)
	 */
	public void removeResource(URI uri, int scope);
	
	/**
	 * This method returns the modelUriConverter
	 * 
	 * @return
	 */
	public ModelURIConverter getModelUriConverter();

}