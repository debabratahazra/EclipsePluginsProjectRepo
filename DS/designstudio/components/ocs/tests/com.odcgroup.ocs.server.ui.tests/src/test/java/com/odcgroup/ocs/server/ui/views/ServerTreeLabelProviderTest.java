package com.odcgroup.ocs.server.ui.views;

import org.eclipse.core.resources.IProject;
import org.junit.Assert;
import org.junit.Test;

import com.odcgroup.server.model.IDSServer;
import com.odcgroup.server.model.impl.DSServer;
import com.odcgroup.server.ui.views.ServerTreeLabelProvider;

public class ServerTreeLabelProviderTest  {

	private static final String LISTENING_PORT = "8080";
	private static final String SERVER_NAME = "Some server name";
	private static final String ON = " on ";

	@Test
	public void testGetTextOk() {
		// Given
		IDSServer server = newServer(SERVER_NAME, LISTENING_PORT);

		// When
		String label = new ServerTreeLabelProvider().getText(server);

		// Then
		Assert.assertTrue(label.startsWith(SERVER_NAME));
		Assert.assertTrue(label.contains(ON));
		Assert.assertTrue(label.endsWith(LISTENING_PORT));
	}

	@Test
	public void testGetTextListeningPortNull() {
		// Given
		IDSServer server = newServer(SERVER_NAME, null);

		// When
		String label = new ServerTreeLabelProvider().getText(server);

		// Then
		Assert.assertTrue(label.startsWith(SERVER_NAME));
		Assert.assertFalse(label.contains(ON));
	}

	@Test
	public void testGetTextListeningPortEmpty() {
		// Given
		IDSServer server = newServer(SERVER_NAME, "");

		// When
		String label = new ServerTreeLabelProvider().getText(server);

		// Then
		Assert.assertTrue(label.startsWith(SERVER_NAME));
		Assert.assertFalse(label.contains(ON));
	}

	public IDSServer newServer(String name, final String listeningPort) {
		IDSServer server = new DSServer("someId", name) {
			public String getLogDirectory() {
				return null;
			}
			public String getListeningPort() {
				return listeningPort;
			}
			@Override
			public String getInstallationDirectory() {
				return null;
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
		return server;
	}
}
