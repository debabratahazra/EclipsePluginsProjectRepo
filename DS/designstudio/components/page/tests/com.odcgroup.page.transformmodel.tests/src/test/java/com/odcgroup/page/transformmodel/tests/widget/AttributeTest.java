 package com.odcgroup.page.transformmodel.tests.widget;

import org.eclipse.emf.common.util.URI;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.page.model.corporate.CorporateDesignUtils;
import com.odcgroup.page.transformmodel.namespaces.NamespaceFacilityUtils;
import com.odcgroup.page.transformmodel.tests.AbstractDSPageTransformationUnitTest;

/**
 * @author yan
 */
public class AttributeTest extends AbstractDSPageTransformationUnitTest {

	private static final String DS3774_FRAGMENT = "resource:///widgets/attribute/DS3774.fragment";

	@Before
	public void setUp() {
		createModelsProject();
		copyResourceInModelsProject("fragment/widgets/attribute/DS3774.fragment");
	}

	@After
	public void tearDown() {
		deleteModelsProjects();

		/**FIXME Required for multiple test to pass, see DS-4155 for details*/
		disposeOfCorporateDesignfacility();
	}

	/**
	 * It is necessary to dispose of the corporate design registry to enable the test to pass
	 * possible bug see {@link http://rd.oams.com/browse/DS-4155} for more details
	 */
	private void disposeOfCorporateDesignfacility() {
		NamespaceFacilityUtils.stopNamespaceFacility();
		CorporateDesignUtils.uninstallCorporateDesign();
	}

	@Test
	public void testDS3374XspGenerationForAttribute() throws Exception {
		assertTransformation(
				URI.createURI("resource:///widgets/attribute/DS3774.fragment"),
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/ExpectedAttribute.xml"),
				"/vbox[1]/hbox[1]/@id");
	}

	/**
	 * Example test using new helper method executeXpathOnXML
	 *
	 * @throws Exception
	 */
	@Test
	public void testDS3848AddSupportForQueryingTheGeneratedXsp() throws Exception {
		String ds3774Fragment1 = createXmlFromUri(URI.createURI(DS3774_FRAGMENT));
		String ds3774Fragment2 = createXmlFromUri(URI.createURI(DS3774_FRAGMENT));

		String xpath = "/xgui:vbox[1]/xgui:hbox[1]/@id";

		String idFromFirstGeneratedDoc = executeXpathOnXML(xpath, ds3774Fragment1);
		String idFromSecondGeneratedDoc = executeXpathOnXML(xpath, ds3774Fragment2);
	
		Assert.assertTrue("XML should be the same. Id's are not generated for Boxes", ds3774Fragment1.equals(ds3774Fragment2));
		Assert.assertTrue((idFromFirstGeneratedDoc == "" && idFromSecondGeneratedDoc == ""));
		
		
	}
}
