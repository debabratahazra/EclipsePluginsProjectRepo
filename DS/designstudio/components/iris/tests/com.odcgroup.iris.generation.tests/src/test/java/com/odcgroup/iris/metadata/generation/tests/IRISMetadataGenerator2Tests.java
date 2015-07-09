package com.odcgroup.iris.metadata.generation.tests;

import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.google.inject.Inject;
import com.odcgroup.iris.generation.tests.IRISMetadataInjectorProvider;
import com.odcgroup.iris.generator.IRISMetadataGenerator2;
import com.odcgroup.workbench.core.tests.util.XMLTestsUtils;
import com.odcgroup.workbench.generation.tests.ng.GeneratorTestHelper;

@RunWith(XtextRunner.class)
@InjectWith(IRISMetadataInjectorProvider.class)
public class IRISMetadataGenerator2Tests {
	@Inject IRISMetadataGenerator2 generator;
	@Inject GeneratorTestHelper helper;
	
	private static String VERSION_MODEL = "SOME_APPLICATION,DS5686.version";
    private static String ENQUIRY_MODEL = "ds5625.enquiry";

    // Customer joins to Sector (ST_Config.domain)
	private static String JOIN_CUSTOMER_MODEL = "ST_Customer.domain";
	private static String JOIN_SECTOR_MODEL = "ST_Config.domain";
	private static String SOLR_MODEL = "Search.domain";

	// CustomerInput (CUSTOMER,INPUT.version) joins to Sector (ST_Config.domain)
	private static String JOIN_VERSION_CUSTOMER_MODEL = "CUSTOMER,INPUT.version";
	
	@Test
	public void testIRISMetadataGeneration() throws Exception {
		helper.loader.getResource(helper.getURI(JOIN_SECTOR_MODEL, getClass()));
		helper.loader.getResource(helper.getURI("ST_Something.domain", getClass()));
		helper.loader.getResource(helper.getURI(ENQUIRY_MODEL, getClass()));
		helper.loader.getResource(helper.getURI("SomeDomain.domain", getClass()));
		URI input = helper.getURI(VERSION_MODEL, getClass());
		generator.doGenerate(input, helper.loader, helper.fsa);
		Map<String, CharSequence> genFiles = helper.fsa.getTextFiles();
		
		CharSequence genXML = genFiles.get("DEFAULT_OUTPUTmetadata-verSomeApplication_Ds5686.xml");
		assertNotNull(genXML);
		String expectedXml = Resources.toString(Resources.getResource(getClass(), "metadata-verSomeApplication_Ds5686.xml"), Charsets.UTF_8);
		XMLTestsUtils.assertXml("XML content not matched.", expectedXml, genXML.toString(), new String());
		
		String genProperties = genFiles.get("DEFAULT_OUTPUTT24-verSomeApplication_Ds5686.properties").toString();
		assertNotNull(genProperties);
		Properties properties = readPropertiesFile(genProperties);
		Assert.assertFalse(properties.isEmpty());
		Assert.assertTrue(properties.containsValue("name: SOME.APPLICATION,DS5686"));
		Assert.assertTrue(properties.containsValue("name: TRACER.DAYS|entity: SOME.APPLICATION,DS5686"));
	}

	/**
	 * Tests the IRISMetadataMapper.getAppByName() method. Expected behaviour is as follows:<ul>
	 * <li>If required MdfClass exists in the collection then getAppByName() should return it</li>
	 * <li>If required MdfClass does not exist in the collection then getAppByName should return null</li></ul>
	 * Ideally it would be a unit test, but getAppByName() is private so the test would have to be an indirect integration test
	 * The test checks the multiplicity of the generated resources which depends on finding the underlying application or not.
	 * @throws Exception
	 */
    @Test
    public void testGetAppByName() throws Exception {
    	helper.loader.getResource(helper.getURI(JOIN_CUSTOMER_MODEL, getClass()));
    	URI input = helper.getURI("ds7154a.enquiry", getClass());	// Take ds7154a.enquiry file to generate
		generator.doGenerate(input, helper.loader, helper.fsa);
		Map<String, CharSequence> genFiles = helper.fsa.getTextFiles();
		CharSequence genXML = genFiles.get("DEFAULT_OUTPUTmetadata-enqDs7154a.xml");
		assertNotNull(genXML);
		String expectedXml = Resources.toString(Resources.getResource(getClass(), "metadata-enqDs7154a.xml"), Charsets.UTF_8);
		XMLTestsUtils.assertXml("XML content not matched.", expectedXml, genXML.toString(), new String());
		
		String genProperties = genFiles.get("DEFAULT_OUTPUTT24-enqDs7154a.properties").toString();
		assertNotNull(genProperties);
		Properties properties = readPropertiesFile(genProperties);
		Assert.assertFalse(properties.isEmpty());
		Assert.assertTrue(properties.containsValue("name: Cus.No|entity: ds7154a|selectionField: @ID"));
		Assert.assertTrue(properties.containsValue("name: Short.Name|entity: ds7154a|selectionField: SHORT.NAME"));
		
		input = helper.getURI("ds7154b.enquiry", getClass());	// Take ds7154b.enquiry file to generate
		generator.doGenerate(input, helper.loader, helper.fsa);
		genFiles = helper.fsa.getTextFiles();
		genXML = genFiles.get("DEFAULT_OUTPUTmetadata-enqDs7154b.xml");
		assertNotNull(genXML);
		expectedXml = Resources.toString(Resources.getResource(getClass(), "metadata-enqDs7154b.xml"), Charsets.UTF_8);
		XMLTestsUtils.assertXml("XML content not matched.", expectedXml, genXML.toString(), new String());
		
		genProperties = genFiles.get("DEFAULT_OUTPUTT24-enqDs7154b.properties").toString();
		assertNotNull(genProperties);
		properties = readPropertiesFile(genProperties);
		Assert.assertFalse(properties.isEmpty());
		Assert.assertTrue(properties.containsValue("name: Cus.No|entity: ds7154b|selectionField: @ID"));
		Assert.assertTrue(properties.containsValue("name: Short.Name|entity: ds7154b|selectionField: SHORT.NAME"));
    }

