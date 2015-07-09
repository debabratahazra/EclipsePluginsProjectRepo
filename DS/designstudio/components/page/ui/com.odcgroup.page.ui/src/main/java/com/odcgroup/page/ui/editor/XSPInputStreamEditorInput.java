package com.odcgroup.page.ui.editor;

import java.io.InputStream;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.IStorageEditorInput;

import com.odcgroup.workbench.core.IOfsModelResource;

/**
 * An InputStreamEditorInput wraps an input stream. It is used for memory-based Editor Inputs.
 * 
 * @author Gary Hayes
 */
public class XSPInputStreamEditorInput extends PlatformObject implements IStorageEditorInput {

	/** The storage. */
	private XSPInputStreamStorage storage;
	
	/** The resource that this stream is for. */
	private IOfsModelResource ofsResource;
	
	/** An id added to the end of the fullPath to create a unique key. */
	private String id;

	/**
	 * Creates a new InputStreamEditorInput.
	 * 
	 * @param contents
	 *            the contents to display
	 * @param readOnly
	 *            Flag indicating whether this EditorInput is read only
	 * @param ofsResource The OFS resource that this stream is for
	 * @param id An id added to the end of the fullPath to create a unique key
	 */
	public XSPInputStreamEditorInput(InputStream contents, boolean readOnly, IOfsModelResource ofsResource, String id) {
		this.ofsResource = ofsResource;
		this.id = id;
		this.storage = new XSPInputStreamStorage(contents, readOnly, ofsResource.getResource(), id);
	}

	/**
	 * Always returns true.
	 * 
	 * @return boolean True
	 */
	public boolean exists() {
		return true;
	}
	
	/**
	 * Gets the resource.
	 * 
	 * @return IResource
	 */
	public IResource getResource() {
		return ofsResource.getResource();
	}

	/**
	 * Returns null.
	 * 
	 * @return null
	 */
	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	/**
	 * Returns the name of the storage.
	 * 
	 * @return String The name of the storage
	 */
	public String getName() {
		return storage.getName();
	}

	/**
	 * Returns null.
	 * 
	 * @return null
	 */
	public IPersistableElement getPersistable() {
		return null;
	}

	/**
	 * Gets the storage.
	 * 
	 * @return IStorage The storage
	 */
	public IStorage getStorage() {
		return storage;
	}

	/**
	 * Returns an empty String.
	 * 
	 * @return String An empty String
	 */
	public String getToolTipText() {
		return "";
	}

	/**
	 * Sets the contents. Note that the editor's user-interface will
	 * not automatically be updated. This needs to be done programatically.
	 * 
	 * @param contents The new contents
	 */
	public void setContents(InputStream contents) {
		this.storage = new XSPInputStreamStorage(contents, true, ofsResource.getResource(), id);
	}
	
	/**
	 * Returns true if the storage is read only.
	 * 
	 * @return boolean True if the storage is read only
	 */
	public boolean isReadOnly() {
		return storage.isReadOnly();
	}	
	
	/**
	 * Sets the flag indicating whether this EditorInput is readonly.
	 * 
	 * @param readOnly True if this editor should be readonly
	 */
	public void setReadOnly(boolean readOnly) {
		storage.setReadOnly(readOnly);
	}
	
	/**
	 * Gets the OFS model resource.
	 * 
	 * @return IOfsModelResource The OFS model resource
	 */
	public final IOfsModelResource getOfsResource() {
		return ofsResource;
	}
}