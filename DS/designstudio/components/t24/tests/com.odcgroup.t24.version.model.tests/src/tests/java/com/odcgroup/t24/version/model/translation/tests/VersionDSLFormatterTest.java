package com.odcgroup.t24.version.model.translation.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.formatting.INodeModelFormatter;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.resource.XtextResource;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.odcgroup.workbench.generation.tests.ng.GeneratorTestHelper;

/**
 * Tests Formatting.
 * 
 * @author vramya
 */
@RunWith(XtextRunner.class)
@InjectWith(VersionWithDependencyInjectorProvider.class)
public class VersionDSLFormatterTest {

	@Inject
	INodeModelFormatter formatter;

	@Inject
	GeneratorTestHelper helper;

	@Test
	public void testFormatting() throws Exception {
		helper.loader.getResource(helper.getURI("BusinessTypes.domain", getClass()));
		helper.loader.getResource(helper.getURI("AA_Accounting.domain", getClass()));
		
		URI uri = helper.getURI("AA_ARR_ACCOUNTING,AA.version", getClass());
		String text = Resources.toString(Resources.getResource(getClass(), "AA_ARR_ACCOUNTING,AA.version"), Charsets.UTF_8);
		EObject eo = helper.loader.getResource(uri).getContents().get(0);
		IParseResult parseResult = ((XtextResource) eo.eResource()).getParseResult();
		assertNotNull(parseResult);
		ICompositeNode rootNode = parseResult.getRootNode();
		String formattedText = formatter.format(rootNode, 0, text.length()).getFormattedText();
		assertEquals(text, formattedText);
	}

}