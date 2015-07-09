package com.odcgroup.process.generation.tests.ocs;

import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.emf.common.util.URI;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.process.generation.internal.ocs.GenerationHelper;
import com.odcgroup.process.model.Pool;
import com.odcgroup.process.model.Process;
import com.odcgroup.process.model.UserTask;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

/**
 * @author yan
 */
public class TestPageflowURL extends AbstractDSUnitTest {
	
	private static final String PAGEFLOW = "ds3742/Ds3742.pageflow";
	private static final String WORKFLOW = "ds3742/Ds3742.workflow";
	
	@Before
	public void setUp() {
		createModelsProject();
		copyResourceInModelsProject("pageflow/" + PAGEFLOW);
		copyResourceInModelsProject("workflow/" + WORKFLOW);
	}
	
	@After
	public void tearDown() {
		deleteModelsProjects();
	}

	@Test
	public void testDS3742PageflowUrl() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException {
		IOfsModelResource workflowResource = getOfsProject().getOfsModelResource(URI.createURI("resource:///" + WORKFLOW));
		Process workflow = (Process)workflowResource.getEMFModel().get(0);
		Pool pool = (Pool)workflow.getPools().get(0);
		UserTask task = (UserTask)pool.getTasks().get(0);
		
		Assert.assertEquals("Pageflow url wrong", 
				"/wui/activity/aaa/pageflow/ds3742-pageflow-Ds3742/Ds3742", 
				GenerationHelper.resolveDesignStudioPageflowURIforGeneration(task, "resource:///ds3742/Ds3742.pageflow", new ArrayList<com.odcgroup.process.model.Property>(), true));
	}
}
