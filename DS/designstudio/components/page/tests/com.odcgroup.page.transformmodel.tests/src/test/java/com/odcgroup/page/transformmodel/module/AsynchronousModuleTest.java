package com.odcgroup.page.transformmodel.module;

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
 * @author atr
 */
public class AsynchronousModuleTest extends AbstractDSPageTransformationUnitTest {

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
	public void testPageLoadsAsynchronousModule() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException, CoreException, SAXException, ParserConfigurationException { 
		copyResourceInModelsProject("page/Default/activity/aaa/DS5693.page",
				"module/Default/module/aaa/DS5693_1.module", "module/Default/module/aaa/DS5693_2.module",
				"module/Default/module/aaa/DS5693_3.module");
		assertTransformation(
				URI.createURI("resource:///Default/activity/aaa/DS5693.page"), 
				readFileFromClasspath(
						"/com/odcgroup/page/transformmodel/tests/module/PageLoadsAsynchronousModuleExpectedResult.xml"),
						"/page[1]/comment()[1]", 
						"/page[1]/activity[1]/modelref[1]/@id",
						"/page[1]/activity[1]/modelref[1]/modelitem[4]/@value",
						"/page[1]/activity[1]/modelref[1]/modelitem[5]/@value",
						"/page[1]/activity[1]/modelref[1]/modelitem[6]/@value"
						);
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
	public void testModuleLoadsAsynchronousModule() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException, CoreException, SAXException, ParserConfigurationException { 
		copyResourceInModelsProject("module/Default/module/aaa/DS5693_1.module");
		copyResourceInModelsProject("module/Default/module/aaa/DS5693_2.module");
		copyResourceInModelsProject("module/Default/module/aaa/DS5693_3.module");
		assertTransformation(
				URI.createURI("resource:///Default/module/aaa/DS5693_1.module"), 
				readFileFromClasspath(
						"/com/odcgroup/page/transformmodel/tests/module/ModuleLoadsAsynchronousModuleExpectedResult.xml"),
						"/page[1]/comment()[1]");
	}

}
