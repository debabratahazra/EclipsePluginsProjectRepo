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
 */
public class TableTreePaginationTest extends AbstractDSPageTransformationUnitTest {

	@Before
	public void setUp() {
		createModelsProject();
	}
	
	@After
	public void tearDown() {
		deleteModelsProjects();
	}
	

	@Test
	public void testDS5132() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException, CoreException, SAXException, ParserConfigurationException {
		copyResourceInModelsProject("module/Default/module/DS5132.module");
		copyResourceInModelsProject("domain/ds5132/DS5132Domain.domain"); 
		assertTransformation(
				URI.createURI("resource:///Default/module/DS5132.module"), 
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/tabletree/ds5132ExpectedResult.xml"),
				"/page[1]/comment()[1]");
	}
	
}
