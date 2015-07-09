package com.odcgroup.workbench.core;

import java.io.IOException;
import java.io.OutputStream;

import org.eclipse.core.resources.IStorage;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.util.Lock;

/**
 * This represents a model resource
 * 
 * @author Kai Kreuzer
 *
 */
public interface IOfsModelResource extends IOfsElement, IStorage {
	
	public IOfsElement getParent();

	public OutputStream getOutputStream();
	
	/**
	 * @return the short name of the model (without the extension)
	 */
	public String getShortName();

    /**
     * Loads the model object with the given name.
     * 
     * The uri of a model object is a uri using the Odyssey-specific 
     * '<tt>resource</tt>'-scheme that identifies the model file plus
     * a '<tt>#</tt>'-separated fragment that uniquely identifies 
     * the model object in the model file.
     * 
     * @param uri The model uri. If no fragment is specified the first
     *            model object in the model will be returned.
     * @return The model object(s) with the given name.
     * @throws IOException if the model file cannot be found or it
     *             does not contain the model object identified by the fragment.
     * @throws InvalidMetamodelVersionException if the model file is based on an
     * 			outdated metamodel version and needs to be migrated.
     */
    public EList<EObject> getEMFModel() throws IOException, InvalidMetamodelVersionException;

    /** 
     * Checks whether this model has already been loaded into the resource set
     * 
     * @return true, if model has already been loaded into memory
     */
    public boolean isLoaded();
    
    /**
     * Unloads the model from memory to free up space. On the next request of getEMFModel(), the
     * model will automatically be loaded again.
     */
    public void unload();
    
    /**
     * Returns an EMF lock for this model resource
     * 
     * @return an EMF lock instance
     */
    public Lock getLock();
}
