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
 * @author phanikumark
 *
 */
public class DS4931RadioButtonTransformationTest extends
		AbstractDSPageTransformationUnitTest {

	private static final String DOMAIN_MODEL = "domain/ds4405/DS4405.domain";
	private static final String FRAG_MODEL = "fragment/ds4931/DS4931Radio.fragment";
	private static final String MODULE_MODEL = "module/Default/module/DS4931.module";

	@Before
	public void setUp() {
		createModelsProject();
		copyResourceInModelsProject(DOMAIN_MODEL, MODULE_MODEL, FRAG_MODEL);
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
	public void testDS4925IncludedFragmentIDGenerationTest()
			throws ModelNotFoundException, IOException,
			InvalidMetamodelVersionException, CoreException, SAXException,
			ParserConfigurationException {

		assertTransformation(
				URI.createURI("resource:///Default/module/DS4931.module"),
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/DS4931ExpectedResult.xml"),
				"/page[1]/comment()[1]");
	}

}
