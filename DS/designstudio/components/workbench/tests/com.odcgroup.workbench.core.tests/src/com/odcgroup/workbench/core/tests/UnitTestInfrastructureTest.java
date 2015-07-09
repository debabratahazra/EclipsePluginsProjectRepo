package com.odcgroup.workbench.core.tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

/**
 * @author yan
 */
public class UnitTestInfrastructureTest extends AbstractDSUnitTest {

	private static final String DOMAIN_MODEL = "domain/ds3168/DS-3168.domain";
	private static final String MODULE_MODEL = "module/Default/module/DS-3168-BigModule.module";

	IOfsProject project;
	
	@Before
	public void setUp() throws Exception {
		project = createModelsProject();
	}
	
	@After
	public void tearDown() throws Exception {
		deleteModelsProjects();
    }
	
	@Test
	public void testCreateModelsProject() {
		Assert.assertTrue("project should be opened", getProject().isOpen());
		
		Assert.assertTrue("domain folder should exists", getDomainFolder().exists());
		Assert.assertTrue("fragment folder should exists", getFragmentFolder().exists());
		Assert.assertTrue("module folder should exists", getModuleFolder().exists());
		Assert.assertTrue("default module folder should exists", getDefaultModuleFolder().exists());
		Assert.assertTrue("pageflow folder should exists", getPageflowFolder().exists());
		Assert.assertTrue("page folder should exists", getPageFolder().exists());
		Assert.assertTrue("default page folder should exists", getDefaultPageFolder().exists());
		Assert.assertTrue("workflow folder should exists", getWorkflowFolder().exists());
	}
	
	@Test
	public void testCreateModelsNamedProject() {
		IOfsProject namedProject = createNamedModelsProject("my-project-models");
		Assert.assertTrue("project should be opened", namedProject.getProject().isOpen());
		
		Assert.assertTrue("domain folder should exists", getDomainFolder(namedProject).exists());
		Assert.assertTrue("fragment folder should exists", getFragmentFolder(namedProject).exists());
		Assert.assertTrue("module folder should exists", getModuleFolder(namedProject).exists());
		Assert.assertTrue("default module folder should exists", getDefaultModuleFolder(namedProject).exists());
		Assert.assertTrue("pageflow folder should exists", getPageflowFolder(namedProject).exists());
		Assert.assertTrue("page folder should exists", getPageFolder(namedProject).exists());
		Assert.assertTrue("default page folder should exists", getDefaultPageFolder(namedProject).exists());
		Assert.assertTrue("workflow folder should exists", getWorkflowFolder(namedProject).exists());
	}
	
	@Test
	public void testGetProject() {
		final String modelProjectName = "testGetProject-models";
		IOfsProject namedProject = createNamedModelsProject(modelProjectName);
		
		Assert.assertEquals(project.getProject(), getProject());
		Assert.assertEquals(namedProject.getProject(), getProject(modelProjectName));
		Assert.assertEquals(project, getOfsProject());
		Assert.assertEquals(namedProject, getOfsProject(modelProjectName));
	}
	
	@Test
	public void testCopyResourceInModelsProject() {
		copyResourceInModelsProject(DOMAIN_MODEL, MODULE_MODEL);
	}
    
}
