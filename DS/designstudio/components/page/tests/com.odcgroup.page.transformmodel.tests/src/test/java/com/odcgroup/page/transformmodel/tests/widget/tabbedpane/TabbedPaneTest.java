package com.odcgroup.page.transformmodel.tests.widget.tabbedpane;

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
public class TabbedPaneTest extends AbstractDSPageTransformationUnitTest {

	@Before
	public void setUp() {
		createModelsProject();
	}
	
	@After
	public void tearDown() {
		deleteModelsProjects();
	}
	
	@Test
	public void testDS3374XspGenerationForTabbedPane() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException, CoreException, SAXException, ParserConfigurationException {
		copyResourceInModelsProject("fragment/widgets/tabbedPane/DS3774.fragment");
		assertTransformation(
				URI.createURI("resource:///widgets/tabbedPane/DS3774.fragment"), 
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/tabbedpane/ExpectedTabbedPane.xml")
			);
	}
	
	@Test
	public void testDS4328XspGenerationForTabbedPane() throws Exception {
		copyResourceInModelsProject("module/widget/tabbedPane/DS4328.module");
		assertTransformation(
				URI.createURI("resource:///widget/tabbedPane/DS4328.module"), 
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/tabbedpane/DS4328ExpectedResult.xml"),
				"/page[1]/comment()[1]"
			);
	}
	
	/**
	 * Ensure the flag "enable" is correctly generated for tab.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testDS4029_EnableDisableTabGeneration() throws Exception {
		copyResourceInModelsProject("fragment/widgets/tabbedPane/DS4029.fragment");
		assertTransformation(
				URI.createURI("resource:///widgets/tabbedPane/DS4029.fragment"), 
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/" +
						"widget/DS4029ExpectedResult.xml"));
	}

	
	@Test
	public void testXspGenerationForDynamicTabbedPane() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException, CoreException, SAXException, ParserConfigurationException { 
		copyResourceInModelsProject("module/widget/tabbedpane/DS3705.module");
		copyResourceInModelsProject("domain/ds3705/DS3705.domain"); 
		assertTransformation(
				URI.createURI("resource:///widget/tabbedpane/DS3705.module"), 
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/tabbedpane/ExpectedDynamicTabbedPane.xml"),
						"/page[1]/comment()[1]", 
						"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/tab-view[1]/tabbed-pane[1]/tab[1]/onevent[1]/submit[1]/param[3]/@name", 
						"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/tab-view[1]/tabbed-pane[1]/tab[1]/onevent[1]/submit[1]/param[4]/@name", 
						"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/tab-view[1]/tabbed-pane[1]/tab[1]/onevent[1]/submit[1]/param[5]/@name", 
						"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/tab-view[1]/tabbed-pane[1]/tab[2]/onevent[1]/submit[1]/param[3]/@name", 
						"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/tab-view[1]/tabbed-pane[1]/tab[2]/onevent[1]/submit[1]/param[4]/@name", 
						"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/tab-view[1]/tabbed-pane[1]/tab[2]/onevent[1]/submit[1]/param[5]/@name", 
						"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/tab-view[1]/tabbed-pane[1]/tab[2]/onevent[1]/submit[1]/param[6]/@name", 
						"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/tab-view[1]/tabbed-pane[1]/tab[2]/onevent[1]/submit[1]/param[7]/@name", 
						"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/tab-view[1]/tabbed-pane[1]/tab[2]/onevent[1]/submit[1]/param[8]/@name",
						"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/tab-view[1]/tabbed-pane[1]/tab[2]/onevent[1]/submit[1]/param[9]/@name");
	}
	
	/**
	 * DS4021:Ensure the tag <udp:udp> is generated only once around the outermost tabbed pane.
	 * @throws ModelNotFoundException
	 * @throws IOException
	 * @throws InvalidMetamodelVersionException
	 * @throws CoreException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	@Test
	public void testDS4021_NestedDynamicTabbedPane() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException, CoreException, SAXException, ParserConfigurationException {
		copyResourceInModelsProject("module/widget/tabbedpane/DS4021.module", "domain/ds4021/DS4021.domain"); 
		assertTransformation(
				URI.createURI("resource:///widget/tabbedpane/DS4021.module"), 
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/tabbedpane/DS4021ExpectedResult.xml"),
						"/page[1]/comment()[1]",
						"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/tab-view[1]/tabbed-pane[1]/tab[1]/onevent[1]/submit[1]/param[2]/@name", 
						"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/tab-view[1]/tabbed-pane[1]/tab[1]/onevent[1]/submit[1]/param[3]/@name", 
						"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/tab-view[1]/tabbed-pane[1]/tab[1]/onevent[1]/submit[1]/param[4]/@name", 
						"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/tab-view[1]/tab-content[1]/tab-view[1]/tabbed-pane[1]/tab[1]/onevent[1]/submit[1]/param[2]/@name", 
						"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/tab-view[1]/tab-content[1]/tab-view[1]/tabbed-pane[1]/tab[1]/onevent[1]/submit[1]/param[3]/@name", 
						"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/tab-view[1]/tab-content[1]/tab-view[1]/tabbed-pane[1]/tab[1]/onevent[1]/submit[1]/param[4]/@name");
		
	}
	
}
