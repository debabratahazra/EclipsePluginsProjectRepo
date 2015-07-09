package com.odcgroup.workbench.core.init;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

public class OfsProjectInitializerTest extends AbstractDSUnitTest {

	@Before
	public void setUp() throws Exception {
		createModelsProject();
	}

	@After
	public void tearDown() throws Exception {
		deleteModelsProjects();
	}
	
	@Test
	public void testOfsBuilderFirst_DS3526() throws CoreException {

		OfsProjectInitializer initializer = new OfsProjectInitializer();		
		initializer.updateConfiguration(getProject(), null);
		
		Assert.assertTrue(initializer.checkConfiguration(getProject()).isOK());
		
		IProjectDescription desc = getProject().getDescription();
		ICommand[] commands = desc.getBuildSpec();
		if(commands.length == 0 || !commands[0].getBuilderName().equals(OfsCore.BUILDER_ID)) {
			Assert.fail();
		}
	}
	
	@Test
	public void testSetTargetFolder() throws CoreException {

		OfsProjectInitializer initializer = new OfsProjectInitializer();	
		IProject project = getProject();
		if(project.hasNature("org.eclipse.jdt.core.javanature")) {
			IFolder binFolder = project.getFolder("bin");
			if(!binFolder.exists()) { 
				binFolder.create(false, true, null);	
			}
			IJavaProject javaProject = JavaCore.create(project);
			javaProject.setOutputLocation(binFolder.getFullPath(), null);
		}
		initializer.updateConfiguration(project, null);
		
		Assert.assertTrue(initializer.checkConfiguration(getProject()).isOK());
		
		IProjectDescription desc = getProject().getDescription();
		ICommand[] commands = desc.getBuildSpec();
		if(commands.length == 0 || !commands[0].getBuilderName().equals(OfsCore.BUILDER_ID)) {
			Assert.fail();
		}
	}
}
