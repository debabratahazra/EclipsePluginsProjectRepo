package com.odcgroup.page.transformmodel.tests.widget;

import org.eclipse.emf.common.util.URI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.page.transformmodel.tests.AbstractDSPageTransformationUnitTest;

/**
 * @author yan
 */
public class FilterCriteriaTest extends AbstractDSPageTransformationUnitTest {

	@Before
	public void setUp() {
		createModelsProject();
		copyResourceInModelsProject("module/widget/filterCriteria/DS3774.module");
	}
	
	@After
	public void tearDown() {
		deleteModelsProjects();
	}
	
	@Test
	public void testDS3374XspGenerationForFilterCriteria() throws Exception {
		assertTransformation(
				URI.createURI("resource:///widget/filterCriteria/DS3774.module"), 
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/" +
						"widget/ExpectedFilterCriteria.xml"),
				"/page[1]/comment()[1]");
	}

}
