package com.odcgroup.cdm.generation.tests;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.cdm.generation.CdmOawGeneratorHelper;
import com.odcgroup.cdm.generation.dataset.CdmDatasetBeanGenerator;
import com.odcgroup.mdf.generation.OAWGenerator;
import com.odcgroup.mdf.metamodel.MdfDatasetMappedProperty;
import com.odcgroup.mdf.metamodel.MdfDomain;

/**
 * @author gha
 */
public class CdmGenerationTests {

	private ResourceSet resources;
	private String outputFolder;

	@Before
	public void setUp() throws IOException {
		resources = new ResourceSetImpl();

		URI uri = URI.createURI(CdmGenerationTests.class.getResource("cdm.domain").toString());

		Resource resource = resources.createResource(uri);
		resource.load(Collections.EMPTY_MAP);
		resource = resources.createResource(URI.createURI(CdmGenerationTests.class.getResource("my.domain").toString()));
		resource.load(Collections.EMPTY_MAP);
		outputFolder = "C:/dvpt/temp/generation-tests";
	}

	@Test
	public void testGetCDMName() throws Exception {
		URI uri = URI.createURI(CdmGenerationTests.class.getResource("cdm.domain").toString());

		Resource resource = resources.getResource(uri, true);
		MdfDomain domain = (MdfDomain) resource.getContents().get(0);

		// Test on PhysicalProspect#addressCity
		MdfDatasetMappedProperty datasetProperty = (MdfDatasetMappedProperty) domain.getDataset("PhysicalProspect")
				.getProperty("addressCity");
		Assert.assertEquals("Test CDM name for property \"addressCity\" of dataset \"PhysicalProspect\"", "contactPoint.city",
				CdmOawGeneratorHelper.getCDMName(datasetProperty));

		// Test on PhysicalProspect#clientRelationshipType
		datasetProperty = (MdfDatasetMappedProperty) domain.getDataset("PhysicalProspect").getProperty(
				"clientRelationshipType");
		Assert.assertEquals("Test CDM name for property \"clientRelationshipType\" of dataset \"PhysicalProspect\"", "type",
				CdmOawGeneratorHelper.getCDMName(datasetProperty));
	}

	@Test
	public void testGetSourceBeanAttributeName() throws Exception {
		URI uri = URI.createURI(CdmGenerationTests.class.getResource("cdm.domain").toString());

		Resource resource = resources.getResource(uri, true);
		MdfDomain domain = (MdfDomain) resource.getContents().get(0);

		// Test on PhysicalProspect#addressCity
		MdfDatasetMappedProperty datasetProperty = (MdfDatasetMappedProperty) domain.getDataset("PhysicalProspect")
				.getProperty("addressCity");
		Assert.assertEquals("Test attribute name for property \"addressCity\" of dataset \"PhysicalProspect\"", "city",
				CdmOawGeneratorHelper.getSourceBeanAttributeName(datasetProperty));

		// Test on PhysicalProspect#clientRelationshipType
		datasetProperty = (MdfDatasetMappedProperty) domain.getDataset("PhysicalProspect").getProperty(
				"clientRelationshipType");
		Assert.assertEquals("Test attribute name for property \"clientRelationshipType\" of dataset \"PhysicalProspect\"",
				"clientType", CdmOawGeneratorHelper.getSourceBeanAttributeName(datasetProperty));
	}

	@Test
	public void testGetSourceBeanName() throws Exception {
		URI uri = URI.createURI(CdmGenerationTests.class.getResource("cdm.domain").toString());

		Resource resource = resources.getResource(uri, true);
		MdfDomain domain = (MdfDomain) resource.getContents().get(0);

		// Test on PhysicalProspect#addressCity
		MdfDatasetMappedProperty datasetProperty = (MdfDatasetMappedProperty) domain.getDataset("PhysicalProspect")
				.getProperty("addressCity");
		Assert.assertEquals("Test source bean name for property \"addressCity\" of dataset \"PhysicalProspect\"",
				"postalAddress", CdmOawGeneratorHelper.getSourceBeanName(datasetProperty));

		// Test on PhysicalProspect#clientRelationshipType
		datasetProperty = (MdfDatasetMappedProperty) domain.getDataset("PhysicalProspect").getProperty(
				"clientRelationshipType");
		Assert.assertEquals("Test source bean name for property \"clientRelationshipType\" of dataset \"PhysicalProspect\"",
				"clientRelationship", CdmOawGeneratorHelper.getSourceBeanName(datasetProperty));
	}

