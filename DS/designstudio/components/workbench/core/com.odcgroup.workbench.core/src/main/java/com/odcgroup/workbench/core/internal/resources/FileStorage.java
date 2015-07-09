package com.odcgroup.workbench.core.internal.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;

import com.odcgroup.workbench.core.OfsCore;

public class FileStorage implements IStorage {

	private File file;
	private URI uri;
	
	public FileStorage(File file, URI uri) {
		this.file = file;
		this.uri = uri;
	}
	
	public InputStream getContents() throws CoreException {
		try {
			return new FileInputStream(file);
		} catch (IOException e) {
			IStatus status = new Status(IStatus.ERROR, OfsCore.PLUGIN_ID, "Cannot access plugin contents", e);
			throw new CoreException(status);
		}
	}

	public IPath getFullPath() {
		return new Path(uri.path());
	}

	public String getName() {
		return file.getName();
	}

	public boolean isReadOnly() {
		return !file.canWrite();
	}

	public Object getAdapter(Class adapter) {
		return null;
	}

}
