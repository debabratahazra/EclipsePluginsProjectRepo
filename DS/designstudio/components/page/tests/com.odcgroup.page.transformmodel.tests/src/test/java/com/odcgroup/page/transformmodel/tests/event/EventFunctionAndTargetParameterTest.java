package com.odcgroup.page.transformmodel.tests.event;

import org.eclipse.emf.common.util.URI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.page.transformmodel.tests.AbstractDSPageTransformationUnitTest;

/**
 * @author atr
 */
public class EventFunctionAndTargetParameterTest  extends AbstractDSPageTransformationUnitTest {

	@Before
	public void setUp() {
		createModelsProject();
	}
	
	@After
	public void tearDown() {
		deleteModelsProjects();
	}
	
	@Test
	public void testDS5212_XspGenerationOfEventOnChangeCheckNumber() throws Exception {
		copyResourceInModelsProject("module/Default/module/DS5212.module");
		assertTransformation(
				URI.createURI("resource:///Default/module/DS5212.module"), 
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/DS5212ExpectedResult.xml"), 
						"/page[1]/comment()[1]");
	}
	
	
	@Test
	public void testDS4019_XspGenerationOfTargetParameterForButton() throws Exception {
		copyResourceInModelsProject("fragment/widgets/button/DS4019.fragment");
		assertTransformation(
				URI.createURI("resource:///widgets/button/DS4019.fragment"), 
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/" +
						"widget/DS4019ExpectedResult.xml"));
	}

}
