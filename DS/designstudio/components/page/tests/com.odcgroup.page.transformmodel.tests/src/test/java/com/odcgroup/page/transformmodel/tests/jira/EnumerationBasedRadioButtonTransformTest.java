package com.odcgroup.page.transformmodel.tests.jira;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.runtime.CoreException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.odcgroup.page.transformmodel.tests.AbstractDSPageTransformationUnitTest;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;

/**
 * @author pkk
 * 
 */
public class EnumerationBasedRadioButtonTransformTest extends
		AbstractDSPageTransformationUnitTest {
	
	private static final String DOMAIN_MODEL = "domain/ds3561/DS3561.domain";
	private static final String MODULE_MODEL = "module/Default/module/DS3561.module";
	private static final String DOMAIN_MODEL_DS5705 = "domain/ds5705/DS5705.domain";
	private static final String MODULE_MODEL_DS5705 = "module/Default/module/DS5705.module";
	@Before
	public void setUp() {
		createModelsProject();
        copyResourceInModelsProject(DOMAIN_MODEL, MODULE_MODEL);
        copyResourceInModelsProject(DOMAIN_MODEL_DS5705, MODULE_MODEL_DS5705);
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
	public void testDS3561EnumerationBasedRatioButtonTransformation()
			throws ModelNotFoundException, IOException,
			InvalidMetamodelVersionException, CoreException, SAXException,
			ParserConfigurationException {
		assertTransformation(
				getResourceURI("/Default/module/DS3561.module"),
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/ExpectedEnumRadioButton.xml"), "/page[1]/comment()[1]");
	}

	@Test
	public void testDS5705EnumerationBasedCalcAttrRatioButtonTransformation()
			throws ModelNotFoundException, IOException,
			InvalidMetamodelVersionException, CoreException, SAXException,
			ParserConfigurationException {
		assertTransformation(
				getResourceURI("/Default/module/DS5705.module"),
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/DS5705ExpectedCalcAttrEnum.xml"), "/page[1]/comment()[1]");
	}
}
