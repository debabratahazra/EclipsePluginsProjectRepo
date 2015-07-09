package com.odcgroup.iris.rim.generation.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.google.inject.Inject;
import com.odcgroup.iris.rim.generation.T24ResourceModelsGenerator;
import com.odcgroup.workbench.core.tests.util.MultiplatformTestUtil;
import com.odcgroup.workbench.generation.tests.ng.GeneratorTestHelper;

/**
 * Integration test for Read Only DataBase enquiries.
 * 
 * @author jtricker
 * @author Debabrata
 */
@RunWith(XtextRunner.class)
@InjectWith(IRISInjectorProvider.class)
public class RODBT24ResourceModelsGeneratorTest {

	private final static String RODB_ENQUIRY_MODEL = "RODB.ENQUIRY.enquiry";
	private final static String RODB_RIM_FILENAME = "enqRODB.ENQUIRY.rim";

	private static final String DEFAULT_OUTPUTT24 = "DEFAULT_OUTPUTT24/";

	@Inject
	T24ResourceModelsGenerator t24ResourceModelsGenerator;
	@Inject
	GeneratorTestHelper helper;

	/**
	 * Check that a canned enquiry, marked up as if it had come from a RO
	 * database generates, the expected RIM.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRODBEnquiry() throws Exception {
		URI uri = helper.getURI(RODB_ENQUIRY_MODEL, getClass());
		t24ResourceModelsGenerator.doGenerate(uri, helper.loader, helper.fsa);
		Map<String, CharSequence> files = helper.fsa.getTextFiles();
		assertEquals("Generated resources", 1, getNumberOfFilesGenerated(files));
		assertTrue("RIM File not exist: " + RODB_RIM_FILENAME, files.get(DEFAULT_OUTPUTT24 + RODB_RIM_FILENAME) != null);
		matchResults(files, RODB_RIM_FILENAME);
	}

	private void matchResults(Map<String, CharSequence> files, String filename) throws IOException {
		String actual = files.get(DEFAULT_OUTPUTT24 + filename).toString();
		String expected = Resources.toString(Resources.getResource(getClass(), filename), Charsets.UTF_8);
		MultiplatformTestUtil.assertEqualsIgnoringEOL(
				"Assertion Failed, Generated RIM file is not same to sample RIM file.", expected, actual);
	}

	private int getNumberOfFilesGenerated(Map<String, CharSequence> files) {
		Set<String> set = files.keySet();
		List<String> list = new ArrayList<String>();
		for (String string : set) {
			String str = string;
			if (str.contains(".rim")) {
				list.add(str);
			}
		}
		return list.size();
	}
}
