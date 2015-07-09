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

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.edit.WidgetEditPart;
import com.odcgroup.page.ui.editor.DesignEditor;
import com.odcgroup.page.ui.tests.util.PageUiAssert;

/**
 * DS-3743
 * @author pkk
 *
 */
public class CheckBoxWidgetTransformerTest extends AbstractPageDesignerUnitTest {

	private static final String DOMAIN_MODEL = "domain/ds3553/DS3553.domain";
	private static final String MODULE_MODEL = "module/Default/module/DS3743.module";

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
	 * @throws PartInitException
	 */
	@Test
	public void testDS3743CheckBoxTransformation() throws PartInitException {
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
			List<?> children = wep.getChildren();
			Assert.assertFalse("Module Widget has no children", children.isEmpty());
			WidgetEditPart bep = (WidgetEditPart) children.get(0);
			Assert.assertFalse("Box Widget has no children", bep.getChildren().isEmpty());
			WidgetEditPart chckbox = (WidgetEditPart) bep.getChildren().get(0);
			Assert.assertNotNull("checkbox widget not found", chckbox);
			
			Widget widget = chckbox.getWidget();
			Assert.assertNotNull(widget);
			Assert.assertEquals("trail", widget.getPropertyValue(PropertyTypeConstants.HORIZONTAL_TEXT_POSITION));
			Assert.assertEquals("true", widget.getPropertyValue("displayLabel"));
			
			Document document = fetchGenDocument(getOfsProject(), wep.getWidget());
			NodeList nodes = document.getElementsByTagName("xgui:checkbox");
			Assert.assertTrue(nodes.getLength() > 0);
			Node checkbox = nodes.item(0);
			Assert.assertNotNull(checkbox);
			NamedNodeMap attributes = checkbox.getAttributes();
			Node htextAttr = attributes.getNamedItem("hTextPosition");
			Assert.assertNotNull("hTextPosition attribute not found for checkbox", htextAttr);
			Node textNode = getTextNode(checkbox.getChildNodes());
			Assert.assertNotNull("Label not found for checkbox", textNode);
			Assert.assertEquals("newdomain.dsflat.isnode.text", textNode.getTextContent().trim());
		}
	}
	
	/**
	 * @param children
	 * @return
	 */
	private Node getTextNode(NodeList children) {
		Node node = null;
		for (int i = 0; i < children.getLength(); i++) {
			node = children.item(i);
			if (node.getNodeName().equals("xgui:text")) {
				return node;
			}
		}
		return null;
	}

}
