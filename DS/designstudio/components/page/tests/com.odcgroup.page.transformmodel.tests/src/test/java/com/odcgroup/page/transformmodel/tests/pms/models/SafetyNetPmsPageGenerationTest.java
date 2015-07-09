package com.odcgroup.page.transformmodel.tests.pms.models;

import org.eclipse.emf.common.util.URI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;

import com.odcgroup.page.transformmodel.tests.AbstractDSPageTransformationUnitTest;

public class SafetyNetPmsPageGenerationTest extends AbstractDSPageTransformationUnitTest {

	@Before
	public void setUp() {
		createModelsProject();
		copyPmsModelsResourceInModelsProject();
		waitForAutoBuild();
	}

	@After
	public void tearDown() {
		deleteModelsProjects();
	}
	
	@Test @Ignore // Because the build hung during tearDown
	public void testPmsModelsSafetyNet() throws Exception {
		assertByCategoryAllEdit();
		assertDomainContext();
		assertPortfolioList();
		assertPortfolioAdministration();
		assertByPortfolioD();
		assertInstrumentAccounting();
		assertDurationCurrencyMatrix();
	}
	
	public void assertByCategoryAllEdit() throws Exception {
		assertTransformation(
				URI.createURI("resource:///Default/module/aaa/ordersession/order/detailedlist/ByCategoryAllEdit.module"),
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/pmsmodels/ExpectedByCategoryAllEdit.xml"),
				"/page[1]/comment()[1]");
	}
	
	public void assertDomainContext() throws Exception {
		assertTransformation(
				URI.createURI("resource:///Default/module/aaa/administration/domain/DomainContext.module"),
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/pmsmodels/ExpectedDomainContext.xml"),
				"/page[1]/comment()[1]");	
	}
	
	public void assertPortfolioList() throws Exception {
		assertTransformation(
				URI.createURI("resource:///Default/module/aaa/administration/portfolio/search/PortfolioList.module"),
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/pmsmodels/ExpectedPortfolioList.xml"),
				"/page[1]/comment()[1]");	
	}
	
	public void assertPortfolioAdministration() throws Exception {
		assertTransformation(
				URI.createURI("resource:///Default/module/aaa/administration/portfolio/view/PortfolioAdministration.module"),
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/pmsmodels/ExpectedPortfolioAdministration.xml"),
				"/page[1]/comment()[1]");
	}
	
	public void assertByPortfolioD() throws Exception {
		assertTransformation(
				URI.createURI("resource:///Default/module/aaa/valuation/summary/ByPortfolio/ByPortfolioD.module"),
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/pmsmodels/ExpectedByPortfolioD.xml"),
				"/page[1]/comment()[1]");	
	}
	
	public void assertInstrumentAccounting() throws Exception {
		assertTransformation(
				URI.createURI("resource:///Default/module/aaa/valuation/summary/ByInstrument/InstrumentAccounting.module"),
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/pmsmodels/ExpectedInstrumentAccounting.xml"),
				"/page[1]/comment()[1]");	
	}
	
	public void assertDurationCurrencyMatrix() throws Exception {
		assertTransformation(
				URI.createURI("resource:///Default/module/aaa/valuation/summary/DynamicByDurationCurrency/DurationCurrencyMatrix.module"),
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/pmsmodels/ExpectedDurationCurrencyMatrix.xml"),
				"/page[1]/comment()[1]");	
	}

}
