package com.odcgroup.page.metamodel.util.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.page.metamodel.DataValue;
import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

/**
 * Tests specific to Operator Type Registry
 * 
 * @author pkk
 * 
 */
public class OperatorTypeRegistryTest extends AbstractDSUnitTest {

	private static final String DOMAIN_MODEL = "DS3288.domain";

	IFolder domainFolder;

	@Before
	public void setUp() throws Exception {
		createModelsProject();

		copyResourceInModelsProject("domain/ds3288/DS3288.domain");		
	}

	@After
	public void tearDown() throws Exception {
		deleteModelsProjects();
	}
	
	/**
	 * DS-3288
	 * tests the newly included operators for String type
	 */
	@Test
	public void testNewStringOperators() throws ModelNotFoundException, CoreException, IOException, InvalidMetamodelVersionException {
		
		IOfsModelResource ofsModelResource = getOfsProject().getOfsModelResource(URI.createURI("resource:///ds3288/" + DOMAIN_MODEL));
    	MdfDomain domain = (MdfDomain)ofsModelResource.getEMFModel().get(0);
    	MdfDataset dataset = domain.getDataset("DSFlat");
    	MdfDatasetProperty property = dataset.getProperty("Portfolio");
    	
    	PropertyType pType = MetaModelRegistry.findOperatorPropertyTypeFor(property.getType());
    	Assert.assertNotNull(pType);
    	
    	// check the size of operators for String type
    	List<DataValue> dValues = pType.getDataType().getValues();
    	Assert.assertEquals("Expected number of operators for string type", 12, dValues.size());

		// check the newly included operators for String type
    	List<String> values = new ArrayList<String>();
		for (DataValue dataValue : dValues) {
			values.add(dataValue.getValue());
		}
		Assert.assertTrue("String Operators contain \'CONTAINS\' operator", values.contains("CONTAINS"));
		Assert.assertTrue("String Operators contain \'NOT_CONTAINS\' operator", values.contains("NOT_CONTAINS"));
		Assert.assertTrue("String Operators contain \'BEGINS_WITH\' operator", values.contains("BEGINS_WITH"));
		Assert.assertTrue("String Operators contain \'ENDS_WITH\' operator", values.contains("ENDS_WITH"));
	 }

}
