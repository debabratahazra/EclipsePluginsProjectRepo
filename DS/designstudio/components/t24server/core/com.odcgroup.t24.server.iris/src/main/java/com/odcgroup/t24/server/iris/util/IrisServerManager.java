package com.odcgroup.t24.server.iris.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

import com.odcgroup.server.model.IDSProject;
import com.odcgroup.server.model.IDSServer;
import com.odcgroup.server.model.impl.DSProject;
import com.odcgroup.server.util.AbstractServerManager;
import com.odcgroup.t24.server.iris.model.IIrisEmbeddedServer;
import com.odcgroup.t24.server.iris.model.internal.IrisEmbeddedServer;
import com.odcgroup.t24.server.iris.nature.IrisEmbeddedServerNature;

/**
 * TODO: Document me!
 *
 * @author hdebabrata
 *
 */
public class IrisServerManager extends AbstractServerManager implements IIrisServerManager {
	
	static IrisServerManager irisServerManager = new IrisServerManager();
	
	public static IIrisServerManager getInstance() {
		return irisServerManager;
	}
	
	protected IrisServerManager() {
	}

	@Override
	public List<IIrisEmbeddedServer> getIrisServers() {
		synchronized (IrisServerManager.class) {
			List<IIrisEmbeddedServer> irisServers = new ArrayList<IIrisEmbeddedServer>();
			for (IDSServer server : getServers()) {
				irisServers.add((IIrisEmbeddedServer) server);
			}
			return irisServers;
		}
	}

	@Override
	public IDSServer newServer(IProject project) {
		return new IrisEmbeddedServer("com.temenos.ds.embedded.server.iris." + project.getName(), "Iris Server (" + project.getName() + ") " , project);
	}

	@Override
	public void createDsProjects(IDSServer server) {

		List<IDSProject> oldDsProjects = new ArrayList<IDSProject>(server.getDsProjects());
		if (!oldDsProjects.isEmpty() && oldDsProjects.size() > 0) {
			server.updateProjectList(oldDsProjects.toArray(new DSProject[oldDsProjects.size()]));
		}

	}

	@Override
	public boolean isServerProject(IProject project) {
		boolean hasServerNature;
		try {
			hasServerNature = project.hasNature(IrisEmbeddedServerNature.NATURE_ID);
		} catch (CoreException e) {
			hasServerNature = false;
		}
		return hasServerNature;
	}
	
	@Override
	public void refresh() {
		super.refresh();
	}
}
