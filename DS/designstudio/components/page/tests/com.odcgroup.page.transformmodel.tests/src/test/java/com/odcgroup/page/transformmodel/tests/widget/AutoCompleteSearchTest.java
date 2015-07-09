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
 * @author pkk
 *
 */
public class AutoCompleteSearchTest extends AbstractDSPageTransformationUnitTest {

	@Before
	public void setUp() {
		createModelsProject();
	}
	
	@After
	public void tearDown() {
		deleteModelsProjects();
	}
	

	/**
	 * @throws ModelNotFoundException
	 * @throws IOException
	 * @throws InvalidMetamodelVersionException
	 * @throws CoreException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	@Test
	public void testDS4994AutoCompleteUriGeneration() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException, CoreException, SAXException, ParserConfigurationException { 
		copyResourceInModelsProject("fragment/ds4994/DS4994.fragment");
		copyResourceInModelsProject("module/Default/autocompleteoutput/mbb/DS4994Auto.module");
		copyResourceInModelsProject("domain/ds3813/DS3813.domain"); 
		assertTransformation(
				URI.createURI("resource:///ds4994/DS4994.fragment"), 
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/ds4994ExpectedResult.xml"));
	}
	
	
	/**
	 * @throws ModelNotFoundException
	 * @throws IOException
	 * @throws InvalidMetamodelVersionException
	 * @throws CoreException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	@Test
	public void testDS4467AutoCompleteSearchGeneration() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException, CoreException, SAXException, ParserConfigurationException { 
		copyResourceInModelsProject("module/Default/module/DS4467.module");
		copyResourceInModelsProject("module/Default/module/DS4467Auto.module");
		copyResourceInModelsProject("domain/ds3813/DS3813.domain"); 
		assertTransformation(
				URI.createURI("resource:///Default/module/DS4467.module"), 
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/ds4467ExpectedResult.xml"),
				"/page[1]/comment()[1]");
	}

}
