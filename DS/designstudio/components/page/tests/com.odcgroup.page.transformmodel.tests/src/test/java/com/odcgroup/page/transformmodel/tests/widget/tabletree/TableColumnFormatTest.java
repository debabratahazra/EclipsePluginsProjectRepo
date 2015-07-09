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
 * @author manilkapoor
 *
 */
public class TableColumnFormatTest extends AbstractDSPageTransformationUnitTest {
	
	@Before
	public void setUp() {
		createModelsProject();
	}
	
	@After
	public void tearDown() {
		deleteModelsProjects();
	}
	
	@Test
	public void testDS8583() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException, CoreException, SAXException, ParserConfigurationException {
		copyResourceInModelsProject("module/widget/tabletree/DS8583.module");
		copyResourceInModelsProject("domain/ds8583/DS8583.domain"); 
		assertTransformation(
				URI.createURI("resource:///widget/tabletree/DS8583.module"), 
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/tabletree/ds8583ExpectedResult.xml"),
				"/page[1]/comment()[1]");
	}

}
