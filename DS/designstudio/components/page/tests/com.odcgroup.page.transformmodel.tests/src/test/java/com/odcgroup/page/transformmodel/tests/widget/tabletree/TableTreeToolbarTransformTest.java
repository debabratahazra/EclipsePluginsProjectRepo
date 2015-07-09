package com.odcgroup.page.transformmodel.tests.widget.tabletree;

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
 * @author phanikumark
 *
 */
public class TableTreeToolbarTransformTest extends AbstractDSPageTransformationUnitTest {

	@Before
	public void setUp() {
		createModelsProject();
	}
	
	@After
	public void tearDown() {
		deleteModelsProjects();
	}

	/**
	 * Test the generation of select/de-select on tree group for Raw type and Hierarchy enabled 
	 */
	@Test
	public void testDS6289TreeToolbarSelectDeslect() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException, CoreException, SAXException, ParserConfigurationException { 
		copyResourceInModelsProject("module/widget/tabletree/DS6289.module");
		copyResourceInModelsProject("domain/ds5365/DS5365.domain"); 
		assertTransformation(
				URI.createURI("resource:///widget/tabletree/DS6289.module"), 
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/tabletree/DS6289ExpectedResult.xml"),
				"/page[1]/comment()[1]");
	}
	
}
