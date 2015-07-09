package com.odcgroup.page.transformmodel.tests.jira;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;
import org.xml.sax.SAXException;

import com.odcgroup.page.transformmodel.tests.AbstractDSPageTransformationUnitTest;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;

/**
 * TODO: Document me!
 *
 * @author mumesh
 *
 */
public class DS6426XSPGenerationTest extends
AbstractDSPageTransformationUnitTest{

    @Before
	public void setUp() throws Exception {
    	createModelsProject();
    	copyPmsModelsResourceInModelsProject();
    	copyResourceInModelsProject("module/Default/module/DS6426ModulePrefix.module");
    }

	@After
	public void tearDown() throws Exception {
        deleteModelsProjects();
     }

	@Test @Ignore /* because the test takes 23 minutes since xtext upgrade */
	public void testDS6426XSPGenerationTest()
			throws ModelNotFoundException, IOException,
			InvalidMetamodelVersionException, CoreException, SAXException,
			ParserConfigurationException {

		assertTransformation(
				URI.createURI("resource:///Default/module/DS6426ModulePrefix.module"),
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/module/DS6426ExpectedResult.xml"),
				"/page[1]/comment()[1]");
	}
}
