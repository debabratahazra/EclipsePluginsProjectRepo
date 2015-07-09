package com.odcgroup.server.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;

import com.odcgroup.server.model.IDSProject;
import com.odcgroup.server.model.IDSServer;
import com.odcgroup.server.ui.views.IAddAction;

public class AggregatorContribution implements IServerContributions {
	
	private List<IServerContributions> contributions;
	
	public AggregatorContribution(List<IServerContributions> contributions) {
		this.contributions = contributions;
	}

	@Override
	public void refreshServers() {
		for (IServerContributions contribution: contributions) {
			contribution.refreshServers();
		}
	}

	@Override
	public List<IDSServer> getServers() {
		List<IDSServer> servers = new ArrayList<IDSServer>();
		for (IServerContributions contribution: contributions) {
			servers.addAll(contribution.getServers());
		}
		return servers;
	}
	
	@Override
	public void notifyProjectChanged(IProject project, IServerContributions.ChangeKind kind) {
		for (IServerContributions contribution: contributions) {
			contribution.notifyProjectChanged(project, kind);
		}
	}

	@Override
	public void addListenerServerAddedRemovedExternally(
			IServerExternalChangeListener listener) {
		for (IServerContributions contribution: contributions) {
			contribution.addListenerServerAddedRemovedExternally(listener);
		}
	}

	@Override
	public void fillAddServerToolbarMenu(IAddAction addAction) {
		for (IServerContributions contribution: contributions) {
			contribution.fillAddServerToolbarMenu(addAction);
		}
	}

	@Override
	public void fillAddServerContextMenu(IDSServer server,	IAddAction addAction) {
		for (IServerContributions contribution: contributions) {
			contribution.fillAddServerContextMenu(server, addAction);
		}
	}

	@Override
	public void fillConfigureServerContextMenu(IDSServer server,IAddAction addAction) {
		for (IServerContributions contribution: contributions) {
			contribution.fillConfigureServerContextMenu(server, addAction);
		}
	}

	@Override
	public void updateDeployedProjects(IDSServer server,
			IDSProject[] projects) {
		for (IServerContributions contribution: contributions) {
			contribution.updateDeployedProjects(server, projects);
		}
	}

	@Override
	public void start(IDSServer server, boolean debug) throws UnableToStartServerException {
		for (IServerContributions contribution: contributions) {
			contribution.start(server, debug);
		}
	}

	@Override
	public void stop(IDSServer server) throws UnableToStopServerException {
		for (IServerContributions contribution: contributions) {
			contribution.stop(server);
		}
	}

}