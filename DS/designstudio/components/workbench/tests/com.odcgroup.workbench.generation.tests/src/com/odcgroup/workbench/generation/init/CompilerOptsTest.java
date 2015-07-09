package com.odcgroup.workbench.generation.init;

import java.io.IOException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.workbench.generation.GenerationCore;
import com.odcgroup.workbench.generation.tests.AbstractDSGenerationTest;

/**
 * @author kkr
 */

public class CompilerOptsTest extends AbstractDSGenerationTest {

	@Before
	public void setUp() {
		createModelAndGenProject();
	}
	
	@After
	public void tearDown() {
		deleteModelsProjects();
	}

	@Test
	public void testGenPom() throws CoreException, IOException {
		for (String genProjectName : GenerationCore.getJavaProjectNames(getProject())) {
			IProject genProject = ResourcesPlugin.getWorkspace().getRoot().getProject(genProjectName);
			IJavaProject javaProject = JavaCore.create(genProject);
			for(String option : CodeGenInitializer.compilerOptions) {
				Assert.assertEquals("ignore", javaProject.getOption(option, true));
			}
		}
	}
}
