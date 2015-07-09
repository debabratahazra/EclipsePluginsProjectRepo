package com.odcgroup.t24.version.xml.generator;

import javax.inject.Inject;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.odcgroup.t24.version.model.translation.tests.VersionWithDependencyInjectorProvider;
import com.odcgroup.workbench.core.tests.util.XMLTestsUtils;
import com.odcgroup.workbench.generation.tests.ng.GeneratorTestHelper;

/**
 * Unit test for VersionXMLGenerator.
 * 
 * @author Michael Vorburger
 */
@RunWith(XtextRunner.class)
@InjectWith(VersionWithDependencyInjectorProvider.class)
public class VersionXMLGeneratorTest{
	@Inject VersionXMLGenerator xmlGenerator;
	@Inject	GeneratorTestHelper helper;
	
	/**
	 * Tests converting test.version to test.version.xml. This first test
	 * intentionally does not put a *.domain into the ResourceSet, so the
	 * forApplication reference will not be resolveable - but it should work
	 * anyways.
	 */
	@Test
	public void testVersionXMLGeneratorWithUnresolvedProxy() throws Exception {
		helper.loader.getResource(helper.getURI("BusinessTypes.domain", getClass()));
		helper.loader.getResource(helper.getURI("AA_Accounting.domain", getClass()));

		URI uri = helper.getURI("AA_ARR_ACCOUNTING,AA.version", getClass());
		xmlGenerator.doGenerate(uri, helper.loader, helper.fsa);
		String genXML = helper.getGeneratedFile("AA_ARR_ACCOUNTING,AA.version.xml");

		// Compare xml content with sample one.
		String expectedXml = Resources.toString(Resources.getResource(getClass(), "AA_ARR_ACCOUNTING,AA.xml"),
				Charsets.UTF_8);
		XMLTestsUtils.assertXml("XML content not matched.", expectedXml, genXML, new String());
	}
}
