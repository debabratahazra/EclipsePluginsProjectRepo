package com.odcgroup.ocs.server.ui.actions;

import java.util.List;

import org.eclipse.core.resources.IProject;

import com.odcgroup.server.model.IDSProject;
import com.odcgroup.server.model.IDSServer;
import com.odcgroup.server.ui.IServerContributions;
import com.odcgroup.server.ui.IServerExternalChangeListener;
import com.odcgroup.server.ui.UnableToStartServerException;
import com.odcgroup.server.ui.UnableToStopServerException;
import com.odcgroup.server.ui.views.IAddAction;

public class MockServerContributions implements IServerContributions {
	
	int startInDebugCalled;
	int startCalled;
	int stopCalled;
	
	@Override
	public void refreshServers() {
	}

	@Override
	public void addListenerServerAddedRemovedExternally(
			IServerExternalChangeListener listener) {
	}

	@Override
	public void fillAddServerToolbarMenu(IAddAction addAction) {
	}

	@Override
	public void fillAddServerContextMenu(IDSServer server, IAddAction addAction) {
	}

	@Override
	public void fillConfigureServerContextMenu(IDSServer server,
			IAddAction addAction) {
	}

	@Override
	public void updateDeployedProjects(IDSServer server, IDSProject[] projects) {
	}

	@Override
	public void start(IDSServer server, boolean debug)
			throws UnableToStartServerException {
		if (debug) {
			startInDebugCalled++;
		} else {
			startCalled++;
		}
	}

	@Override
	public void stop(IDSServer server) throws UnableToStopServerException {
		stopCalled++;
	}

	@Override
	public List<IDSServer> getServers() {
		return null;
	}

	@Override
	public void notifyProjectChanged(IProject project, ChangeKind kind) {
	}

}
