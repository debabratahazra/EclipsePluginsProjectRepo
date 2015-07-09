package com.odcgroup.page.transformmodel.tests.widget;

import org.eclipse.emf.common.util.URI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.page.transformmodel.tests.AbstractDSPageTransformationUnitTest;

/**
 * @author yan
 */
public class ComboBoxTest extends AbstractDSPageTransformationUnitTest {

	@Before
	public void setUp() {
		createModelsProject();
	}
	
	@After
	public void tearDown() {
		deleteModelsProjects();
	}
	
	@Test
	public void testDS3374XspGenerationForComboBox() throws Exception {
		copyResourceInModelsProject("fragment/widgets/comboBox/DS3774.fragment");
		copyResourceInModelsProject("domain/ds3774/DS3774.domain");
		assertTransformation(
				URI.createURI("resource:///widgets/comboBox/DS3774.fragment"), 
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/" +
						"widget/ExpectedComboBox.xml"));
	}

	@Test
	public void testDS4100XspGenerationForComboBox() throws Exception {
		copyResourceInModelsProject("domain/ds4100/DS4100.domain");
		copyResourceInModelsProject("fragment/widgets/comboBox/DS4100.fragment");
		copyResourceInModelsProject("module/Default/module/DS4100.module");
		assertTransformation(
				URI.createURI("resource:///Default/module/DS4100.module"), 
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/DS4100ExpectedResult.xml"),
						"/page[1]/comment()[1]");
	}


}
