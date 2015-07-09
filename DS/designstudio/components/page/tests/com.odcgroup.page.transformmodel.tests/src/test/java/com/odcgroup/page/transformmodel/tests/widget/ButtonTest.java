package com.odcgroup.page.transformmodel.tests.widget;

import org.eclipse.emf.common.util.URI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.page.transformmodel.tests.AbstractDSPageTransformationUnitTest;

/**
 * @author yan
 */
public class ButtonTest extends AbstractDSPageTransformationUnitTest {

	@Before
	public void setUp() {
		createModelsProject();
		copyResourceInModelsProject("fragment/widgets/button/DS3774.fragment");
	}
	
	@After
	public void tearDown() {
		deleteModelsProjects();
	}
	
	@Test
	public void testDS3374XspGenerationForButton() throws Exception {
		assertTransformation(
				URI.createURI("resource:///widgets/button/DS3774.fragment"), 
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/" +
						"widget/ExpectedButton.xml"));
	}
}
