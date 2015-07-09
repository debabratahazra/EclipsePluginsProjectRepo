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
 * @author yan,atr
 */
public class TableTreeTest extends AbstractDSPageTransformationUnitTest {

	@Before
	public void setUp() {
		createModelsProject();
	}
	
	@After
	public void tearDown() {
		deleteModelsProjects();
	}
	
	@Test
	public void testDS3374XspGenerationForTableTree() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException, CoreException, SAXException, ParserConfigurationException {
		copyResourceInModelsProject("fragment/widgets/tableTree/DS3774.fragment");
		copyResourceInModelsProject("domain/ds3774/DS3774.domain"); // Otherwise the translation doesn't appear
		assertTransformation(
				URI.createURI("resource:///widgets/tableTree/DS3774.fragment"), 
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/tabletree/ExpectedTableTree.xml"),
				"/vbox[1]/udp[1]/render-list[1]/logic[1]/content[1]/table[1]/toolbar[1]/icon[1]/onevent[1]/submit[1]/param[3]/@name", 
				"/vbox[1]/udp[1]/render-list[1]/logic[1]/content[1]/table[1]/toolbar[1]/icon[1]/onevent[1]/submit[1]/param[4]/@name", 
				"/vbox[1]/udp[1]/render-list[1]/table[1]/toolbar[1]/icon[1]/onevent[1]/submit[1]/param[3]/@name", 
				"/vbox[1]/udp[1]/render-list[1]/table[1]/toolbar[1]/icon[1]/onevent[1]/submit[1]/param[4]/@name");
	}
	
	@Test
	public void testDS3813() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException, CoreException, SAXException, ParserConfigurationException {
		copyResourceInModelsProject("module/Default/module/DS3813.module");
		copyResourceInModelsProject("domain/ds3813/DS3813.domain"); 
		assertTransformation(
				URI.createURI("resource:///Default/module/DS3813.module"), 
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/tabletree/ds3813ExpectedResult.xml"),
				"/page[1]/comment()[1]");
	}
	
	/**
	 * test the view-ref. 
	 */
	@Test
	public void testDS3717()  throws ModelNotFoundException, IOException, InvalidMetamodelVersionException, CoreException, SAXException, ParserConfigurationException {
		copyResourceInModelsProject("module/Default/module/DS3717.module");
		copyResourceInModelsProject("domain/ds3717/DS3717.domain"); 
		assertTransformation(
				URI.createURI("resource:///Default/module/DS3717.module"), 
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/tabletree/ds3717ExpectedResult.xml"),
				"/page[1]/comment()[1]");
	}
	
