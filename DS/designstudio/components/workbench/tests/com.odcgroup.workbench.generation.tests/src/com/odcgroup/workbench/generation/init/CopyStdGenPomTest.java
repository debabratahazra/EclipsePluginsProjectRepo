package com.odcgroup.workbench.generation.init;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.workbench.core.IContainerIdentifier;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.generation.tests.AbstractDSGenerationTest;

public class CopyStdGenPomTest extends AbstractDSGenerationTest {

	private IOfsProject nonStdProject;
	private IOfsProject stdProject;

	@Before
	public void setUp() {
		nonStdProject = createNamedModelsProject("non-std-project");
		stdProject = createNamedModelsProject("pms-models");
	}
	
	@After
	public void tearDown() {
		deleteModelsProjects();
	}

	@Test
	public void testGetStandardProjectContainerIdentifier() {
		// Given
		// (created std/non std projects)
		
		// When
		IContainerIdentifier identifierForNonStdProject = new CodeGenInitializer().getStandardProjectContainerIdentifier(nonStdProject.getProject());
		IContainerIdentifier identifierForStdProject = new CodeGenInitializer().getStandardProjectContainerIdentifier(stdProject.getProject());
		
		// Then
		Assert.assertNull("No identifier should be found", identifierForNonStdProject);
		Assert.assertNotNull("Identifier should be found", identifierForStdProject);
		Assert.assertEquals("Name doesn't match", "pms-models", identifierForStdProject.getName());
		Assert.assertEquals("Wrong groupId", "com.odcgroup.ocs.cbi", identifierForStdProject.getGroupId());
	}

}
