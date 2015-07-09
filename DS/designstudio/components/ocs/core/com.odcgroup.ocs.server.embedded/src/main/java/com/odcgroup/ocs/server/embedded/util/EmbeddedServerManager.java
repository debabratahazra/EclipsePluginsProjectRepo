package com.odcgroup.ocs.server.embedded.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

import com.odcgroup.ocs.server.embedded.model.IEmbeddedServer;
import com.odcgroup.ocs.server.embedded.model.internal.EmbeddedServer;
import com.odcgroup.ocs.server.embedded.nature.EmbeddedServerNature;
import com.odcgroup.server.model.IDSProject;
import com.odcgroup.server.model.IDSServer;
import com.odcgroup.server.model.impl.DSProject;
import com.odcgroup.server.util.AbstractServerManager;

public class EmbeddedServerManager extends AbstractServerManager implements IEmbeddedServerManager {

	static EmbeddedServerManager embeddedServerManager = new EmbeddedServerManager();
	
	public static IEmbeddedServerManager getInstance() {
		return embeddedServerManager;
	}
	
	protected EmbeddedServerManager() {
	}

	public List<IEmbeddedServer> getEmbeddedServers() {
		synchronized (EmbeddedServerManager.class) {
			List<IEmbeddedServer> embeddedServers = new ArrayList<IEmbeddedServer>();
			for (IDSServer server : getServers()) {
				embeddedServers.add((IEmbeddedServer)server);
			}
			return embeddedServers;
		}
	}

	public IDSServer newServer(IProject project) {
		return new EmbeddedServer("com.odcgroup.ocs.server.embedded." + project.getName(), "Embedded Server (" + project.getName() + ")", project);
	}

	public boolean isServerProject(IProject project) {
		boolean hasServerNature;
		try {
			hasServerNature = project.hasNature(EmbeddedServerNature.NATURE_ID);
		} catch (CoreException e) {
			hasServerNature = false;
		}
		return hasServerNature;
	}
	
	/**
	 * @param server
	 */
	public void createDsProjects(IDSServer server) {
		Set<IProject> referencedProjectsInContainer = EmbeddedServerClasspathHelper.getInstance().getWorkspaceReferencedProject(server.getServerProject(), true, false);
		Set<IProject> referencedProjectsInBuildPath = EmbeddedServerClasspathHelper.getInstance().getWorkspaceReferencedProject(server.getServerProject(), false, true);

		createOcsProjects(server, referencedProjectsInContainer,
				referencedProjectsInBuildPath);
	}
	
	/**
	 * @param server
	 * @param lockedProjects
	 * @param unlockedProjects
	 */
	protected void createOcsProjects(IDSServer server,
			Set<IProject> lockedProjects,
			Set<IProject> unlockedProjects) {
		List<IDSProject> oldOcsProjects = new ArrayList<IDSProject>(server.getDsProjects());
		unlockedProjects.removeAll(lockedProjects);

		List<IDSProject> ocsProjects = new ArrayList<IDSProject>(); 
		
		// Project from container are locked (can't be removed)
		for (IProject referencedProject: lockedProjects) {
			IDSProject ocsProject = reuseOrCreateDsProject(referencedProject, oldOcsProjects);
			ocsProject.setLocked(true);
			ocsProjects.add(ocsProject);
		}
		
		// Project from the build path are not locked
		for (IProject referencedProject: unlockedProjects) {
			ocsProjects.add(reuseOrCreateDsProject(referencedProject, oldOcsProjects));
		}
		
		server.updateProjectList(ocsProjects.toArray(new DSProject[ocsProjects.size()]));
	}
	


}
