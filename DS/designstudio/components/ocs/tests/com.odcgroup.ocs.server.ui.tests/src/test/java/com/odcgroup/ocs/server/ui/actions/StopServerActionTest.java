package com.odcgroup.ocs.server.ui.actions;

import org.eclipse.core.resources.IProject;
import org.junit.Assert;
import org.junit.Test;

import com.odcgroup.server.model.IDSServerStates;
import com.odcgroup.server.model.impl.DSServer;
import com.odcgroup.server.ui.IServerContributions;
import com.odcgroup.server.ui.actions.StopServerAction;

public class StopServerActionTest  {

	@Test
	public void testStopWhenServerStarted() {
		// Given
		final MockServerContributions mockContibutions = new MockServerContributions();

		StopServerAction action = new StopServerAction(null, ServerActionTestHelper.newMockSelectionProvider()) {
			@Override
			protected IServerContributions getContributions() {
				return mockContibutions;
			}
		};

		DSServer server = new DSServer("someId", "someName") {
			@Override
			public String getListeningPort() {
				return "123";
			}
			@Override
			public String getLogDirectory() {
				return "C:/fakeDir/logs/";
			}
			@Override
			public String getInstallationDirectory() {
				return "C:/fakeDir/";
			}
			@Override
			public IProject getServerProject() {
				return null;
			}
			@Override
			public boolean canDeploy(IProject project) {
				return false;
			}
		};
		server.setState(IDSServerStates.STATE_STARTED);

		// When
		action.run(server);

		// Then
		Assert.assertEquals(0, mockContibutions.startCalled);
		Assert.assertEquals(0, mockContibutions.startInDebugCalled);
		Assert.assertEquals(1, mockContibutions.stopCalled);
	}

	@Test
	public void testStopWhenServerStopped() {
		// Given
		final MockServerContributions mockContibutions = new MockServerContributions();

		StopServerAction action = new StopServerAction(null, ServerActionTestHelper.newMockSelectionProvider()) {
			@Override
			protected IServerContributions getContributions() {
				return mockContibutions;
			}
		};

		DSServer server = new DSServer("someId", "someName") {
			@Override
			public String getListeningPort() {
				return "123";
			}
			@Override
			public String getLogDirectory() {
				return "C:/fakeDir/logs/";
			}
			@Override
			public String getInstallationDirectory() {
				return "C:/fakeDir/";
			}
			@Override
			public IProject getServerProject() {
				return null;
			}
			@Override
			public boolean canDeploy(IProject project) {
				return false;
			}
		};
		server.setState(IDSServerStates.STATE_STOPPED);

		// When
		action.run(server);

		// Then
		Assert.assertEquals(0, mockContibutions.startCalled);
		Assert.assertEquals(0, mockContibutions.startInDebugCalled);
		Assert.assertEquals(0, mockContibutions.stopCalled);
	}

}
