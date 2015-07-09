package com.odcgroup.ocs.server.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.ocs.server.ui.logs.LogWatcherManager;
import com.odcgroup.server.model.IDSProject;
import com.odcgroup.server.model.IDSServer;
import com.odcgroup.server.model.impl.DSProject;
import com.odcgroup.server.model.impl.DSServer;
import com.odcgroup.server.tests.AbstractServerTest;
import com.odcgroup.server.ui.AggregatorContribution;
import com.odcgroup.server.ui.IServerContributions;
import com.odcgroup.server.ui.IServerExternalChangeListener;
import com.odcgroup.server.ui.UnableToStartServerException;
import com.odcgroup.server.ui.UnableToStopServerException;
import com.odcgroup.server.ui.views.IAddAction;

public class AggregatorContributionTest extends AbstractServerTest {

	class MockContribution implements IServerContributions {
		boolean startCalled;
		
		@Override 
		public void start(IDSServer server, boolean debug) {
			startCalled = true;
		}

		// Dummy implementation for the remaining methods
		@Override public void refreshServers() {}
		@Override public List<IDSServer> getServers() { return null; }
		@Override public void addListenerServerAddedRemovedExternally(IServerExternalChangeListener listener) { }
		@Override public void fillAddServerToolbarMenu(IAddAction addAction) { }
		@Override public void fillAddServerContextMenu(IDSServer server, IAddAction addAction) { }
		@Override public void fillConfigureServerContextMenu(IDSServer server, IAddAction addAction) { }
		@Override public void updateDeployedProjects(IDSServer server, IDSProject[] projects) { }
		@Override public void stop(IDSServer server) throws UnableToStopServerException { }
		@Override public void notifyProjectChanged(IProject project, ChangeKind kind) { }
	}
	
	class MockServer extends DSServer {
		
		public MockServer() {
			super("someId", "someName");
		}
		
		@Override public String getListeningPort() { return null; }
		@Override public String getLogDirectory() { return null; }
		@Override public String getInstallationDirectory() { return null; }
		@Override public IProject getServerProject() { return null; }
		@Override public boolean canDeploy(IProject project) { return false; }
	}
	
	private MockContribution mockContribution;
	private AggregatorContribution aggregator;
	private IDSServer server;
	private IJavaProject genProject;

	@Before
	public void setUp() {
		mockContribution = new MockContribution();
		List<IServerContributions> contributions = new ArrayList<IServerContributions>();
		contributions.add(mockContribution);
		aggregator = new AggregatorContribution(contributions);
		
		server = new MockServer();
		
		try {
			createModelsProject();
			genProject = createJavaProject(DEFAULT_MODELS_PROJECT + "-gen");
			server.addDsProject(new DSProject(genProject.getProject()));
		} catch (CoreException e) {
			throw new RuntimeException("Unable to create the java project", e);
		}
		
		OcsServerUICore.getDefault().logWatcherManager = new LogWatcherManager() {
			public void startAllLogWatchers(IDSServer server) {
				// disable this behavior
			}
		};
	}
	
	@After
	public void tearDown() {
		deleteModelsProjects();
		try {
			genProject.getProject().delete(true, null);
		} catch (CoreException e) {
			// Ignore
		}
	}

	@Test
	public void testStartCanceledBecauseOfEmptyProject() throws UnableToStartServerException {
		// Given
		
		// When
		boolean unableToStart = false;
		try {
			aggregator.start(server, true);
		} catch (UnableToStartServerException e) {
			unableToStart = true;
		}
		
		// Then
		Assert.assertTrue("The start should have raised UnableToStartException", unableToStart);
		Assert.assertTrue("Start shouldn't have started", mockContribution.startCalled);
	}


}
