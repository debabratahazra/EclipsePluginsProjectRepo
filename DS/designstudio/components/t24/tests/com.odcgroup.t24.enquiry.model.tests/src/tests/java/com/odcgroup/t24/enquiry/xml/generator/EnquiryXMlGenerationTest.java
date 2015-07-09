package com.odcgroup.t24.enquiry.xml.generator;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang.CharEncoding;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.odcgroup.t24.enquiry.EnquiryInjectorProvider;
import com.odcgroup.workbench.core.tests.util.XMLTestsUtils;
import com.odcgroup.workbench.generation.tests.ng.GeneratorTestHelper;

@RunWith(XtextRunner.class)
@InjectWith(EnquiryInjectorProvider.class)
public class EnquiryXMlGenerationTest {
	@Inject	GeneratorTestHelper helper;
	@Inject EnquiryXMLGenerator xmlGenerator;

	@Test
	public void testEnquiryXMLGenerator() throws Exception {
		Resource xmlResource = null;
		String xml = null;

		URI uri = helper.getURI("FATCA.SECURITIES.WHT.enquiry", getClass());
		xmlResource = xmlGenerator.generateXMLResourceWithoutYetSavingIt(helper.loader.getResource(uri), uri,
				helper.loader);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		xmlResource.save(baos, null);
		xml = baos.toString(CharEncoding.UTF_8);

		assertNotNull(xml);
		assertNotNull(xmlResource);

		// check if the generated xml contain enquiry name
		assertThat(xml, containsString("name=\"FATCA.SECURITIES.WHT\""));
		Assert.assertFalse(xml.contains("generateIFP="));
		
		// check if the generated xml does not contain enquiry mode
		Assert.assertFalse(xml.contains("enquiryMode="));

		// check if the generated xml contains server mode
		Assert.assertFalse(xml.contains("serverMode="));

		// Compare xml content with sample one.
		String expectedXml = Resources.toString(Resources.getResource(getClass(), "FATCA.SECURITIES.WHT.xml"),
				Charsets.UTF_8);
		XMLTestsUtils.assertXml("XML content not matched.", expectedXml, xml, new String());

		xmlResource.unload();
	}

	@Test
	public void testDefaultValueGenerationForINT() throws Exception{
		String xmlGen = null;
		URI uri = helper.getURI("AA.PRODUCT.DESIGNER.enquiry", getClass());
		xmlGenerator.doGenerate(uri, helper.loader, helper.fsa);
		xmlGen = helper.getGeneratedFile("AA.PRODUCT.DESIGNER.enquiry.xml");
		assertNotNull(xmlGen);
		Assert.assertTrue(xmlGen.contains("position column=\"0\"/"));
		Assert.assertTrue(xmlGen.contains("header column=\"1\" line=\"0\""));

		String expectedXml = Resources.toString(Resources.getResource(getClass(), "AA.PRODUCT.DESIGNER.xml"),
				Charsets.UTF_8);
		XMLTestsUtils.assertXml("XML content not matched.", expectedXml, xmlGen, new String());
	}

	@Test
	public void testDrillDownTypeModification() throws Exception{
		String xmlGen = null;
		URI uri = helper.getURI("AA.PRODUCT.DESIGNER.enquiry", getClass());
		xmlGenerator.doGenerate(uri, helper.loader, helper.fsa);
		xmlGen = helper.getGeneratedFile("AA.PRODUCT.DESIGNER.enquiry.xml");
		assertNotNull(xmlGen);
		Assert.assertTrue(xmlGen.contains("drillDowns labelField=\"@ID\" quitSee=\"@ID\""));
		Assert.assertFalse(xmlGen.contains("type property=\"quit-SEE:\""));
		String expectedXml = Resources.toString(Resources.getResource(getClass(), "AA.PRODUCT.DESIGNER.xml"),
				Charsets.UTF_8);
		XMLTestsUtils.assertXml("XML content not matched.", expectedXml, xmlGen, new String());
	}

}
