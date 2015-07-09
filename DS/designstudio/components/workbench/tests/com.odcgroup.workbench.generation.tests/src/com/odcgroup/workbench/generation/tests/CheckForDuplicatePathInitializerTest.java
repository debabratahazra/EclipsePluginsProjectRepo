package com.odcgroup.workbench.generation.tests;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.workbench.generation.GenerationCore;
import com.odcgroup.workbench.generation.init.CheckForDuplicatePathInitializer;

public class CheckForDuplicatePathInitializerTest extends
		AbstractDSGenerationTest {

	private IProject testProject;

	@Before
	public void setUp() throws Exception {
		createModelAndGenProject();
		createNamedModelAndGenProject("clean-project");

		// create a test project and create linked folders that point to existing projects
		testProject = ResourcesPlugin.getWorkspace().getRoot().getProject("test");
		testProject.create(null);
		testProject.open(null);
		testProject.getFolder("models").createLink(getProject().getLocation(), IResource.REPLACE, null);
		for(String genProjectName : GenerationCore.getJavaProjectNames(getProject())) {
			IProject genProject = ResourcesPlugin.getWorkspace().getRoot().getProject(genProjectName);
			testProject.getFolder(genProjectName).createLink(genProject.getLocation(), IResource.REPLACE, null);
		}
	}
	
	@After
	public void tearDown() throws Exception {
		deleteModelsProjects();
		testProject.delete(true, null);
	}
	
	@Test
	public void testDetectDuplicateModelProjectPath_DS3878() {
		CheckForDuplicatePathInitializer initializer = new CheckForDuplicatePathInitializer();
		IStatus status = initializer.checkConfiguration(getProject());
		Assert.assertEquals(IStatus.ERROR, status.getSeverity());
		Assert.assertTrue(status.getChildren().length > 0);
		Assert.assertTrue(status.getChildren()[0].getMessage().contains(testProject.getName()));
		
		status = initializer.checkConfiguration(getProject("clean-project"));
		Assert.assertEquals(IStatus.OK, status.getSeverity());
	}
	
	@Test
	public void testDetectDuplicateGenProjectPath() {
		CheckForDuplicatePathInitializer initializer = new CheckForDuplicatePathInitializer();
		for(String genProjectName : GenerationCore.getJavaProjectNames(getProject())) {
			IProject genProject = ResourcesPlugin.getWorkspace().getRoot().getProject(genProjectName);
			IStatus status = initializer.checkConfiguration(genProject);
			Assert.assertEquals(IStatus.ERROR, status.getSeverity());
			Assert.assertTrue(status.getChildren().length > 0);
			Assert.assertTrue(status.getChildren()[0].getMessage().contains(testProject.getName()));
		}

		for(String genProjectName : GenerationCore.getJavaProjectNames(getProject("clean-project"))) {
			IProject genProject = ResourcesPlugin.getWorkspace().getRoot().getProject(genProjectName);
			IStatus status = initializer.checkConfiguration(genProject);
			Assert.assertEquals(IStatus.OK, status.getSeverity());
		}
}
	
}
