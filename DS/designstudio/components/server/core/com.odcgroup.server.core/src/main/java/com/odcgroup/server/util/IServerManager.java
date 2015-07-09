package com.odcgroup.server.util;

import java.util.List;

import org.eclipse.core.resources.IProject;

import com.odcgroup.server.model.IDSServer;

public interface IServerManager {

	public List<IDSServer> getServers();

	// TODO Yann: rename this
	public boolean canBeAddedToServersList(IProject project);

	public void refresh();
	
	public void reset();
	
	public boolean isServerProject(IProject project);
	
	public IDSServer addServer(IProject project);
	
	public IDSServer removeServer(IProject project);

}