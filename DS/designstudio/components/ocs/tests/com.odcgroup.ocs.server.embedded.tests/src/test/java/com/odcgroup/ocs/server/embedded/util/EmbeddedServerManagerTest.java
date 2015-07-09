package com.odcgroup.ocs.server.embedded.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.ocs.server.embedded.model.IEmbeddedServer;
import com.odcgroup.ocs.server.embedded.model.internal.EmbeddedServer;
import com.odcgroup.ocs.server.embedded.nature.EmbeddedServerNature;
import com.odcgroup.server.model.IDSProject;
import com.odcgroup.server.model.IDSServer;
import com.odcgroup.server.model.impl.DSProject;
import com.odcgroup.server.tests.AbstractServerTest;
import com.odcgroup.server.util.AbstractServerManager;
import com.odcgroup.server.util.IServerManager;

public class EmbeddedServerManagerTest extends AbstractServerTest {

	private IJavaProject serverProject1;
	private IJavaProject serverProject2;
	private IJavaProject javaProject1;
	private IJavaProject javaProject2;

	@Before
	public void setUp() {
		try {
			serverProject1 = createJavaProject("serverProject1", EmbeddedServerNature.NATURE_ID);
			serverProject2 = createJavaProject("serverProject2", EmbeddedServerNature.NATURE_ID);
			javaProject1 = createJavaProject("javaProject1");
			javaProject2 = createJavaProject("javaProject2");
		} catch (CoreException e) {
			throw new RuntimeException(e);
		}
		EmbeddedServerManager.getInstance().reset();
	}
	
	@After
	public void tearDown() {
		try {
			serverProject1.getProject().delete(true, null);
			serverProject2.getProject().delete(true, null);
			javaProject1.getProject().delete(true, null);
			javaProject2.getProject().delete(true, null);
		} catch (CoreException e) {
			throw new RuntimeException(e);
		}
		EmbeddedServerManager.getInstance().reset();
	}
	
	@Test
	public void testGetEmbeddedServers() throws CoreException {
		// Given 
		
		// When
		List<IEmbeddedServer> servers = EmbeddedServerManager.getInstance().getEmbeddedServers();
		
		// Then
		Assert.assertEquals("only two project have the embedded server nature", 2, servers.size());
	}
	
	@Test
	public void testAddEmbeddedServers() throws CoreException {
		// Given
		EmbeddedServerManager embeddedServerManager = new EmbeddedServerManager();
		Assert.assertEquals(2, embeddedServerManager.getEmbeddedServers().size());

		// When
		IJavaProject newServerProject = null;
		IDSServer addedServer;
		try {
			newServerProject = createJavaProject("newServerProject", EmbeddedServerNature.NATURE_ID);
			addedServer = embeddedServerManager.addServer(newServerProject.getProject());
		} finally {
			if (newServerProject != null) {
				newServerProject.getProject().delete(true, null);
			}
		}
		
		// Then
		Assert.assertEquals(3, embeddedServerManager.getEmbeddedServers().size());
		Assert.assertNotNull(addedServer);
	}
	
	@Test
	public void testAddEmbeddedServersIllegalArgument() throws CoreException {
		// Given
		EmbeddedServerManager embeddedServerManager = new EmbeddedServerManager();

		// When
		IJavaProject javaProject = null;
		boolean illegalArgs = true;
		try {
			javaProject = createJavaProject("newJavaProject");
			try {
				embeddedServerManager.addServer(javaProject.getProject());
			} catch (IllegalArgumentException e) {
				illegalArgs = true;
			}
		} finally {
			if (javaProject != null) {
				javaProject.getProject().delete(true, null);
			}
		}
		
		// Then
		Assert.assertTrue("Illegal args should be thrown", illegalArgs);
	}
	
	@Test
	public void testAddEmbeddedServersIllegalArgument2() throws CoreException {
		// Given
		EmbeddedServerManager embeddedServerManager = new EmbeddedServerManager();

		// When
		IJavaProject createJavaProject = null;
		boolean illegalArgs = false;
		try {
			createJavaProject = createJavaProject("newServerProject", EmbeddedServerNature.NATURE_ID);
			createJavaProject.getProject().close(null);
			try {
				embeddedServerManager.addServer(createJavaProject.getProject());
			} catch (IllegalArgumentException e) {
				illegalArgs = true;
			}
		} finally {
			if (createJavaProject != null) {
				createJavaProject.getProject().delete(true, null);
			}
		}
		
		// Then
		Assert.assertTrue("Illegal args should be thrown", illegalArgs);
	}
	
