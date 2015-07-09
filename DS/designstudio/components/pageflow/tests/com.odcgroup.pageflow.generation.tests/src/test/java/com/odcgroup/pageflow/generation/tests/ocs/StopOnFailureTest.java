package com.odcgroup.pageflow.generation.tests.ocs;

import java.io.IOException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.odcgroup.pageflow.generation.tests.AbstractPageflowGenerationTest;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;

/**
 * JIRA DS-7899
 */
public class StopOnFailureTest extends AbstractPageflowGenerationTest {
	
	private static final String PAGEFLOW = "ds7899/DS7899.pageflow";


	@Before
	public void setUp() {
		createModelsProject();
		copyResourceInModelsProject("pageflow/" + PAGEFLOW);
	}

	@After
	public void tearDown() {
		deleteModelsProjects();
	}
	
	@Test
	public void testPmsModelsSafetyNet() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException, CoreException, SAXException {
		assertTransformation(
				URI.createURI("resource:///" + PAGEFLOW),
				readFileFromClasspath("/com/odcgroup/pageflow/generation/tests/ds7899-pageflow-DS7899.xml"),
				"/config[1]/comment()[1]");
	}

}
