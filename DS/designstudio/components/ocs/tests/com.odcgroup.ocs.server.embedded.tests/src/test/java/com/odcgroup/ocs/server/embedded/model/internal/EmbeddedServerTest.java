package com.odcgroup.ocs.server.embedded.model.internal;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.ocs.server.tests.ServerTestsCore;

public class EmbeddedServerTest  {
	
	IProject serverProject;
	IProject notProperlyInitializedServerProject;
	
	@Before
	public void setUp() {
		serverProject = createProject("embeddedServer");
		notProperlyInitializedServerProject = createProject("notProperlyInitializedServerProject");
	}
	
	@After
	public void tearDown() {
		try {
			serverProject.delete(true, null);
		} catch (CoreException e) {
			// Ignore
		}
		try {
			notProperlyInitializedServerProject.delete(true, null);
		} catch (CoreException e) {
			// Ignore
		}
	}

	/**
	 * @param projectName
	 * @throws CoreException
	 */
	private IProject createProject(String projectName) {
		try {
			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
			project.create(null);
			project.open(null);
			return project;
		} catch (CoreException e) {
			throw new RuntimeException("unable to create " + projectName, e);
		}
	}
	
	/**
	 * @param serverProject
	 * @return
	 */
	private void copyServerProperties(IProject newServerProject, String from, String to) {
		try {
			newServerProject.getFolder("config").create(true, true, null);
		} catch (CoreException e) {
			throw new RuntimeException("Unable to create the server project", e);		
		}
		IFile targetFile = newServerProject.getFile(to);
		
		URL url = FileLocator.find(ServerTestsCore.getDefault().getBundle(), new Path("/resources/embedded-server/" + from), null);
        File serverProperties;
		try {
			serverProperties = new File(FileLocator.toFileURL(url).toURI());
		} catch (Exception e) {
			throw new RuntimeException("Unable to find: " + url, e);
		}

        try {
			targetFile.create(new FileInputStream(serverProperties), true, null);
		} catch (Exception e) {
			throw new RuntimeException("Unable to create the file in the project", e);
		}
	}

	@Test
	public void testGetListeningPort() {
		// Given
		copyServerProperties(serverProject, "config/server.properties", "config/server.properties");
		EmbeddedServer embeddedServer = new EmbeddedServer("someId", "some name", serverProject);
		
		// Then
		Assert.assertEquals("8081", embeddedServer.getListeningPort());
	}

	@Test
	public void testGetListeningPortDefaultValue() {
		// Given
		copyServerProperties(serverProject, "config/server2.properties", "config/server.properties");
		EmbeddedServer embeddedServer = new EmbeddedServer("someId", "some name", serverProject);
		
		// Then
		Assert.assertEquals(EmbeddedServer.DEFAULT_LISTENING_PORT, embeddedServer.getListeningPort());
	}

	@Test
	public void testGetListeningPortNoServerProperties1() throws CoreException {
		// Given
		EmbeddedServer embeddedServer = new EmbeddedServer("someId", "some name", notProperlyInitializedServerProject);
		
		// Then
		Assert.assertEquals("", embeddedServer.getListeningPort());
	}
	
	@Test
	public void testGetListeningPortNoServerProperties2() throws CoreException {
		// Given
		EmbeddedServer embeddedServer = new EmbeddedServer("someId", "some name", notProperlyInitializedServerProject) {
			@Override
			protected File getServerPropertiesFile() {
				// return an existing folder instead of an existing file
				return serverProject.getFile("/dummy").getFullPath().toFile();
			}
			
		};

		// Then
		Assert.assertEquals("", embeddedServer.getListeningPort());
	}

	//TODO Test getListeningPort avec default value
	
	@Test
	public void testGetVmArguments() {
		// Given
		copyServerProperties(serverProject, "config/server.properties", "config/server.properties");
		EmbeddedServer embeddedServer = new EmbeddedServer("someId", "some name", serverProject);
		
		// Then
		Assert.assertEquals("some vm args", embeddedServer.getVmArguments());
	}

	@Test
	public void testGetVmArgumentsDefaultValue() {
		// Given
		copyServerProperties(serverProject, "config/server2.properties", "config/server.properties");
		EmbeddedServer embeddedServer = new EmbeddedServer("someId", "some name", serverProject);
		
		// Then
		Assert.assertEquals(EmbeddedServer.DEFAULT_VM_ARGUMENTS, embeddedServer.getVmArguments());
	}
	
	@Test
	public void testGetStarterClass() {
		// Given
		copyServerProperties(serverProject, "config/server.properties", "config/server.properties");
		EmbeddedServer embeddedServer = new EmbeddedServer("someId", "some name", serverProject);
		
		// Then
		Assert.assertEquals("some.server.StarterClass", embeddedServer.getStarterClass());
	}
	
	@Test
	public void testGetStarterClassError() throws CoreException {
		// Given
		EmbeddedServer embeddedServer = new EmbeddedServer("someId", "some name", notProperlyInitializedServerProject);
		
		// Then
		Assert.assertEquals("", embeddedServer.getStarterClass());
	}

	@Test
	public void testGetStarterClassErrorDefaultValue() {
		// Given
		copyServerProperties(serverProject, "config/server2.properties", "config/server.properties");
		EmbeddedServer embeddedServer = new EmbeddedServer("someId", "some name", serverProject);
		
		// Then
		Assert.assertEquals(EmbeddedServer.DEFAULT_STARER_CLASS, embeddedServer.getStarterClass());
	}

	@Test
	public void testGetServerProject() {
		// Given
		EmbeddedServer embeddedServer = new EmbeddedServer("someId", "some name", serverProject);
		
		// Then
		Assert.assertEquals(serverProject, embeddedServer.getServerProject());
	}

}
