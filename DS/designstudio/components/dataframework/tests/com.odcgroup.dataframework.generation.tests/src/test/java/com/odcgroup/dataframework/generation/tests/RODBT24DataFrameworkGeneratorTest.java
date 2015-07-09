package com.odcgroup.dataframework.generation.tests;

import java.util.Map;

import junit.framework.Assert;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.google.inject.Inject;
import com.odcgroup.dataframework.generation.T24DataFrameworkGenerator;
import com.odcgroup.workbench.core.tests.util.MultiplatformTestUtil;
import com.odcgroup.workbench.generation.tests.ng.GeneratorTestHelper;

/**
 * Integration test for Read Only DataBase enquiries.
 * 
 * @author jtricker
 */
@RunWith(XtextRunner.class)
@InjectWith(IRISInjectorProvider.class)

public class RODBT24DataFrameworkGeneratorTest {

	@Inject T24DataFrameworkGenerator dataFrameworkGenerator;
	@Inject GeneratorTestHelper helper;
	
	private final static String RODB_ENQUIRY_MODEL = "RODB.ENQUIRY.enquiry";
	private final static String T24_ENQUIRY_MODEL = "T24.ENQUIRY.enquiry";
	private final static String NOMODE_ENQUIRY_MODEL = "NOMODE.ENQUIRY.enquiry";
	private final static String RODB_NON_ENQUIRY_MODEL = "RODB.ENQUIRY.domain";

	/**
	 * Check that a canned enquiry, marked up as if it had come from a RO
	 * database generates, the expected Java.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRODBEnquiry() throws Exception {

		helper.loader.getResource(helper.getURI(T24_ENQUIRY_MODEL, getClass()));
		helper.loader.getResource(helper.getURI(NOMODE_ENQUIRY_MODEL, getClass()));
		URI uri = helper.getURI(RODB_ENQUIRY_MODEL, getClass());

		dataFrameworkGenerator.doGenerate(uri, helper.loader, helper.fsa);
		Map<String, CharSequence> genFiles = helper.fsa.getTextFiles();

		CharSequence generated = genFiles.get("DEFAULT_OUTPUTcom/temenos/dataframework/rodb/enqRodbEnquiry.java");
		Assert.assertNotNull(generated);
		String expected = Resources.toString(Resources.getResource(getClass(), "enqRodbEnquiry.javatxt"),
				Charsets.UTF_8);
		Assert.assertNotNull(expected);
		MultiplatformTestUtil.assertEqualsIgnoringEOL(
				"Assertion Failed, Generated java file is not same to sample java file.", expected,
				generated.toString());
	}

	/**
	 * Check that a canned enquiry, marked up as if it had come from a RW
	 * database does not generate Java.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testNonRODBEnquiry() throws Exception {
		helper.loader.getResource(helper.getURI(NOMODE_ENQUIRY_MODEL, getClass()));
		helper.loader.getResource(helper.getURI(RODB_ENQUIRY_MODEL, getClass()));
		URI stdURI = helper.getURI(T24_ENQUIRY_MODEL, getClass());
		dataFrameworkGenerator.doGenerate(stdURI, helper.loader, helper.fsa);
		Map<String, CharSequence> genFiles = helper.fsa.getTextFiles();
		Assert.assertNotNull(genFiles);
		Assert.assertEquals("Java file(s) generated", 0, genFiles.keySet().size());
	}

	
	/**
	 * Check that a canned enquiry without database markup, i.e. the old style,
	 * does not generate Java.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testMissingRODBEnquiry() throws Exception {
		Resource enquiryResource = helper.loader.getResource(helper.getURI(NOMODE_ENQUIRY_MODEL, getClass()));
		URI stdURI = enquiryResource.getURI();
		dataFrameworkGenerator.doGenerate(stdURI, helper.loader, helper.fsa);
		Map<String, CharSequence> genFiles = helper.fsa.getTextFiles();
		Assert.assertNotNull(genFiles);
		Assert.assertEquals("Generated resources", 0, genFiles.keySet().size());
	}
	
	/**
	 * Check that a canned enquiry, marked up as if it had come from a RO
	 * database, but named as some other entity type does not throw or generate Java.
	 * 
	 * i.e. don't throw and break the build.
	 * 
	 * @throws Exception
	 */
	@Test (expected=AssertionError.class)
	public void testRODBNonEnquiry() throws Exception {
		Resource enquiryResource = helper.loader.getResource(helper.getURI(RODB_NON_ENQUIRY_MODEL, getClass())); // AssertionError - expected here with new style test case
		URI stdURI = enquiryResource.getURI();
		try {
			dataFrameworkGenerator.doGenerate(stdURI, helper.loader, helper.fsa);
		}
		catch (Exception e) {
			Assert.fail("Generation with wrong entity type should fail silently but threw: " + e.getMessage());
		}
	}
}
