package com.odcgroup.iris.metadata.generation.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.odcgroup.iris.generation.tests.IRISMetadataInjectorProvider;
import com.odcgroup.iris.generator.IRISMetadataGenerator2;
import com.odcgroup.workbench.generation.tests.ng.GeneratorTestHelper;

@RunWith(XtextRunner.class)
@InjectWith(IRISMetadataInjectorProvider.class)
public class RODBIRISMetadataGeneratorTests {
	@Inject IRISMetadataGenerator2 generator;
	@Inject GeneratorTestHelper helper;
	
	 private static String ENQUIRY_MODEL = "RODB.ENQUIRY.enquiry";
	
	/*
	 * An enquiry with the RODB attribute set is going to access a read only database. The .xml file is required but,
	 * since T24 will not be accessed, no .properties file is required. 
	 */
	@Test
	public void testRODBNotGenerated() throws Exception {
		helper.loader.getResource(helper.getURI(ENQUIRY_MODEL, getClass()));
		URI input = helper.getURI(ENQUIRY_MODEL, getClass());
		
		generator.doGenerate(input, helper.loader, helper.fsa);

		Map<String, CharSequence> genFiles = helper.fsa.getTextFiles();
		
		// Check there is just one file
		assertEquals("Incorrect files count", 1, genFiles.size());
		
		// Check that it's the .xml file
		CharSequence genXML = genFiles.get("DEFAULT_OUTPUTmetadata-enqRodbEnquiry.xml");
		assertNotNull(".xml file not generated", genXML);
	}
}