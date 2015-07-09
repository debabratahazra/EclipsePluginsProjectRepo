package com.odcgroup.t24.server.external.ui.tests;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.server.tests.AbstractServerTest;
import com.odcgroup.t24.server.external.model.IExternalServer;
import com.odcgroup.t24.server.external.ui.T24ServerUIExternalCore;

public class T24ServerUIExternalCoreTest extends AbstractServerTest{
	
	private IProject serverProject;
	private static final String SERVER_PROJECT = "test-server";

	@Before
	public void setup() throws Exception {
		serverProject = T24ServerUtil.createServer(SERVER_PROJECT);
	}
	
	@After
	public void teardown() throws Exception {
		deleteModelsProject(serverProject.getName());
	}
	
	@Test
	public void testGetDisplayableExternalServers() throws Exception {
		T24ServerUIExternalCore t24ServerUIExternalCore = new T24ServerUIExternalCore();
		List<IExternalServer> displayableExternalServers = t24ServerUIExternalCore.getDisplayableExternalServers();
		Assert.assertEquals(1, displayableExternalServers.size());
	}
	
}
