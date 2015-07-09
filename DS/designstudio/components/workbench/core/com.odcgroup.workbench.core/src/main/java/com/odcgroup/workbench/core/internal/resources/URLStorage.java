package com.odcgroup.workbench.core.internal.resources;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;

import com.odcgroup.workbench.core.OfsCore;

public class URLStorage implements IStorage {

	private URL url;
	private URI uri;
	
	public URLStorage(URL url, URI uri) {
		this.url = url;
		this.uri = uri;
	}
	
	public InputStream getContents() throws CoreException {
		try {
			final URLConnection connection = url.openConnection();
			connection.setUseCaches(false);
			return connection.getInputStream();
		} catch (IOException e) {
			IStatus status = new Status(IStatus.ERROR, OfsCore.PLUGIN_ID, "Cannot access URL " + url, e);
			throw new CoreException(status);
		}
	}

	public IPath getFullPath() {
		return new Path(uri.path());
	}

	public String getName() {
		return uri.lastSegment();
	}

	public boolean isReadOnly() {
		return true;
	}

	public Object getAdapter(Class adapter) {
		return null;
	}

}
