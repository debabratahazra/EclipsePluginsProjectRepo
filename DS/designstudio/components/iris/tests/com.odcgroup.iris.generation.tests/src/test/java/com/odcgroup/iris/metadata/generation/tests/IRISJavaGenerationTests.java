package com.odcgroup.iris.metadata.generation.tests;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.google.inject.Inject;
import com.odcgroup.iris.generation.tests.IRISInjectorProvider;
import com.odcgroup.iris.java.generation.IRISJavaGenerator;
import com.odcgroup.workbench.core.tests.util.XMLTestsUtils;
import com.odcgroup.workbench.generation.tests.ng.GeneratorTestHelper;

@RunWith(XtextRunner.class)
@InjectWith(IRISInjectorProvider.class)
public class IRISJavaGenerationTests {

	@Inject	IRISJavaGenerator generatorToTest;
	@Inject	GeneratorTestHelper helper;

	private static String RIM_MODEL = "Restbucks.rim";

	@Test
	public void testIRISJavaGeneration() throws Exception {
		
		URI input = helper.getURI(RIM_MODEL, getClass());
		generatorToTest.doGenerate(input, helper.loader, helper.fsa);
		String genXML = helper.getGeneratedFile("IRIS-RestbucksModel_Restbucks-PRD.xml");
		String expectedXml = Resources.toString(Resources.getResource(getClass(), "IRIS-RestbucksModel_Restbucks-PRD.xml"), Charsets.UTF_8);
		XMLTestsUtils.assertXml("XML content not matched.", expectedXml, genXML, new String());
		
	}
}
