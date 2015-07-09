package com.odcgroup.page.ui.util.tests;

import java.util.ArrayList;
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
 * DS-
 *
 * @author pkk
 *
 */
public class MatrixTransformIssuesTests  extends AbstractPageDesignerUnitTest {

	private static final String DOMAIN_MODEL = "domain/ds3714/SimpleData.domain";
	private static final String MODULE_MODEL = "module/Default/module/DS3762.module";

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
	 * DS-3761
	 * DS-3764
	 * DS-3766
	 * 
	 * @throws PartInitException
	 */
	@Test
	public void testMatrixWidgetTransformationIssues() throws PartInitException {
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

			//DS-3761 - extra for matrix
			NodeList nodes = document.getElementsByTagName("udp:keep");
			Assert.assertTrue(nodes.getLength() == 6);
			Node keep = nodes.item(5);
			Assert.assertNotNull(keep);
			Assert.assertEquals("Matrix Extra is not found", "instrument", keep.getTextContent());
			
			// dumpmodel for x-axis DS3764
			nodes = document.getElementsByTagName("udp:dump-model");
			Assert.assertTrue("udp:dumpModel not found in the generated xsp", nodes.getLength() == 1);
			
			// cssClass for Matrix DS-3766
			nodes = document.getElementsByTagName("xgui:matrix");
			Assert.assertTrue(nodes.getLength() > 0);
			Node matrix = nodes.item(0);
			Assert.assertNotNull(matrix);
			NamedNodeMap attrs = matrix.getAttributes();
			Node classAttr = attrs.getNamedItem("class");
			Assert.assertNotNull("class attribute not found for matrix", classAttr);
			Assert.assertEquals("CssClass is not generated as expected", "compliance", classAttr.getNodeValue());
			
			// cssClass for MatrixExtraColumn DS-3801
			nodes = document.getElementsByTagName("udp:for-each-row");
			Assert.assertTrue(nodes.getLength() == 1);
			classAttr = getExtraColumnClassAttributeNode(nodes.item(0).getChildNodes());
			Assert.assertNotNull("class attribute not found for matrix", classAttr);
			Assert.assertEquals("CssClass is not generated as expected", "red_y", classAttr.getNodeValue());
		}

	}
	
	/**
	 * @param nodes
	 * @return
	 */
	private Node getExtraColumnClassAttributeNode(NodeList nodes) {
		Node row = null;
		Node node = null;
		for (int i = 0; i < nodes.getLength(); i++) {
			node = nodes.item(i);
			if ("xgui:row".equals(node.getNodeName())) {
				row = node;
				break;
			}					
		}
		Assert.assertTrue(row != null);
		NodeList cNodes = row.getChildNodes();
		List<Node> cellNodes = new ArrayList<Node>();
		for (int i = 0; i < cNodes.getLength(); i++) {
			node = cNodes.item(i);
			if ("xgui:cell".equals(node.getNodeName())) {
				cellNodes.add(node);
			}
		}
		Assert.assertTrue(cellNodes.size() == 3);
		Node cellNode = cellNodes.get(2);
		NodeList lNodes = cellNode.getChildNodes();
		List<Node> labelNodes = new ArrayList<Node>();
		for (int i = 0; i < lNodes.getLength(); i++) {
			node = lNodes.item(i);
			if ("xgui:label".equals(node.getNodeName())) {
				labelNodes.add(node);
			}
		}
		Assert.assertTrue(labelNodes.size() ==1);		
		return labelNodes.get(0).getAttributes().getNamedItem("class");
	}

}