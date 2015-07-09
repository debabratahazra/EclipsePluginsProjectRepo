package com.odcgroup.t24.mdf.generation.tests;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.google.inject.Inject;
import com.odcgroup.t24.mdf.generator.T24MdfGenerator;
import com.odcgroup.workbench.core.tests.util.XMLTestsUtils;
import com.odcgroup.workbench.generation.tests.ng.GeneratorTestHelper;

/**
 * Integration test of the T24MdfGenerator.
 * 
 * TODO this is just a skeleton, someone will complete this (and remove this line)
 * @see http://rd.oams.com/browse/DS-7433
 * 
 * @author Michael Vorburger (com.odcgroup.workbench.generation.tests.ng.tests.ExampleGeneratorTest)
 * TODO add your name here if you actually make this test useful...
 */
@RunWith(XtextRunner.class)
@InjectWith(DomainWithLocalRefDependenciesInjectorProvider.class)
public class T24MdfGeneratorTest {
	
	@Inject	T24MdfGenerator generatorToTest;
	@Inject	GeneratorTestHelper helper;

	@Test
	public void LocalFieldDefDomainGenerationTest() throws Exception {
		helper.loader.getResource(helper.getURI("BusinessTypes.domain", getClass()));
		helper.loader.getResource(helper.getURI("ST_Config.domain", getClass()));
		helper.loader.getResource(helper.getURI("LocalFieldsEnumeration.domain", getClass()));
		
		generateAndAssertXML("LocalFieldsDefinition.domain", "151.xml");
	}
	
	@Test
	public void LocalRefApplicationGenerationTest() throws Exception {
		helper.loader.getResource(helper.getURI("BusinessTypes.domain", getClass()));
		helper.loader.getResource(helper.getURI("ST_CompanyCreation.domain", getClass()));
		helper.loader.getResource(helper.getURI("EB_ARC.domain", getClass()));

		generateAndAssertXML("X_EB_SECURE_MESSAGE.domain", "X_EB_SECURE_MESSAGE.xml");
	}

	protected void generateAndAssertXML(String inputModelClasspathResourceToGenerate, String xmlSampleToAssertAgainst) throws Exception {
		URI input = helper.getURI(inputModelClasspathResourceToGenerate, getClass());
		generatorToTest.doGenerate(input, helper.loader, helper.fsa);
		String genXML = helper.getGeneratedFile(xmlSampleToAssertAgainst);
		assertXMLFiles(genXML, xmlSampleToAssertAgainst);
	}
	
	/**
	 * Assert the sample xml file with the generated now.
	 * 
	 * @param resource
	 * @throws IOException
	 */
	private void assertXMLFiles(String genXml, String sampleFilename) throws Exception {
		String expectedXml = Resources.toString(Resources.getResource(getClass(), "sample/" + sampleFilename), Charsets.UTF_8);
		XMLTestsUtils.assertXml("XML content not matched.", expectedXml, genXml, new String());
	}
}