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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.odcgroup.page.ui.edit.WidgetEditPart;
import com.odcgroup.page.ui.editor.DesignEditor;
import com.odcgroup.page.ui.tests.util.PageUiAssert;

/**
 * DS-3867
 *
 * @author pkk
 *
 */
public class MatrixCellItemPropertiesTest extends AbstractPageDesignerUnitTest {

	private static final String DOMAIN_MODEL = "domain/ds3714/SimpleData.domain";
	private static final String MODULE_MODEL = "module/Default/module/DS3867.module";

	/**
	 * @throws PartInitException
	 */
	@Test
	public void testDS3867MatrixCellItemProperties() throws PartInitException {
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
			// max-groupings for x-axis
			NodeList nodes = document.getElementsByTagName("udp:for-each-cell");
			Assert.assertTrue(nodes.getLength() == 1);
			Node cell = getCellNode(nodes.item(0));
			Assert.assertNotNull(cell);				
			Node label = getLabelNode(cell);
			Assert.assertNotNull(label);
			Node classAttr = label.getAttributes().getNamedItem("class");
			Assert.assertNotNull("class attribute not found", classAttr);
			Assert.assertEquals("red_bold", classAttr.getNodeValue());
			
			Node halign = label.getAttributes().getNamedItem("halign");
			Assert.assertNotNull("halign attribute not found", halign);
			Assert.assertEquals("lead", halign.getNodeValue());
			
			Node valign = label.getAttributes().getNamedItem("valign");
			Assert.assertNotNull("valign attribute not found", valign);
			Assert.assertEquals("center", valign.getNodeValue());				
		}
	}
	
	/**
	 * @param node
	 * @return
	 */
	private Node getCellNode(Node node) {
		NodeList children = node.getChildNodes();
		Node child;
		for (int i = 0; i < children.getLength(); i++) {
			child = children.item(i);
			if(child.getNodeName().equals("xgui:cell")) {
				return child;
			}
		}
		return null;
	}
	
	/**
	 * @return
	 */
	private Node getLabelNode(Node cell) {
		NodeList children = cell.getChildNodes();
		Node child;
		for (int i = 0; i < children.getLength(); i++) {
			child = children.item(i);
			if(child.getNodeName().equals("xgui:label")) {
				return child;
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Before
	public void setUp() throws Exception {
		createModelsProject();
		copyResourceInModelsProject(DOMAIN_MODEL, MODULE_MODEL);
		closeWelcomeScreen(); // Otherwise the editor won't get displayed
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	@After
	public void tearDown() throws Exception {
		deleteModelsProjects();
	}

}
