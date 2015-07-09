package com.odcgroup.edge.t24.generation.tests.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.google.inject.Inject;
import com.odcgroup.edge.t24.generation.util.SkipXPaths;
import com.odcgroup.edge.t24.generation.util.StandaloneTestableT24EdgeGenerator;
import com.odcgroup.edge.t24ui.tests.T24UIInjectorProvider;
import com.odcgroup.workbench.core.tests.util.XMLTestsUtils;
import com.odcgroup.workbench.generation.tests.ng.GeneratorTestHelper;

@RunWith(XtextRunner.class)
@InjectWith(T24UIInjectorProvider.class)
public class ExampleIfpIntegrationTest {

	@Inject StandaloneTestableT24EdgeGenerator generatorToTest;
	@Inject GeneratorTestHelper helper;

	@Test
	public void aBasicEdgeGenerator() throws Exception {
		// Tip: if the "main" resource you are loading depends on other models,
		// you have to load them into the ResourceSet first here like this:
		helper.loader.getResource(helper.getURI("BusinessTypes.domain", getClass()));
		helper.loader.getResource(helper.getURI("AA_Accounting.domain", getClass()));
		
		URI input = helper.getURI("AA_ARR_ACCOUNTING,AA.version", getClass());

		generatorToTest.beforeGeneration();
		generatorToTest.doGenerate(input, helper.loader, helper.fsa);
		generatorToTest.afterGeneration();
		
		// TODO assertEquals("com.odcgroup.ds.Test", helper.getGeneratedFile("<ifp file>"));
		// The problem is that the T24EdgeGenerator doesn't actually output into the IFileSystemAccess ... :-(
		// location of generated file
		//target/StandaloneTestableT24EdgeGenerator-gen/src/generated/edge/META-INF/resources/WEB-INF/DynamicComponents/edge/t24/generation/tests/example/verAaArrAccounting_Aa/verAaArrAccounting_Aa.ifp
		
		CharSequence genXml = getContents(generatorToTest.getOutputPath());
		String expectedXml = Resources.toString(Resources.getResource(getClass(), "verAaArrAccounting_Aa.ifp"),
				Charsets.UTF_8);
		
		// Skips few elements during XML compare.
		XMLTestsUtils.assertXml("XML content not matched.", expectedXml, genXml.toString(), SkipXPaths.skipPaths);
	}

	private CharSequence getContents(String location) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				getClass().getResourceAsStream("/" + location)));
		StringBuffer buffer = new StringBuffer();
		try {
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
		} catch (IOException ex) {
			throw new IOException(ex.getMessage());
		}

		finally {
			try {
				reader.close();
			} catch (IOException e) {
				throw new IOException(e.getMessage());
			}
		}
		return buffer;
	}

}
