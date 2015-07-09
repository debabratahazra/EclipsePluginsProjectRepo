package com.odcgroup.workbench.ui;

import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.IStorageEditorInput;
import org.eclipse.ui.PlatformUI;

/**
 * @author pkk
 *
 */
public class ModelEditorInput implements IStorageEditorInput {
	
	private IStorage storage;
	
	/**
	 * @param model
	 */
	public ModelEditorInput(IStorage storage) {
		this.storage = storage;
	}

	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IStorageEditorInput#getStorage()
	 */
	public IStorage getStorage() throws CoreException {
		return storage;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IEditorInput#exists()
	 */
	public boolean exists() {
		return true;
	}
	
	/**
	 * @return
	 */
	public String getContentType() {
		return storage.getFullPath().getFileExtension();
	}

	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IEditorInput#getImageDescriptor()
	 */
	public ImageDescriptor getImageDescriptor() {
		IEditorRegistry registry= PlatformUI.getWorkbench().getEditorRegistry();
		return registry.getImageDescriptor(getContentType());
	}

	
	public String getName() {
		String name = storage.getName();
		if(storage.isReadOnly()) name += " (r/o)";
		return name;
	}

	
	public IPersistableElement getPersistable() {
		return null;
	}

	
	public String getToolTipText() {
		String tooltip = storage.getFullPath().toString();
		if(storage.isReadOnly()) tooltip += " (r/o)";
		return tooltip;
	}

	
	public Object getAdapter(Class adapter) {
		return null;
	}

	
}