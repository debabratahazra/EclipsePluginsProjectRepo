package com.odcgroup.page.transformmodel.tests.widget.tabletree;

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
 * @author ramapriyamn
 *
 */
public class DS4871CheckboxOnTableGrpingTest extends AbstractDSPageTransformationUnitTest {

	@Before
	public void setUp() {
		createModelsProject();
	}
	
	@After
	public void tearDown() {
		deleteModelsProjects();
	}

	/**
	 * Test the generation of checkbox on table group for Raw type and Hierarchy enabled 
	 */
	@Test
	public void testDS4871CheckboxOnGrpingWithRaw() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException, CoreException, SAXException, ParserConfigurationException { 
		copyResourceInModelsProject("module/widget/tabletree/DS4871CheckboxOnGroupingWithRaw.module");
		copyResourceInModelsProject("domain/ds5365/DS5365.domain"); 
		assertTransformation(
				URI.createURI("resource:///widget/tabletree/DS4871CheckboxOnGroupingWithRaw.module"), 
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/tabletree/DS4871CheckboxOnGroupingWithRaw.xml"),
				"/page[1]/comment()[1]");
	}
	
	/**
	 * Test the generation of checkbox on simple table group  
	 */
	@Test
	public void testDS4871CheckboxOnGrpingWithoutRaw() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException, CoreException, SAXException, ParserConfigurationException { 
		copyResourceInModelsProject("module/widget/tabletree/DS4871CheckboxOnGroupingWithoutRaw.module");
		copyResourceInModelsProject("domain/ds3458/DS3458.domain"); 
		assertTransformation(
				URI.createURI("resource:///widget/tabletree/DS4871CheckboxOnGroupingWithoutRaw.module"), 
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/tabletree/DS4871CheckboxOnGroupingWithoutRaw.xml"),
				"/page[1]/comment()[1]", 
				"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/render-list[1]/logic[2]/content[1]/table[1]/toolbar[1]/icon[1]/onevent[1]/submit[1]/param[3]/@name",
				"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/render-list[1]/logic[2]/content[1]/table[1]/toolbar[1]/icon[1]/onevent[1]/submit[1]/param[4]/@name",
				"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/render-list[1]/tree[1]/toolbar[1]/icon[1]/onevent[1]/submit[1]/param[3]/@name", 
				"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/render-list[1]/tree[1]/toolbar[1]/icon[1]/onevent[1]/submit[1]/param[4]/@name");
	}
	
}
