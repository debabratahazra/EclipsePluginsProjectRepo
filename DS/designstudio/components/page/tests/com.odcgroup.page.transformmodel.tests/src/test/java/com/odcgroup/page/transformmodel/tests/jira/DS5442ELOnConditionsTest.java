package com.odcgroup.page.transformmodel.tests.jira;

import org.eclipse.emf.common.util.URI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;

import com.odcgroup.page.transformmodel.tests.AbstractDSPageTransformationUnitTest;

/**
 * EL Test. This seems to be the most useful EL test of all, because it covers
 * expressions that failed during the last major upgrade (when others passed).
 * 
 * Whenever we next make major/any changes to Vorburger ELang, Worbench EL,
 * ConditionUtils, PageDSELCompiler & friends, this test MUST pass for non-regression.
 * 
 * @see http://rd.oams.com/browse/DS-7397
 * @see http://rd.oams.com/browse/DS-6553
 */
public class DS5442ELOnConditionsTest extends AbstractDSPageTransformationUnitTest {

	private static final String DOMAIN_MODEL1 = "domain/ds5442/DS-5442.domain";
	private static final String ENT_DOM_MODEL = "domain/aaa/entities/Entities.domain";
	private static final String BT_DOM_MODEL = "domain/aaa/entities/BusinessTypes.domain";
	private static final String MODULE_MODEL1 = "module/Default/module/DS5442_ConditionEL.module";
	
	private static final String DOMAIN_MODEL2 = "domain/ds5734/DS-5734_extoperation.domain";
	private static final String MODULE_MODEL2 = "module/Default/module/DS5734_MMDepositIdentifierCreate.module";
	
	@Before
	public void setUp() {
		createModelsProject();
		copyResourceInModelsProject(BT_DOM_MODEL, ENT_DOM_MODEL, DOMAIN_MODEL1, MODULE_MODEL1, DOMAIN_MODEL2, MODULE_MODEL2);
	}

	@After
	public void tearDown() {
		deleteModelsProjects();
	}

	@Test @Ignore /* because the test takes 13 minutes since xtext upgrade */
	public void testDS5442ELExpressionsOnConditionsTest() throws Exception {
		// This test requires the maven resolution to be done
		resolveMavenDependenciesAndWaitForAutoBuild();
		
		assertTransformation(
				URI.createURI("resource:///Default/module/DS5442_ConditionEL.module"),
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/DS5442ExpectedResult.xml"),
				"/page[1]/comment()[1]");
	}

	@Test @Ignore /* because the test takes 13 minutes since xtext upgrade */
	public void testDS5734ELExpressionsOnConditionsSAXExceptionTest() throws Exception {
		createXmlFromUri(URI.createURI("resource:///Default/module/DS5734_MMDepositIdentifierCreate.module"));
	}

}
