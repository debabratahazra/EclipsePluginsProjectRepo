package com.odcgroup.page.transformmodel.tests.widget;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.odcgroup.page.transformmodel.tests.AbstractDSPageTransformationUnitTest;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;

/**
 * @author yan
 */
public class SpacerTest extends AbstractDSPageTransformationUnitTest {

	@Before
	public void setUp() {
		createModelsProject();
		copyResourceInModelsProject("fragment/widgets/spacer/DS3774.fragment");
	}
	
	@After
	public void tearDown() {
		deleteModelsProjects();
	}
	
	@Test
	public void testDS3374XspGenerationForSpacer() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException, CoreException, SAXException, ParserConfigurationException {
		assertTransformation(
				URI.createURI("resource:///widgets/spacer/DS3774.fragment"), 
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/ExpectedSpacer.xml"));
	}

}
