package com.odcgroup.workbench.core;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.transaction.util.Lock;
import org.osgi.framework.Version;

import com.odcgroup.workbench.core.repository.IModelVisitor;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.core.repository.ModelResourceSet;

/**
 * This is the general handle of an Odyssey project.
 * It provides information about the project and allows browsing its contents.
 * 
 * @author Kai Kreuzer
 *
 */
public interface IOfsProject extends IOfsModelContainer, IResourceChangeListener {

	/**
	 * Scope constant to declare that an {@link IOfsElement} is a member
	 * of all available scopes.
	 */
	final static public int SCOPE_ALL = 0xFFFF;

	/**
	 * Scope constant to declare that an {@link IOfsElement} is a member
	 * of a referenced project (declared in the project dependencies).
	 */
	final static public int SCOPE_DEPENDENCY = 0x02;

	/**
	 * Scope constant to declare that an {@link IOfsElement} is a member
	 * of the local project.
	 */
	final static public int SCOPE_PROJECT = 0x01; 

	/** 
	 * Retrieves the underlying resource.
	 * 
	 * @return the <tt>IProject</tt> resource
	 */
	public IProject getProject();	
	

	/** 
	 * Sets the underlying resource, what needs to be done if the instance is 
	 * created by the Eclipse extension mechanism
	 * 
	 * @param project the <tt>IProject</tt> resource
	 */
	public void setProject(IProject project);	

	/**
	 * Returns all model folders for this project
	 * 
	 * @return an array containing all model folders of this project
	 */
	public IOfsModelFolder[] getAllModelFolders();
	
	/**
	 * @return <tt>true</tt> if this project has at least one model folder, 
	 *         otherwise it return <tt>false</tt>
	 */
	public boolean hasModelFolders();
	
	/**
	 * Returns the model folder for a specified model type
	 * 
	 * @param modelType the model type that the model folder is requested for
	 * @return the model folder for the specified model type
	 */
	public IOfsModelFolder getModelFolder(String modelType);
	
	/**
	 * Returns the global resource set for this model project.
	 * 
	 * There is always exactly one resource set per project.
	 * 
	 * @return the resource set for this project
	 */
	public ModelResourceSet getModelResourceSet();
	
	/**
	 * returns the <tt>IOfsModelResource</tt>
	 * that corresponds to the specified uri
	 * 
	 * @param uri the uri of the model resource that should be returned
	 * 
	 * @return the model element that has this uri
	 * @throws ModelNotFoundException if the model could not be found
	 */
	public IOfsModelResource getOfsModelResource(URI uri) 
		throws ModelNotFoundException;

	/**
	 * returns <code>true</code> if a resource that corresponds to 
	 * the specified uri exists in the project, otherwise it returns
	 * <code>false</code>.
	 * 
	 * @param uri the uri of the model resource that should be verified
	 * 
	 * @return <code>true</true> if a model exists with the given uri.
	 */
	public boolean ofsModelResourceExists(URI uri); 

	/**
     * A lookup facility using the visitor pattern. The lookup traverses through
     * the complete repository and passes each model resource to the visitor.
     * 
     * @param visitor a visitor to the model repository
     */
    public void lookup(IModelVisitor visitor);


	/**
	 * This makes the project rescan all contained models including its dependencies.
	 * Especially useful if dependencies have changed and we need to update our caches.
	 */
	public void refresh();
	
	/**
	 * Releases all memory that might be allocated (especially for
	 * the loaded model resources).
	 */
	public void clearCache();

}