	@Test
	public void testIsCustomField() throws Exception {
		URI uri = URI.createURI(CdmGenerationTests.class.getResource("cdm.domain").toString());

		Resource resource = resources.getResource(uri, true);
		MdfDomain domain = (MdfDomain) resource.getContents().get(0);

		// Test on PhysicalProspect#addressCity
		MdfDatasetMappedProperty datasetProperty = (MdfDatasetMappedProperty) domain.getDataset("PhysicalProspect")
				.getProperty("addressCity");
		Assert.assertEquals("Test if for property \"addressCity\" of dataset \"PhysicalProspect\" is a custom field", false,
				CdmOawGeneratorHelper.isCustomField(datasetProperty));

		// Test on PhysicalProspect#clientRelationshipType
		datasetProperty = (MdfDatasetMappedProperty) domain.getDataset("PhysicalProspect").getProperty(
				"clientRelationshipType");
		Assert.assertEquals(
				"Test if for property \"clientRelationshipType\" of dataset \"PhysicalProspect\" is a custom field",
				false, CdmOawGeneratorHelper.isCustomField(datasetProperty));
	}

	@Test
	public void testGetJavaType() throws Exception {
		URI uri = URI.createURI(CdmGenerationTests.class.getResource("cdm.domain").toString());

		Resource resource = resources.getResource(uri, true);
		MdfDomain domain = (MdfDomain) resource.getContents().get(0);

		// Test on PhysicalProspect#clientRelationshipType
		MdfDatasetMappedProperty datasetProperty = (MdfDatasetMappedProperty) domain.getDataset("PhysicalProspect")
				.getProperty("clientRelationshipType");
		Assert.assertEquals("Test java type of \"clientRelationshipType\" of dataset \"PhysicalProspect\"",
				"java.lang.String", CdmOawGeneratorHelper.getJavaType(datasetProperty));

	}

	@Test
	public void testIsMultiValued() throws Exception {
		URI uri = URI.createURI(CdmGenerationTests.class.getResource("my.domain").toString());

		Resource resource = resources.getResource(uri, true);
		MdfDomain domain = (MdfDomain) resource.getContents().get(0);

		// Test on PhysicalProspect#clientRelationshipType
		// By ref many
		MdfDatasetMappedProperty datasetProperty = (MdfDatasetMappedProperty) domain.getDataset("DSParentClass")
				.getProperty("byRefMany");
		Assert.assertNotNull("multiple property", datasetProperty);
		Assert.assertTrue("multiple property is not multi-values", CdmOawGeneratorHelper.isMultiValued(datasetProperty));
		// By ref single		
		datasetProperty = (MdfDatasetMappedProperty) domain.getDataset("DSParentClass").getProperty("byRefSingle");
		Assert.assertNotNull("Single property", datasetProperty);
		Assert.assertFalse("Single property is not multi-values", CdmOawGeneratorHelper.isMultiValued(datasetProperty));
		// By value many singled
		datasetProperty = (MdfDatasetMappedProperty) domain.getDataset("DSParentClass").getProperty("byValueManySingled");
		Assert.assertNotNull("byValueManySingled property", datasetProperty);
		Assert.assertFalse("byValueManySingled property is not multi-values", CdmOawGeneratorHelper.isMultiValued(datasetProperty));		
		// By value many
		datasetProperty = (MdfDatasetMappedProperty) domain.getDataset("DSParentClass").getProperty("byValueMany");
		Assert.assertNotNull("byValueMany property", datasetProperty);
		Assert.assertTrue("byValueMany property is not multi-values", CdmOawGeneratorHelper.isMultiValued(datasetProperty));		
		// By value single
		datasetProperty = (MdfDatasetMappedProperty) domain.getDataset("DSParentClass").getProperty("byValueSingle");
		Assert.assertNotNull("byValueSingle property", datasetProperty);
		Assert.assertFalse("byValueSingle property is not multi-values", CdmOawGeneratorHelper.isMultiValued(datasetProperty));		
	}

	@Test
	public void testCdmDatasetBeanGenerator() throws Exception {
		try {
			runGenerator(new CdmDatasetBeanGenerator());
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	protected void runGenerator(OAWGenerator generator) throws CoreException {
		Set<MdfDomain> contents = new HashSet<MdfDomain>();
		for(Resource res : resources.getResources()) {
			contents.add((MdfDomain) res.getContents().get(0));
		}
		generator.doInternalRun("TestProject", outputFolder, contents);
	}

}
