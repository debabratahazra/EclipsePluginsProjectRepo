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
 * DS-3931
 *
 * @author pkk
 *
 */
public class MatrixAggregatesCssTransformTest extends AbstractPageDesignerUnitTest {

	private static final String DOMAIN_MODEL = "domain/ds3714/SimpleData.domain";
	private static final String MODULE_MODEL = "module/Default/module/DS3931.module";

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
	 * DS-3931
	 * 
	 * @throws PartInitException
	 */
	@Test
	public void testDS3931AggregatesCssTest() throws PartInitException {
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

			NodeList nodes = document.getElementsByTagName("udp:aggregated-cell");
			Assert.assertTrue(nodes.getLength() == 1);
			Node aggcell = nodes.item(0);
			NodeList cells = aggcell.getChildNodes();
			Assert.assertTrue(cells.getLength() == 3);
			Node cell = cells.item(1);
			Assert.assertTrue(cell.getChildNodes().getLength() == 3);
			Node label = cell.getChildNodes().item(1);
			NamedNodeMap map = label.getAttributes();
			Node clss = map.getNamedItem("class");
			Assert.assertNotNull("class attribute not found for aggregate", clss);
			Assert.assertTrue("red".equals(clss.getNodeValue()));	
			
			nodes = document.getElementsByTagName("udp:for-each-column-footer");
			Assert.assertTrue(nodes.getLength() == 1);
			aggcell = nodes.item(0);
			cells = aggcell.getChildNodes();
			Assert.assertTrue(cells.getLength() == 3);
			cell = cells.item(1);
			Assert.assertTrue(cell.getChildNodes().getLength() == 3);
			label = cell.getChildNodes().item(1);
			map = label.getAttributes();
			clss = map.getNamedItem("class");
			Assert.assertNotNull("class attribute not found for aggregate", clss);
			Assert.assertTrue("green".equals(clss.getNodeValue()));	
		}
	}
}