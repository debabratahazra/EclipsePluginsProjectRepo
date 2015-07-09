package com.odcgroup.page.transformmodel.tests.widget.matrix;

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
 *
 * @author pkk
 *
 */
public class MatrixConditionalWidgetTest extends AbstractDSPageTransformationUnitTest {

	@Before
	public void setUp() {
		createModelsProject();
		copyResourceInModelsProject("module/Default/module/DS3911.module");
		copyResourceInModelsProject("module/Default/module/DS5568.module");
		copyResourceInModelsProject("module/Default/module/DS5666MatrixAggregations.module");
		copyResourceInModelsProject("module/Default/module/DS5666MatrixAggregations1.module");
		copyResourceInModelsProject("module/Default/module/DS5666MatrixAggregations2.module");
		copyResourceInModelsProject("module/Default/module/DS5666MatrixAggregations3.module");
		copyResourceInModelsProject("module/Default/module/DS5666MatrixAggregations4.module");
		copyResourceInModelsProject("domain/ds3714/SimpleData.domain"); // Otherwise the translation doesn't appear
	}
	
	@After
	public void tearDown() {
		deleteModelsProjects();
	}
	
	@Test
	public void testDS3374XspGenerationForMatrix() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException, CoreException, SAXException, ParserConfigurationException {
		assertTransformation(
				URI.createURI("resource:///Default/module/DS3911.module"), 
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/matrix/ExpectedMatrixCondition.xml"),	"/page[1]/comment()[1]");
	}
	
	@Test
	public void testDS4892XspGenerationForComputationOfPercentage() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException, CoreException, SAXException, ParserConfigurationException {
	    copyResourceInModelsProject("module/widget/matrix/ComputePercentageinMatrix.module");	
	    assertTransformation(
				URI.createURI("resource:///widget/matrix/ComputePercentageinMatrix.module"), 
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/matrix/DS4892ComputationOfPercentages.xml"),	"/page[1]/comment()[1]");
	}
	
	@Test
	public void testDS5568XspGenerationForBPERConditional() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException, CoreException, SAXException, ParserConfigurationException {
		assertTransformation(
				URI.createURI("resource:///Default/module/DS5568.module"), 
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/matrix/DS5568BPERConditonal.xml"),	"/page[1]/comment()[1]");
	}
	
	@Test
	public void testDS5666XspGenerationForLastRowCells() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException, CoreException, SAXException, ParserConfigurationException {
		assertTransformation(
				URI.createURI("resource:///Default/module/DS5666MatrixAggregations.module"), 
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/matrix/DS5666AggregationsGen.xml"),	"/page[1]/comment()[1]");
	}
	
	@Test
	public void testDS5666XspGenerationForLastColumnCells() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException, CoreException, SAXException, ParserConfigurationException {
		assertTransformation(
				URI.createURI("resource:///Default/module/DS5666MatrixAggregations1.module"), 
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/matrix/DS5666AggregationsGen1.xml"),	"/page[1]/comment()[1]");
	}
	
	@Test
	public void testDS5666XspGenerationForLastCells() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException, CoreException, SAXException, ParserConfigurationException {
		assertTransformation(
				URI.createURI("resource:///Default/module/DS5666MatrixAggregations2.module"), 
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/matrix/DS5666AggregationsGen2.xml"),	"/page[1]/comment()[1]");
	}
	
	@Test
	public void testDS5666XspGenerationForNoAggregates() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException, CoreException, SAXException, ParserConfigurationException {
		assertTransformation(
				URI.createURI("resource:///Default/module/DS5666MatrixAggregations3.module"), 
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/matrix/DS5666AggregationsGen3.xml"),	"/page[1]/comment()[1]");
	}
	
	@Test
	public void testDS5666XspGenerationForDeletedAggregates() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException, CoreException, SAXException, ParserConfigurationException {
		assertTransformation(
				URI.createURI("resource:///Default/module/DS5666MatrixAggregations4.module"), 
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/matrix/DS5666AggregationsGen4.xml"),	"/page[1]/comment()[1]");
	}
	
}