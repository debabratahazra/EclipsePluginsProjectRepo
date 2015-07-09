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
public class DS5377XTooltipTransformationTest extends
		AbstractDSPageTransformationUnitTest {

	private static final String DOMAIN_MODEL = "domain/ds4884/DS4884.domain";
	private static final String FRAG_MODEL = "fragment/ds5377/MyFragment.fragment";
	private static final String MODULE_MODEL = "module/Default/module/DS5377_ExtendedToolTip.module";

	@Before
	public void setUp() {
		createModelsProject();
		copyResourceInModelsProject(DOMAIN_MODEL, MODULE_MODEL, FRAG_MODEL);
	}

	@After
	public void tearDown() {
		deleteModelsProjects();
	}

	@Test
	public void testDS5377ExtendedToolTipTransformerTest()
			throws ModelNotFoundException, IOException,
			InvalidMetamodelVersionException, CoreException, SAXException,
			ParserConfigurationException {

		assertTransformation(
				URI.createURI("resource:///Default/module/DS5377_ExtendedToolTip.module"),
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/DS5377ExpectedResult.xml"),
				"/page[1]/comment()[1]");
	}

}
