package com.odcgroup.workbench.core;

import java.util.Collection;

import org.eclipse.core.resources.IFolder;
import org.eclipse.emf.common.util.URI;

/**
 * An <tt>IOfsModelFolder</tt> represents the parent folder in the workspace
 * for models of a specific type (i.e. "pageflow"). These model folders only have
 * a file system equivalent for projects in the workspace. The bundle
 * version of a model project has all model packages directly in its root.
 * 
 * @author Kai Kreuzer
 *
 */
public interface IOfsModelFolder extends IOfsElement {

	/**
	 * Gets all model packages that are contained in this model folder
	 * (regardless of their scope)
	 * 
	 * @return a collection of model packages
	 */
	Collection<IOfsModelPackage> getPackages();

	/**
	 * Gets all model packages of a certain scopy that are
	 * contained in this model folder. 
	 * 
	 * The scope is a bit mask consisting out of constants defined on
	 * {@link com.odcgroup.workbench.core.repository.IOfsProject} (like
	 * {@link IOfsProject.SCOPE_PROJECT}).
	 * 
	 * @return a collection of model packages of the specified scope
	 */
	Collection<IOfsModelPackage> getPackages(int scope);

	/**
	 * returns the <tt>IOfsElement</tt> (either a model package or a model resource)
	 * that corresponds to the specified uri
	 * 
	 * @param uri the uri of the model element that should be returned
	 * 
	 * @return the model element that has this uri
	 */
	IOfsElement getOfsElement(URI uri);
	
	/**
	 * Determines whether a given file extension is valid for models inside
	 * this folder.
	 * @param ext the file extension to check
	 * @return true, if it's an accepted extension for models
	 */
	boolean acceptFileExtension(String ext);
	
	IOfsModelPackage getOfsPackage(String name);
	void addPackage(IFolder folder);
	void removePackage(String name);
	void removeAll();
	
}
