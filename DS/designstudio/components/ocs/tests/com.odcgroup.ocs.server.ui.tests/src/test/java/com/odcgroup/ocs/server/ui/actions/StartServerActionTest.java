package com.odcgroup.ocs.server.ui.actions;

import org.eclipse.core.resources.IProject;
import org.junit.Assert;
import org.junit.Test;

import com.odcgroup.server.model.IDSServerStates;
import com.odcgroup.server.model.impl.DSServer;
import com.odcgroup.server.ui.IServerContributions;
import com.odcgroup.server.ui.actions.StartServerAction;

public class StartServerActionTest  {

	@Test
	public void testStartWhenStopped() {
		// Given
		final MockServerContributions mockContibutions = new MockServerContributions();

		StartServerAction action = new StartServerAction(null, ServerActionTestHelper.newMockSelectionProvider()) {
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
		Assert.assertEquals(1, mockContibutions.startCalled);
		Assert.assertEquals(0, mockContibutions.stopCalled);
		Assert.assertEquals(0, mockContibutions.startInDebugCalled);
	}

	@Test
	public void testStartWhenRunning() {
		// Given
		final MockServerContributions mockContibutions = new MockServerContributions();

		StartServerAction action = new StartServerAction(null, ServerActionTestHelper.newMockSelectionProvider()) {
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
		Assert.assertEquals(1, mockContibutions.startCalled);
		Assert.assertEquals(1, mockContibutions.stopCalled);
		Assert.assertEquals(0, mockContibutions.startInDebugCalled);
	}

}
