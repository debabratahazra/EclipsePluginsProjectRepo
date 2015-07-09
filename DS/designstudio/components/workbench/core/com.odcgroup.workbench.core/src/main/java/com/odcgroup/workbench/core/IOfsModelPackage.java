package com.odcgroup.workbench.core;

import java.util.Collection;

import org.eclipse.core.resources.IFolder;
import org.eclipse.emf.common.util.URI;

/**
 * An <tt>IOfsModelPackage</tt> represents a folder (resp. java package)
 * that might contain sub packages or model resources. 
 * 
 * A package can have a local representation, but it might not have one.
 * As it can exist in different scopes at the same time (in the workspace
 * as well as in some referenced bundles), its scope property defines
 * where it exists by a bitmask.<p>
 * 
 * @author Kai Kreuzer
 *
 */
public interface IOfsModelPackage extends IOfsElement {
	
	/**
	 * Returns the parent which is either a model folder or a model package
	 * 
	 * @return the parent element
	 */
	IOfsElement getParent();

	/**
	 * Returns the model folder in which this package lives. This might be
	 * the parent element or it can be higher up in the hierarchy.
	 * 
	 * @return The IOfsModelFolder to which this package belongs to
	 */
	IOfsModelFolder getModelFolder();
	
	/**
	 * Gets all Models that are contained in this package (not in any sub-package)
	 * 
	 * @return all model resources inside this package
	 */
	Collection<IOfsModelResource> getModels();

	/**
	 * Gets all Models that are contained in this package (not in a sub-package)
	 * and restrict the results to a specified scope
	 *
	 * @param scope the scope to restrict the results to
	 * @return all model resources inside this package and the specified scope
	 */
	Collection<IOfsModelResource> getModels(int scope);

	/**
	 * Gets all direct sub packages of this package
	 * 
	 * @return an array of direct sub packages
	 */
	Collection<IOfsModelPackage> getSubPackages();
	
	/**
	 * Gets all direct sub packages of this package
	 * and restrict the results to a specified scope
	 * 
	 * @param scope the scope to restrict the results to
	 * @return an array of direct sub packages
	 */
	Collection<IOfsModelPackage> getSubPackages(int scope);

	/** 
	 * Checks whether this package is empty (i.e. it has no model resources
	 * or sub packages)
	 * 
	 * @return true, if there are no model resources or sub packages
	 */
	boolean isEmpty();
	
	/** 
	 * Checks whether this package is empty (i.e. it has no model resources
	 * or sub packages) with respect to a certain scope
	 * 
	 * @param scope the scope to use for the check
	 * @return true, if there are no model resources or sub packages
	 */
	boolean isEmpty(int scope);
	
	/**
	 * returns the <tt>IOfsModelPackage</tt> 
	 * that corresponds to the specified uri
	 * 
	 * @param uri the uri of the model package that should be returned
	 * 
	 * @return the model package that has this uri
	 */
	IOfsModelPackage getOfsPackage(URI uri);
	
	IOfsModelPackage getOfsPackage(String name);
	void addPackage(IFolder folder);
	void add(IOfsModelResource value);
	void removeModel(URI uri);
	void removePackage(String name);
	void delete();

}
