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

public class DS5275ModulePrefixIDGenerationTest extends
		AbstractDSPageTransformationUnitTest {

	private static final String MODULE_MODEL1 = "module/Default/module/DS5275_IdGen.module";
	private static final String FRAG_MODEL2 = "fragment/ds5275/DS5275Search.fragment";
	private static final String FRAG_MODEL3 = "fragment/ds5275/DS5275Text.fragment";
	private static final String DOMAIN_MODEL1 = "domain/ds5275/DS5275.domain";
	
	@Before
	public void setUp() {
		createModelsProject();
		copyResourceInModelsProject(DOMAIN_MODEL1, MODULE_MODEL1, FRAG_MODEL2, FRAG_MODEL3);
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
	public void testDS5275ModulePrefixIDGenerationTest()
			throws ModelNotFoundException, IOException,
			InvalidMetamodelVersionException, CoreException, SAXException,
			ParserConfigurationException {
		assertTransformation(
				URI.createURI("resource:///Default/module/DS5275_IdGen.module"),
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/DS5275_IDgenExpected.xml"), "/page[1]/comment()[1]");
	}
}
