package com.odcgroup.ocs.server.embedded.ui;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.ocs.server.embedded.model.internal.EmbeddedServer;
import com.odcgroup.server.model.impl.DSProject;
import com.odcgroup.server.tests.AbstractServerTest;

public class EmbeddedServerLauncherHelperTest extends AbstractServerTest {

	IProject serverProject;
	IJavaProject project;

	@Before
	public void setUp() {
		try {
			serverProject = ResourcesPlugin.getWorkspace().getRoot().getProject("server");
			serverProject.create(null);
			serverProject.open(null);
			
			project = createJavaProject("someProject");
		} catch (CoreException e) {
			throw new RuntimeException(e);
		}
	}
	
	@After
	public void tearDown() {
		try {
			serverProject.delete(true, null);
		} catch (CoreException e) {
			// Ignore
		}
		try {
			project.getProject().delete(true, null);
		} catch (CoreException e) {
			// Ignore
		}
	}
	
	@Test
	public void testCheckProjectsConfiguration_Ok() {
		// Given
		class EmbeddedServerLauncherHelperSpy extends EmbeddedServerLauncherHelper {
			public boolean warningDisplayed=false;
			@Override
			protected boolean displayWarningMessage(
					List<String> projectsWithConfigProblem) {
				warningDisplayed = true;
				return true;
			}
		};
		EmbeddedServerLauncherHelperSpy helperSpy = new EmbeddedServerLauncherHelperSpy();
		
		EmbeddedServer embeddedServer = new EmbeddedServer("someId", "someName", serverProject);
		embeddedServer.addDsProject(new DSProject(project.getProject()));
		
		// When
		helperSpy.checkProjectsConfiguration(embeddedServer);
		
		// Then
		Assert.assertFalse(helperSpy.warningDisplayed);
	}

	@Test
	public void testCheckProjectsConfiguration_NotOk() throws JavaModelException {
		// Given
		class EmbeddedServerLauncherHelperSpy extends EmbeddedServerLauncherHelper {
			public boolean warningDisplayed=false;
			@Override
			protected boolean displayWarningMessage(
					List<String> projectsWithConfigProblem) {
				warningDisplayed = true;
				return true;
			}
		};
		EmbeddedServerLauncherHelperSpy helperSpy = new EmbeddedServerLauncherHelperSpy();
		
		EmbeddedServer embeddedServer = new EmbeddedServer("someId", "someName", serverProject);
		embeddedServer.addDsProject(new DSProject(project.getProject()));

		IPath sourceFolder = new Path("/" + project.getProject().getName() + "/src");
		IPath outputLocation = sourceFolder.append("bin");
		IClasspathEntry srcEntry = JavaCore.newSourceEntry(
				sourceFolder, // source folder location
				new IPath[] { outputLocation }); // excluded nested folder

		project.setRawClasspath(new IClasspathEntry[] {srcEntry}, null);

		// When
		helperSpy.checkProjectsConfiguration(embeddedServer);
		
		// Then
		Assert.assertTrue(helperSpy.warningDisplayed);
	}


}
