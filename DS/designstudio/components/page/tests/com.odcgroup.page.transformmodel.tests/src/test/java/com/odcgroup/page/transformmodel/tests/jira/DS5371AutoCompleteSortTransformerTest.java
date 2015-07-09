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

public class DS5371AutoCompleteSortTransformerTest extends AbstractDSPageTransformationUnitTest {
	
	private static final String DOMAIN_MODEL = "domain/ds4035/DS4035.domain";
	private static final String MODULE_MODEL = "module/widget/autocomplete/AutoComplete.module";

	@Before
	public void setUp() {
		createModelsProject();
		copyResourceInModelsProject(DOMAIN_MODEL, MODULE_MODEL);
	}

	@After
	public void tearDown() {
		deleteModelsProjects();
	}

	@Test
	public void testDS5304SearchFieldMappedAttributeGenerationTest()
			throws ModelNotFoundException, IOException,
			InvalidMetamodelVersionException, CoreException, SAXException,
			ParserConfigurationException {

		assertTransformation(
				URI.createURI("resource:///widget/autocomplete/AutoComplete.module"),
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/DS5371ExpectedResult.xml"),
				"/page[1]/comment()[1]");
	}


}