	@Test
	public void testRemoveEmbeddedServers() throws CoreException {
		// Given
		EmbeddedServerManager embeddedServerManager = new EmbeddedServerManager();
		Assert.assertEquals(2, embeddedServerManager.getEmbeddedServers().size());
		
		// When
		serverProject1.getProject().close(null);
		IDSServer serverRemoved = embeddedServerManager.removeServer(serverProject1.getProject());
		
		// Then
		Assert.assertNotNull(serverRemoved);
		Assert.assertEquals(1, embeddedServerManager.getEmbeddedServers().size());
	}
	
	@Test
	public void testRemoveEmbeddedServersReturnNull() throws CoreException {
		IJavaProject newServerProject = null;
		try {
			// Given
			EmbeddedServerManager embeddedServerManager = new EmbeddedServerManager();
			newServerProject = createJavaProject("newServerProject", EmbeddedServerNature.NATURE_ID);
			
			// When
			boolean illegalArgsThrown = false;
			try {
				embeddedServerManager.removeServer(newServerProject.getProject());
			} catch (IllegalArgumentException e) {
				illegalArgsThrown = true;
			}
			
			// Then
			Assert.assertTrue("Should throw illegal args because the project is still valid (exist, open, with the embeddedserver nature)", illegalArgsThrown);
		} finally {
			if (newServerProject != null) {
				newServerProject.getProject().delete(true, null);
			}
		}
	}

	@Test
	public void testReuseOrCreateOCSProject_Existing() {
		// Given
		List<IDSProject> projects = new ArrayList<IDSProject>();
		DSProject existingOcsProject = new DSProject(javaProject1.getProject());
		projects.add(existingOcsProject);
		
		// When
		IDSProject ocsProject = ((AbstractServerManager)EmbeddedServerManager.getInstance()).reuseOrCreateDsProject(javaProject1.getProject(), projects);
		
		// Then
		Assert.assertNotNull("Should be found", ocsProject);
		Assert.assertTrue("Should be reused", existingOcsProject==ocsProject);
		Assert.assertEquals("Should reference the location of the java project", javaProject1.getProject().getLocation(), ocsProject.getProjectLocation());
	}
	
	@Test
	public void testReuseOrCreateOCSProject_NonExisting() {
		// Given
		List<IDSProject> projects = new ArrayList<IDSProject>();
		
		// When
		IDSProject ocsProject = ((AbstractServerManager)EmbeddedServerManager.getInstance()).reuseOrCreateDsProject(javaProject1.getProject(), projects);
		DSProject existingOcsProject = new DSProject(javaProject2.getProject());
		projects.add(existingOcsProject);
		
		// Then
		Assert.assertNotNull("Should be created", ocsProject);
		Assert.assertEquals("Should match with the java project location", javaProject1.getProject().getLocation(), ocsProject.getProjectLocation());
	}
	
	@Test
	public void testCreateOcsProjects() {
		// Given
		IEmbeddedServer server = new EmbeddedServer("someId", "someServerName", serverProject1.getProject());
		Set<IProject> referencedProjectsInContainer = new HashSet<IProject>();
		referencedProjectsInContainer.add(javaProject1.getProject());
		Set<IProject> referencedProjectsInBuildPath = new HashSet<IProject>(); 
		referencedProjectsInBuildPath.add(javaProject2.getProject());
		
		// When
		((EmbeddedServerManager)EmbeddedServerManager.getInstance()).createOcsProjects(server, referencedProjectsInContainer, referencedProjectsInBuildPath);
		
		// Then
		List<IDSProject> ocsProjects = server.getDsProjects();
		Assert.assertEquals(2, ocsProjects.size());
		
		IDSProject projectReferencedByContainer = ocsProjects.get(0);
		IDSProject projectReferencedByBuildPath = ocsProjects.get(1);
		if (!projectReferencedByContainer.getProjectLocation().equals(javaProject1.getProject().getLocation())) {
			IDSProject tmp = projectReferencedByBuildPath;
			projectReferencedByContainer = projectReferencedByBuildPath;
			projectReferencedByBuildPath = tmp;
		}

		Assert.assertEquals(javaProject1.getProject().getLocation(), projectReferencedByContainer.getProjectLocation());
		Assert.assertEquals(javaProject2.getProject().getLocation(), projectReferencedByBuildPath.getProjectLocation());
		
		Assert.assertTrue(projectReferencedByContainer.isLocked());
		Assert.assertFalse(projectReferencedByBuildPath.isLocked());
	}
	
	@Test
	public void testRefresh() {
		// Given
		final IEmbeddedServer server1 = new EmbeddedServer("server1", "server1", serverProject1.getProject());
		IServerManager manager = new EmbeddedServerManager() {
			@Override
			protected void init() {
				servers = new ArrayList<IDSServer>();
				servers.add(server1);
			}
		};
		Assert.assertEquals(1, manager.getServers().size());
		
		// When
		manager.refresh();
		
		// Then
		Assert.assertEquals(2, manager.getServers().size());
	}

}
