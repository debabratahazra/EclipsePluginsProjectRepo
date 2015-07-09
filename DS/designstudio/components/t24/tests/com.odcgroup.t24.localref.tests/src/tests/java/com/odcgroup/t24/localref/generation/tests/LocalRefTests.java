package com.odcgroup.t24.localref.generation.tests;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import javax.inject.Inject;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang.CharEncoding;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.odcgroup.domain.DomainInjectorProvider;
import com.odcgroup.t24.mdf.generator.T24MdfGenerator;
import com.odcgroup.workbench.core.tests.util.XMLTestsUtils;
import com.odcgroup.workbench.generation.tests.ng.GeneratorTestHelper;

@RunWith(XtextRunner.class)
@InjectWith(DomainInjectorProvider.class)
public class LocalRefTests {
	@Inject
	T24MdfGenerator generatorToTest;
	@Inject
	GeneratorTestHelper helper;

	@Test
	public void testLocalFieldEnumerationType() throws Exception {

		helper.loader.getResource(helper.getURI("BusinessTypes.domain", getClass()));
		helper.loader.getResource(helper.getURI("ST_Config.domain", getClass()));
		helper.loader.getResource(helper.getURI("LocalFieldsEnumeration.domain", getClass()));

		URI uri = helper.getURI("LocalFieldsDefinition.domain", getClass());

		Resource xmlResource = null;
		String xml = null;
		xmlResource = generatorToTest.generateXMLResourceWithoutYetSavingIt(helper.loader.getResource(uri), uri,
				helper.loader);
		assertNotNull(xmlResource);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		xmlResource.save(baos, null);
		xml = baos.toString(CharEncoding.UTF_8);
		assertNotNull(xml);
		assertXMLFiles(xml);
	}

	/**
	 * Assert the sample xml file with the generated now.
	 * 
	 * @param resource
	 * @throws IOException
	 */
	private void assertXMLFiles(String genXml) throws Exception {
		String expectedXml = Resources.toString(Resources.getResource(getClass(), "sample/151.xml"), Charsets.UTF_8);
		XMLTestsUtils.assertXml("XML content not matched.", expectedXml, genXml, new String());
	}
}
