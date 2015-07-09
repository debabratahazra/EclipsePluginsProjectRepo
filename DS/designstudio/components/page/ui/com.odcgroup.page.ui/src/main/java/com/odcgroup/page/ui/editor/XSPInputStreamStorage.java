package com.odcgroup.page.ui.editor;

import java.io.InputStream;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.PlatformObject;

/**
 * Stores data as an InputStream. This is used for memory-based EditorInput's.
 * 
 * @author Gary Hayes
 */
public class XSPInputStreamStorage extends PlatformObject implements IStorage {

	/** The contents of this storage. */
	private InputStream contents;

	/** Flag indicating whether this IStorage is read only. */
	private boolean readOnly;

	 /** The resource that this stream is for. */
	 private IResource resource;
	 
		/** An id added to the end of the fullPath to create a unique key. */
		private String id;
	 
	/**
	 * Creates a new InputStreamEditorInput.
	 * 
	 * @param contents
	 *            the contents to store
	 * @param readOnly
	 *            Flag indicating whether this IStorage is read only
	 * @param resource The resource that this stream is for
	 * @param id An id added to the end of the fullPath to create a unique key
	 */
	public XSPInputStreamStorage(InputStream contents, boolean readOnly, IResource resource, String id) {
		this.contents = contents;
		this.readOnly = readOnly;
		this.resource = resource;
		this.id = id;
	}

	/**
	 * Gets the contents of the IStorage.
	 * 
	 * @return InputStream The contents of the IStorage
	 * @throws CoreException
	 */
	public InputStream getContents() throws CoreException {
		return contents;
	}

	/**
	 * Returns null since the contents are stored in memory.
	 * 
	 * @return null
	 */
	public IPath getFullPath() {
		if (resource == null) {
			return new Path(id);
		}
		return resource.getFullPath().append("_" + id);
	}

	/**
	 * Returns an empty String.
	 * 
	 * @return String An empty String
	 */
	public String getName() {
		// DS-4327: We have to add ".xsp" extension to make sure that WST recognizes the content as XML
		if (resource == null) {
			return id + ".xsp";
		}
		return resource.getName() + ".xsp";
	}

	/**
	 * Returns true if the storage is read only.
	 * 
	 * @return boolean True if the storage is read only
	 */
	public boolean isReadOnly() {
		return readOnly;
	}
	
	/**
	 * Sets the flag indicating whether the storage is readonly.
	 * 
	 * @param readOnly The flag indicating whether the storage is readonly
	 */
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	/**
	 * Sets the contents of this Storage.
	 * 
	 * @param contents The contents to store
	 * @throws CoreException Thrown if an error occurs
	 */
	public void setContents(InputStream contents) throws CoreException {
		this.contents = contents;
	}
	
}