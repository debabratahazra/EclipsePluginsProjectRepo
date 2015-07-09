package com.odcgroup.page.transformmodel.tests.event;

import org.eclipse.emf.common.util.URI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.page.transformmodel.tests.AbstractDSPageTransformationUnitTest;

/**
 * @author atr
 */
public class PullModuleTest extends AbstractDSPageTransformationUnitTest {

	@Before
	public void setUp() {
		createModelsProject();
	}
	
	@After
	public void tearDown() {
		deleteModelsProjects();
	}
	
	@Test
	public void testDS4027_PullModuleXspGenerationForButton() throws Exception {
		copyResourceInModelsProject("fragment/widgets/button/DS4027.fragment");
		assertTransformation(
				URI.createURI("resource:///widgets/button/DS4027.fragment"), 
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/" +
						"widget/DS4027ExpectedResult.xml"));
	}
}
