package com.odcgroup.server.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;

import com.odcgroup.server.model.IDSProject;
import com.odcgroup.server.model.IDSServer;
import com.odcgroup.server.model.impl.DSProject;

public abstract class AbstractServerManager implements IServerManager {

	protected List<IDSServer> servers = null;
	
	protected AbstractServerManager() {
		init();
	}

	protected void init() {
		synchronized (AbstractServerManager.class) {
			servers = new ArrayList<IDSServer>();
			for (IProject project: ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
				if (canBeAddedToServersList(project)) {
					addServer(project);
				}
			}
		}
	}
	
	public void reset() {
		init();
	}

	@Override
	public List<IDSServer> getServers() {
		synchronized (AbstractServerManager.class) {
			return servers;
		}
	}

	public synchronized IDSServer addServer(IProject project) {
		if (!canBeAddedToServersList(project)) {
			throw new IllegalArgumentException("The project must be open and have the appropriate server nature");
		}

		for (IDSServer server: getServers()) {
			if (server.getServerProject().equals(project)) {
				throw new IllegalArgumentException("Project " + project.getName() + " already in the list of servers");
			}
		}
		
		// new server
		IDSServer newServer = newServer(project);

		createDsProjects(newServer);
		
		servers.add(newServer);
		return newServer;
	}

	public abstract IDSServer newServer(IProject project);

	/**
	 * @param server
	 */
	public abstract void createDsProjects(IDSServer server);

	public IDSProject reuseOrCreateDsProject(IProject project, List<IDSProject> existingDsProjects) {
		for (IDSProject dsProject: existingDsProjects) {
			if (dsProject.getProjectLocation().equals(project.getLocation())) {
				return dsProject;
			}
		}
		return new DSProject(project);
	}

	@Override
	public boolean canBeAddedToServersList(IProject project) {
		return 
			project != null &&
			project.exists() &&
			project.isOpen() &&
			isServerProject(project);
	}

	public abstract boolean isServerProject(IProject project);

	public synchronized IDSServer removeServer(IProject project) {
		IDSServer serverToRemove = null;
		for (IDSServer server: getServers()) {
			if (project.equals(server.getServerProject())) {
				serverToRemove = server;
				break;
			}
		}
		if (serverToRemove != null) {
			servers.remove(serverToRemove);
			return serverToRemove;
		} else {
			throw new IllegalArgumentException("The project " + project.getName() + " is not referenced as a server project");
		}
	}

	@Override
	public void refresh() {
		synchronized (AbstractServerManager.class) {
			projects: for (IProject project: ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
				if (canBeAddedToServersList(project)) {
					for (IDSServer existingServer: servers) {
						if (project.equals(existingServer.getServerProject())) {
							createDsProjects(existingServer);
							continue projects;
						}
					}
					addServer(project);
				}
			}
		}
	}
	
}
