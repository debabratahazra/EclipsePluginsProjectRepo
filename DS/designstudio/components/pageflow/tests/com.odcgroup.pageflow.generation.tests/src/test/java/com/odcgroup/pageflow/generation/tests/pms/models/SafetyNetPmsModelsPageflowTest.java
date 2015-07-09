package com.odcgroup.pageflow.generation.tests.pms.models;

import java.io.IOException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.odcgroup.pageflow.generation.tests.AbstractPageflowGenerationTest;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;

public class SafetyNetPmsModelsPageflowTest extends AbstractPageflowGenerationTest {

	@Before
	public void setUp() {
		createModelsProject();
		copyPmsModelsResourceInModelsProject();
	}

	@After
	public void tearDown() {
		deleteModelsProjects();
	}
	
	@Test
	public void testPmsModelsSafetyNet() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException, CoreException, SAXException {
		assertStrategyLinkDetails();
		assertStrategyAdministration();
		assertPortfolioAdministration();
	}

	private void assertStrategyLinkDetails() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException, CoreException, SAXException {
		assertTransformation(
				URI.createURI("resource:///administration/strategylink/StrategyLinkDetails.pageflow"),
				readFileFromClasspath("/com/odcgroup/pageflow/generation/tests/ExpectedStrategyLinkDetails.xml"),
				"/config[1]/comment()[1]");
	}

	private void assertStrategyAdministration() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException, CoreException, SAXException {
		assertTransformation(
				URI.createURI("resource:///administration/strategy/StrategyAdministration.pageflow"),
				readFileFromClasspath("/com/odcgroup/pageflow/generation/tests/ExpectedStrategyAdministration.xml"),
				"/config[1]/comment()[1]");
	}

	private void assertPortfolioAdministration() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException, CoreException, SAXException {
		assertTransformation(
				URI.createURI("resource:///administration/portfolio/PortfolioAdministration.pageflow"),
				readFileFromClasspath("/com/odcgroup/pageflow/generation/tests/ExpectedPortfolioAdministration.xml"),
				"/config[1]/comment()[1]");
	}

}
