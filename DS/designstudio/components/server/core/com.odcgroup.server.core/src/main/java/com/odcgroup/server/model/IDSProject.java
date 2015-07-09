package com.odcgroup.server.model;

import org.eclipse.core.runtime.IPath;

public interface IDSProject  {
	
	public String getName();
	
	public boolean exists();
	
	public IPath getProjectLocation();
	
	public IDSServer getServer();
	
	public void setServer(IDSServer dsServer);
	
	public void setLocked(boolean locked);
	
	public boolean isLocked();

}
