package com.odcgroup.page.transformmodel.tests.jira;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.odcgroup.page.transformmodel.tests.AbstractDSPageTransformationUnitTest;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;

/**
 * EventGnerationOnTabs Test class to test the events generation on tabs
 * @author snn
 *
 */
public class EventGenerationOnTabs extends AbstractDSPageTransformationUnitTest {

	@Before
	public void setUp() {
		createModelsProject();
		
	}

	@After
	public void tearDown() {
		deleteModelsProjects();
	}
	/*
	 * test if the onclick event generation contain widget-group-ref="form" ,only-changed=true.
	 */
	@Test
	public void testDS4964OnCLickEventGenerationOnTab()throws ModelNotFoundException, IOException,
	InvalidMetamodelVersionException, CoreException, SAXException,
	ParserConfigurationException{
		copyResourceInModelsProject("module/widget/tabbedpane/DS4964.module");
       assertTransformation(
					URI.createURI("resource:///widget/tabbedpane/DS4964.module"),
					readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/Ds4964ExpectedResult.xml"),
					"/page[1]/comment()[1]");
		
	}

}
