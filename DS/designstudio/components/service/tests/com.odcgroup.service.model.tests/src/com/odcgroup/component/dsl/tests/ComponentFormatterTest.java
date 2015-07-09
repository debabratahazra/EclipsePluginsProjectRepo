package com.odcgroup.component.dsl.tests;

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
import com.odcgroup.service.model.ComponentInjectorProvider;
import com.odcgroup.workbench.dsl.xml.EIO;
import com.odcgroup.workbench.generation.tests.ng.ValidatingModelLoaderImpl;

/**
 * Tests Formatting.
 * 
 * @author vramya
 */
@RunWith(XtextRunner.class)
@InjectWith(ComponentInjectorProvider.class)
public class ComponentFormatterTest {

	@Inject
	INodeModelFormatter formatter;

	@Inject
	EIO eio;

	@Inject
	ValidatingModelLoaderImpl loader;

	@Test
	public void testFormatting() throws Exception {
		URI uri = eio.getURI("sampleComponent.component", getClass());
		String text = Resources.toString(Resources.getResource(getClass(), "sampleComponent.component"), Charsets.UTF_8);
		EObject eo = loader.getResource(uri).getContents().get(0);
		IParseResult parseResult = ((XtextResource) eo.eResource()).getParseResult();
		assertNotNull(parseResult);
		ICompositeNode rootNode = parseResult.getRootNode();
		String formattedText = formatter.format(rootNode, 0, text.length()).getFormattedText();
		// This is required as formatter was adding \r\n but sample text has only \n which was causing test case to fail.
		text = text.replaceAll("\r\n","\n");
		formattedText = formattedText.replaceAll("\r\n", "\n");
		assertEquals(text, formattedText);
	}

}