package com.odcgroup.workbench.core;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.util.URI;

import com.odcgroup.workbench.core.repository.IModelVisitor;

/**
 * An <tt>IOfsElement</tt> can be regarded as the Odyssey equivalent of an <tt>IResource</tt>
 * 
 * Its implementors usually form the content of a 
 * {@link com.odcgroup.workbench.core.repository.IOfsProject}.
 * Each element has a uri and if it is local, it is associated to an <tt>IResource</tt> object.
 * 
 * @author Kai Kreuzer
 *
 */
public interface IOfsElement extends IAdaptable {

	/**
	 * If the element is associated to a resource in the workspace, this will be
	 * returned, otherwise null.
	 * 
	 * @return corresponding resource in the workspace or null if not existent
	 */
	public IResource getResource();
	
	/**
	 * The name of the element
	 * 
	 * @return the name of the element
	 */
	public String getName();
	
	/**
	 * The Odyssey project handle that this element is associated to.
	 * 
	 * @return the Odyssey project handle that this element is associated to
	 */
	public IOfsProject getOfsProject();
	
	/**
	 * Retrieves the uri for this element. This uri is of the Odyssey-specific
	 * "resource" scheme. 
	 * 
	 * @return the uri of this element
	 */
	public URI getURI();

	/**
	 * The scope that defines where this element exists. The result is a bitmask
	 * of constants defined in {@link com.odcgroup.workbench.core.repository.IOfsProject}
	 * 
	 * @return the scope where this element exists
	 */
	public int getScope();

	/**
	 * Sets the scope that defines where this element exists. A bitmask of constants defined in 
	 * {@link com.odcgroup.workbench.core.repository.IOfsProject} (or in subclasses of it) is expected
	 * 
	 * @param scope the scope where this element exists
	 */
	public void setScope(int scope);

	/**
	 * Determines whether this resource exists in the given scope
	 * 
	 * @param scope the scope to check for this element
	 * @return true, if the element exists in the given scope
	 */
	public boolean hasScope(int scope);

    /**
     * A lookup facility using the visitor pattern. The lookup traverses through
     * the model element and passes each child to the visitor.
     * 
     * @param visitor a visitor to the model repository
     */
    public void accept(IModelVisitor visitor);

}
