package com.odcgroup.workbench.core.internal.resources;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.Bundle;

import com.odcgroup.workbench.core.OfsCore;

public class PluginStorage implements IStorage {

	private Bundle bundle;
	private IPath path;
	
	public PluginStorage(Bundle bundle, IPath path) {
		this.bundle = bundle;
		this.path = path;
	}
	
	public InputStream getContents() throws CoreException {
		try {
			return FileLocator.openStream(bundle, path, false);
		} catch (IOException e) {
			IStatus status = new Status(IStatus.ERROR, OfsCore.PLUGIN_ID, "Cannot access plugin contents", e);
			throw new CoreException(status);
		}
	}

	public IPath getFullPath() {
		return path;
	}

	public String getName() {
		return path.lastSegment();
	}

	public boolean isReadOnly() {
		return true;
	}

	public Object getAdapter(Class adapter) {
		return null;
	}

}