	/**
	 * 
	 * @throws ModelNotFoundException
	 * @throws IOException
	 * @throws InvalidMetamodelVersionException
	 * @throws CoreException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	@Test
	public void testDS3557() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException, CoreException, SAXException, ParserConfigurationException {
		copyResourceInModelsProject("module/widget/tabletree/DS3557.module");
		copyResourceInModelsProject("domain/ds3557/DS3557.domain"); 
		assertTransformation(
				URI.createURI("resource:///widget/tabletree/DS3557.module"), 
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/tabletree/ds3557ExpectedResult.xml"),
				"/page[1]/comment()[1]");
	}
	
	@Test
	public void testDS4646() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException, CoreException, SAXException, ParserConfigurationException {
		copyResourceInModelsProject("module/widget/tabletree/DS3557.module");
		copyResourceInModelsProject("domain/ds3557/DS3557.domain"); 
		assertTransformation(
				URI.createURI("resource:///widget/tabletree/DS3557.module"), 
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/tabletree/ds3557ExpectedResult.xml"),
				"/page[1]/comment()[1]");
	}
	
	/**
	 * Test the generation of a table including dynamic columns with an aggregation
	 * @throws ModelNotFoundException
	 * @throws IOException
	 * @throws InvalidMetamodelVersionException
	 * @throws CoreException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	@Test
	public void testDS3458DynamicColumnsWithAggregation() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException, CoreException, SAXException, ParserConfigurationException { 
		copyResourceInModelsProject("module/widget/tabletree/DynamicTableWithAggregation.module");
		copyResourceInModelsProject("domain/ds3458/DS3458.domain"); 
		assertTransformation(
				URI.createURI("resource:///widget/tabletree/DynamicTableWithAggregation.module"), 
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/tabletree/DynamicTableWithAggregationExpectedResult.xml"),
				"/page[1]/comment()[1]");
	}

	/**
	 * Test the generation of a table including dynamic columns without an aggregation
	 * @throws ModelNotFoundException
	 * @throws IOException
	 * @throws InvalidMetamodelVersionException
	 * @throws CoreException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	@Test
	public void testDS3458DynamicColumnsWithoutAggregation() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException, CoreException, SAXException, ParserConfigurationException { 
		copyResourceInModelsProject("module/widget/tabletree/DynamicTableWithoutAggregation.module");
		copyResourceInModelsProject("domain/ds3458/DS3458.domain"); 
		assertTransformation(
				URI.createURI("resource:///widget/tabletree/DynamicTableWithoutAggregation.module"), 
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/tabletree/DynamicTableWithoutAggregationExpectedResult.xml"),
				"/page[1]/comment()[1]");
	}
	
	/**
	 * Test the generation of a table including dynamic columns without an aggregation
	 * @throws ModelNotFoundException
	 * @throws IOException
	 * @throws InvalidMetamodelVersionException
	 * @throws CoreException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	@Test
	public void testDS3847TableSorting() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException, CoreException, SAXException, ParserConfigurationException { 
		copyResourceInModelsProject("module/widget/tabletree/TableSorting.module");
		copyResourceInModelsProject("domain/ds3847/DS3847.domain"); 
		assertTransformation(
				URI.createURI("resource:///widget/tabletree/TableSorting.module"), 
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/tabletree/ds3847ExpectedResult.xml"),
				"/page[1]/comment()[1]");
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
	public void testDS4453PlaceHolderNoDisplayWidgets() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException, CoreException, SAXException, ParserConfigurationException { 
		copyResourceInModelsProject("module/Default/module/DS4453.module");
		copyResourceInModelsProject("domain/ds3813/DS3813.domain"); 
		assertTransformation(
				URI.createURI("resource:///Default/module/DS4453.module"), 
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/tabletree/ds4453ExpectedResult.xml"),
				"/page[1]/comment()[1]");
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
	public void testDS4428TableColumnVerticalLayout() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException, CoreException, SAXException, ParserConfigurationException { 
		copyResourceInModelsProject("module/widget/tabletree/DS4428.module");
		copyResourceInModelsProject("domain/ds3458/DS3458.domain"); 
		assertTransformation(
				URI.createURI("resource:///widget/tabletree/DS4428.module"), 
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/tabletree/ds4428ExpectedResult.xml"),
				"/page[1]/comment()[1]");
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
	public void test4416TableTreeCheckboxGen() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException, CoreException, SAXException, ParserConfigurationException { 
		copyResourceInModelsProject("module/widget/tabletree/DS4166.module");
		copyResourceInModelsProject("domain/ds3458/DS3458.domain"); 
		assertTransformation(
				URI.createURI("resource:///widget/tabletree/DS4166.module"), 
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/tabletree/ds4419ExpectedResult.xml"),
				"/page[1]/comment()[1]", 
				"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/render-list[1]/logic[2]/content[1]/table[1]/toolbar[1]/icon[1]/onevent[1]/submit[1]/param[3]/@name",
				"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/render-list[1]/logic[2]/content[1]/table[1]/toolbar[1]/icon[1]/onevent[1]/submit[1]/param[4]/@name",
				"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/render-list[1]/tree[1]/toolbar[1]/icon[1]/onevent[1]/submit[1]/param[3]/@name", 
				"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/render-list[1]/tree[1]/toolbar[1]/icon[1]/onevent[1]/submit[1]/param[4]/@name");
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
	public void test4505SelectAllChecbokGen() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException, CoreException, SAXException, ParserConfigurationException { 
		copyResourceInModelsProject("module/widget/tabletree/DS4505.module");
		copyResourceInModelsProject("domain/ds3458/DS3458.domain"); 
		assertTransformation(
				URI.createURI("resource:///widget/tabletree/DS4505.module"), 
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/tabletree/ds4505ExpectedResult.xml"),
				"/page[1]/comment()[1]");
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
	public void test4708ExtraAggregationGen() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException, CoreException, SAXException, ParserConfigurationException {
		copyResourceInModelsProject("module/widget/tabletree/DS4708.module");
		copyResourceInModelsProject("domain/ds3813/DS3813.domain"); 
		assertTransformation(
				URI.createURI("resource:///widget/tabletree/DS4708.module"), 
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/tabletree/ds4708ExpectedResult.xml"),
				"/page[1]/comment()[1]");
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
	public void test4730RenderCheckBoxGeneration() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException, CoreException, SAXException, ParserConfigurationException {
		copyResourceInModelsProject("module/widget/tabletree/DS4730.module");
		copyResourceInModelsProject("domain/ds4754/DS4754.domain"); 
		assertTransformation(
				URI.createURI("resource:///widget/tabletree/DS4730.module"), 
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/tabletree/ds4730ExpectedResult.xml"),
				"/page[1]/comment()[1]");
	}
	/**
	 * 
	 * @throws ModelNotFoundException
	 * @throws IOException
	 * @throws InvalidMetamodelVersionException
	 * @throws CoreException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	@Test
	public void test2844RenderTableTreeElementWIthShowNumItems() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException, CoreException, SAXException, ParserConfigurationException {
		copyResourceInModelsProject("module/widget/tabletree/DS2844TreeWithShowNumItems.module");
		copyResourceInModelsProject("domain/ds2844/DS2844Domain.domain");
		assertTransformation(
				URI.createURI("resource:///widget/tabletree/DS2844TreeWithShowNumItems.module"), 
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/tabletree/ds2844TreewithNumItemsExpectedResult.xml"),
				"/page[1]/comment()[1]");
	}
	/**
	 * test the rowselector and column selector for table tree.
	 * @throws ModelNotFoundException
	 * @throws IOException
	 * @throws InvalidMetamodelVersionException
	 * @throws CoreException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	@Test
	public void testDS4932RowSelectorAndColumnSelector() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException, CoreException, SAXException, ParserConfigurationException {
	    copyResourceInModelsProject("module/widget/tabletree/DS4932.module");
	    copyResourceInModelsProject("domain/ds3458/DS3458.domain");
	    assertTransformation(
			URI.createURI("resource:///widget/tabletree/DS4932.module"), 
			readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/tabletree/ds4932ExpectedResult.xml"),
			"/page[1]/comment()[1]");
	    
	}
	
	@Test
	public void testDS5299TableGrpFormat()  throws ModelNotFoundException, IOException, InvalidMetamodelVersionException, CoreException, SAXException, ParserConfigurationException{
		copyResourceInModelsProject("module/widget/tabletree/DS5299TableGrpFormat.module");
		copyResourceInModelsProject("domain/ds5365/DS5365.domain"); 
		assertTransformation(
				URI.createURI("resource:///widget/tabletree/DS5299TableGrpFormat.module"), 
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/tabletree/DS5299ExpectedResult.xml"),
				"/page[1]/comment()[1]");
	}
	
	@Test
	public void testDS5339TableColumnWithMultipleItems() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException, CoreException, SAXException, ParserConfigurationException{
		copyResourceInModelsProject("module/widget/tabletree/DS5339.module");
		copyResourceInModelsProject("domain/ds5339/DS5339.domain"); 
		assertTransformation(
				URI.createURI("resource:///widget/tabletree/DS5339.module"), 
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/tabletree/ds5339ExpectedResult.xml"),
				"/page[1]/comment()[1]");

	}
}
