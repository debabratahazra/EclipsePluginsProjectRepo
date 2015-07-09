package com.odcgroup.server.model.impl;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.odcgroup.server.model.IDSProject;
import com.odcgroup.server.model.IDSServer;

public class DSProject implements IDSProject {
	
	private String name;
	private IDSServer server;
	private IPath projectLocation;
	private boolean locked = false;
	
	/**
	 * @param name
	 * @param path
	 */
	public DSProject(String name, String path) {
		this.name = name;
		this.projectLocation = new Path(path);
	}
	
	/**
	 * @param project
	 */
	public DSProject(IProject project) {
		this.name = project.getName();
		this.projectLocation = project.getLocation();		
	}

	public boolean exists() {
		return false;
	}

	public String getName() {
		return this.name;
	}

	public IPath getProjectLocation() {
		return this.projectLocation;
	}	

	public IDSServer getServer() {
		return this.server;
	}

	public void setServer(IDSServer dsServer) {
		this.server = dsServer;
	}

	/**
	 * @return the locked
	 */
	public boolean isLocked() {
		return locked;
	}

	/**
	 * @param locked the locked to set
	 */
	public void setLocked(boolean locked) {
		this.locked = locked;
	}

}