	@Test 
	public void testT24PropertiesJoinedToGeneration() throws Exception {
		helper.loader.getResource(helper.getURI(JOIN_CUSTOMER_MODEL, getClass()));
		URI input = helper.getURI(JOIN_SECTOR_MODEL, getClass());
		generator.doGenerate(input, helper.loader, helper.fsa);
		Map<String, CharSequence> genFiles = helper.fsa.getTextFiles();
		CharSequence genXML = genFiles.get("DEFAULT_OUTPUTmetadata-enqlistRiskClass.xml");
		assertNotNull(genXML);
		String expectedXml = Resources.toString(Resources.getResource(getClass(), "metadata-enqlistRiskClass.xml"), Charsets.UTF_8);
		XMLTestsUtils.assertXml("XML content not matched.", expectedXml, genXML.toString(), new String());
		
		String genProperties = genFiles.get("DEFAULT_OUTPUTT24-AcAccountSweepError.properties").toString();
		assertNotNull(genProperties);
		Properties properties = readPropertiesFile(genProperties);
		Assert.assertFalse(properties.isEmpty());
		Assert.assertTrue(properties.containsValue("name: SWP.ERR.REF|entity: AC.ACCOUNT.SWEEP.ERROR|joinedTo: AC.ACCOUNT.SWEEP.ERROR"));
		Assert.assertTrue(properties.containsValue("name: SWEEP.TYPE|entity: AC.ACCOUNT.SWEEP.ERROR|joinedTo: AC.SWEEP.TYPE"));
	}		

	@Test
	public void testT24PropertiesVersionJoinedToGeneration() throws Exception {
		helper.loader.getResource(helper.getURI(JOIN_SECTOR_MODEL, getClass()));
		helper.loader.getResource(helper.getURI(JOIN_CUSTOMER_MODEL, getClass()));
		URI input = helper.getURI(JOIN_VERSION_CUSTOMER_MODEL, getClass());
		
		generator.doGenerate(input, helper.loader, helper.fsa);
		Map<String, CharSequence> genFiles = helper.fsa.getTextFiles();
		CharSequence genXML = genFiles.get("DEFAULT_OUTPUTmetadata-verCustomer_Input.xml");
		assertNotNull(genXML);
		String expectedXml = Resources.toString(Resources.getResource(getClass(), "metadata-verCustomer_Input.xml"), Charsets.UTF_8);
		XMLTestsUtils.assertXml("XML content not matched.", expectedXml, genXML.toString(), new String());
		
		String genProperties = genFiles.get("DEFAULT_OUTPUTT24-verCustomer_Input.properties").toString();
		assertNotNull(genProperties);
		Properties properties = readPropertiesFile(genProperties);
		Assert.assertFalse(properties.isEmpty());
		Assert.assertTrue(properties.containsValue("name: GIVEN.NAMES|entity: CUSTOMER,INPUT"));
		Assert.assertTrue(properties.containsValue("name: MNEMONIC|entity: CUSTOMER,INPUT"));
		Assert.assertTrue(properties.containsValue("name: CUSTOMER.TYPE|entity: CUSTOMER,INPUT"));
		Assert.assertTrue(properties.containsValue("name: CUSTOMER.RATING|entity: CUSTOMER,INPUT"));
	}		
	
	@Test(expected=AssertionError.class)
	public void testIRISMetadataGenerationFailure() throws Exception {
		URI input = helper.getURI("ds6367.version", getClass());
		generator.doGenerate(input, helper.loader, helper.fsa);
	}		

	@Test
	public void testIRISSolrMetadataGeneration() throws Exception {
		// Add Domains and Version to project
		URI input = helper.getURI(SOLR_MODEL, getClass());
		
		generator.doGenerate(input, helper.loader, helper.fsa);
		Map<String, CharSequence> genFiles = helper.fsa.getTextFiles();
		CharSequence genXML = genFiles.get("DEFAULT_OUTPUTmetadata-Search.xml");
		assertNotNull(genXML);
		String expectedXml = Resources.toString(Resources.getResource(getClass(), "metadata-Search.xml"), Charsets.UTF_8);
		XMLTestsUtils.assertXml("XML content not matched.", expectedXml, genXML.toString(), new String());
		
		String genProperties = genFiles.get("DEFAULT_OUTPUTT24-enqlistSearch.properties").toString();
		assertNotNull(genProperties);
		Properties properties = readPropertiesFile(genProperties);
		Assert.assertFalse(properties.isEmpty());
		Assert.assertTrue(properties.containsValue("name: @ID|entity: %Search"));
	}		
	
	@Test
	@Ignore
	public void testIRISSolrClassGenerationSkip() throws Exception {
		// same as testIRISSolrMetadataGeneration() test case for new style test case.
	}
	
	private Properties readPropertiesFile(String content) throws Exception {
		Properties prop = new Properties();
		InputStream is = new ByteArrayInputStream(content.getBytes(Charsets.UTF_8));
		prop.load(is);
		is.close();
		return prop;
	}
}
