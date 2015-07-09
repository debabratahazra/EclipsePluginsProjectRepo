package com.odcgroup.page.ui.util.tests;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.gef.RootEditPart;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.odcgroup.page.ui.edit.WidgetEditPart;
import com.odcgroup.page.ui.editor.DesignEditor;
import com.odcgroup.page.ui.tests.util.PageUiAssert;

/**
 * DS-3796
 *
 * @author pkk
 *
 */
public class MatrixExtraColumnDisplayFormatTest extends AbstractPageDesignerUnitTest {

	private static final String DOMAIN_MODEL = "domain/ds3714/SimpleData.domain";
	private static final String MODULE_MODEL = "module/Default/module/DS3796.module";

	@Before
	public void setUp() throws Exception {
		createModelsProject();
		copyResourceInModelsProject(DOMAIN_MODEL, MODULE_MODEL);
		closeWelcomeScreen(); // Otherwise the editor won't get displayed
	}

	@After
	public void tearDown() throws Exception {
		deleteModelsProjects();
	}
	
	/**
	 * DS-3796
	 * 
	 * @throws PartInitException
	 */
	@Test
	public void testDS3796MatrixExtraColumnDisplayFormat() throws PartInitException {
		IFile moduleFile = getProject().getFile(MODULE_MODEL);
		Assert.assertTrue(moduleFile.exists());
		IEditorPart ep = openDefaultEditor(moduleFile);
		PageUiAssert.assertInstanceOfMultiPageEditorPart(ep);
		MultiPageEditorPart mep = (MultiPageEditorPart) ep;
		DesignEditor editor = (DesignEditor) mep.getSelectedPage();
		RootEditPart rep = editor.getViewer().getRootEditPart();

		List<?> list = rep.getChildren();
		if (!list.isEmpty()) {
			WidgetEditPart wep = (WidgetEditPart) list.get(0);
			Assert.assertNotNull("Module WidgetEditPart is not found", wep);

			Document document = fetchGenDocument(getOfsProject(), wep.getWidget());

			//DS-3796 - extra for matrix
			NodeList nodes = document.getElementsByTagName("udp:keep");
			Assert.assertTrue(nodes.getLength() == 3);
			Node keep = nodes.item(2);
			Assert.assertNotNull(keep);
			Assert.assertEquals("Matrix Extra is not found", "my_return", keep.getTextContent());	
			NamedNodeMap attrs = keep.getAttributes();
			Node attr1 = attrs.getNamedItem("as-type");
			Assert.assertNotNull("as-type attribute not found", attr1);
			Assert.assertEquals("as-type attribute value not matched", "decimal", attr1.getNodeValue());
			Node attr2 = attrs.getNamedItem("format");
			Assert.assertNotNull("format attribute not found", attr2);
			Assert.assertEquals("format attribute value not matched", "detailed", attr2.getNodeValue());
		
		}

	}

}