package com.odcgroup.t24.server.external.ui.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.InputStream;
import java.util.Properties;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;

import com.odcgroup.server.tests.AbstractServerTest;
import com.odcgroup.t24.server.external.model.internal.ExternalT24Server;
import com.odcgroup.workbench.core.IOfsProject;

public class TestExternalServerCreate extends AbstractServerTest {
	
	public static final String DEPLOY_KEY = "deployed.projects";
	public static final String SERVER_PROJECT = "test-server";
	public static final String DS_PROJECT_1 = "ds1-models-gen";
	private IOfsProject iProject_1;
	private IProject serverProject;

	@Before
	public void setup() throws Exception {
	}

	@After
	public void teardown() throws Exception {
		deleteModelsProject(serverProject.getName());
		deleteModelsProject(iProject_1.getName());
	}
	
	@Test
	@Ignore
	public void testVerifyServerPropertiesAfterDSProjectCreation() throws Exception {
		iProject_1 = createNamedModelsProject(DS_PROJECT_1);
		serverProject = T24ServerUtil.createServer(SERVER_PROJECT);
		waitForAutoBuild();
		verifyDeployableProjects();
	}

	private void verifyDeployableProjects() throws Exception {
		IFile file;
		InputStream inputStream = null;
		try {
			IFolder folder = serverProject.getFolder(ExternalT24Server.CONFIG);
			file = folder.getFile(ExternalT24Server.SERVER_PROPERTIES);
			file.refreshLocal(IResource.DEPTH_ZERO, null);

			Properties properties = new Properties();
			assertNotNull(properties);
			inputStream = file.getContents();
			assertNotNull(inputStream);
			properties.load(inputStream);

			String value = properties.getProperty(DEPLOY_KEY);
			assertEquals("server.properties file not updated properly.", DS_PROJECT_1,
					value.trim());
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
	}
	
	@Test
	public void testVerifyServerPropertiesBeforeDSProjectCreation() throws Exception {
		serverProject = T24ServerUtil.createServer(SERVER_PROJECT);
		iProject_1 = createNamedModelsProject(DS_PROJECT_1);
		waitForAutoBuild();
		verifyDeployableProjects();
	}
	
}
