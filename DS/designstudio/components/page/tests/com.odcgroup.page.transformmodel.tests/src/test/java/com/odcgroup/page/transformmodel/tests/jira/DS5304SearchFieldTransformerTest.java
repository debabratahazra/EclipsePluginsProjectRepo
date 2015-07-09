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

public class DS5304SearchFieldTransformerTest extends AbstractDSPageTransformationUnitTest {
	
	private static final String DOMAIN_MODEL = "domain/ds3774/DS3774.domain";
	private static final String FRAG_MODEL = "fragment/widgets/searchField/DS5304.fragment";
	private static final String FRAG_MODEL1 = "fragment/widgets/searchField/DS5304_Container.fragment";
	private static final String MODULE_MODEL = "module/Default/module/DS5304.module";

	@Before
	public void setUp() {
		createModelsProject();
		copyResourceInModelsProject(DOMAIN_MODEL, MODULE_MODEL, FRAG_MODEL, FRAG_MODEL1);
	}

	@After
	public void tearDown() {
		deleteModelsProjects();
	}

	/**
	 * @throws ModelNotFoundException
	 * @throws IOException
	 * @throws InvalidMetamodelVersionException
	 * @throws CoreException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	@Test
	public void testDS5304SearchFieldMappedAttributeGenerationTest()
			throws ModelNotFoundException, IOException,
			InvalidMetamodelVersionException, CoreException, SAXException,
			ParserConfigurationException {

		assertTransformation(
				URI.createURI("resource:///widgets/searchField/DS5304_Container.fragment"),
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/DS5304ExpectedResult.xml"));
	}


}
