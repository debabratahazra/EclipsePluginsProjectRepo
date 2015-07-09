package com.odcgroup.page.ui.util.tests;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.draw2d.IFigure;
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
import com.odcgroup.page.ui.figure.table.TableFigure;
import com.odcgroup.page.ui.tests.util.PageUiAssert;

/**
 * DS-3830
 *
 * @author pkk
 *
 */
public class TableFilterSubmitMethodTest  extends AbstractPageDesignerUnitTest {

	private static final String DOMAIN_MODEL = "domain/ds3553/DS3553.domain";
	private static final String MODULE_MODEL = "module/Default/module/DS3830.module";

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
	public void testDS3830FilterSubmitMethod() throws PartInitException {
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
			WidgetEditPart tableep = (WidgetEditPart) bep.getChildren().get(0);
			IFigure mfig = tableep.getFigure();
			Assert.assertTrue(mfig instanceof TableFigure);				

			Document document = fetchGenDocument(getOfsProject(), wep.getWidget());
			NodeList nodes = document.getElementsByTagName("xgui:icon");
			Assert.assertTrue(nodes.getLength() == 2);
			Node icon = nodes.item(0);
			Assert.assertNotNull(icon);
			Node submitnode = getSubmitNodeOnFilterIcon(icon);
			Assert.assertNotNull(submitnode);
			Node methodAttr = submitnode.getAttributes().getNamedItem("method");
			Assert.assertEquals("post", methodAttr.getNodeValue());
		}
	}
	
	/**
	 * @param column
	 * @return
	 */
	private Node getSubmitNodeOnFilterIcon(Node column) {
		NodeList nodes = column.getChildNodes();
		Node node = null;
		for (int i = 0; i < nodes.getLength(); i++) {
			node = nodes.item(i);
			if (node.getNodeName().equals("xgui:onevent")) {
				NodeList eNodes = node.getChildNodes();
				Node eNode = null;
				for(int j=0;j<eNodes.getLength();j++) {
					eNode = eNodes.item(j);
					if (eNode.getNodeName().equals("xgui:submit")) {
						return eNode;
					}
				}
			}			
		}
		return null;
	}

}