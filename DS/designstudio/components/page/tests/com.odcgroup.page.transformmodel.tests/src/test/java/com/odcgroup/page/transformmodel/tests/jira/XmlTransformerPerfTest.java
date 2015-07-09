package com.odcgroup.page.transformmodel.tests.jira;

import org.eclipse.emf.common.util.URI;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.page.model.corporate.CorporateDesignUtils;
import com.odcgroup.page.transformmodel.namespaces.NamespaceFacilityUtils;
import com.odcgroup.page.transformmodel.tests.AbstractDSPageTransformationUnitTest;

/**
 *
 * @author pkk
 *
 */
public class XmlTransformerPerfTest extends AbstractDSPageTransformationUnitTest {

	private static final String DOMAIN_MODEL = "domain/ds3168/DS-3168.domain";
	private static final String MODULE_MODEL = "module/Default/module/DS-3168-BigModule.module";

    @Before
	public void setUp() throws Exception {
    	createModelsProject();
        copyResourceInModelsProject(DOMAIN_MODEL, MODULE_MODEL);
        NamespaceFacilityUtils.stopNamespaceFacility();
		CorporateDesignUtils.uninstallCorporateDesign();
    }

	@After
	public void tearDown() throws Exception {
        deleteModelsProjects();
        NamespaceFacilityUtils.stopNamespaceFacility();
		CorporateDesignUtils.uninstallCorporateDesign();
    }

	@Test
	public void test3700XspGenPerformanceTest() throws Exception {
		long start = System.currentTimeMillis();
		createXmlFromUri(URI.createURI("resource:///Default/module/DS-3168-BigModule.module"));
		long executionTime = System.currentTimeMillis() - start;

		final long MAX_EXECUTION_TIME = 1500;
		Assert.assertTrue("Page designer opened too slowly ["+executionTime+"ms](", executionTime < MAX_EXECUTION_TIME);
	}

}
