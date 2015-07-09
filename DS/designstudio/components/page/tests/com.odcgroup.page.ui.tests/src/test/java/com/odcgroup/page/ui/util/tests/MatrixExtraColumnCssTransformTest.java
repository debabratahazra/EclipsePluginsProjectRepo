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
 *
 * @author pkk
 *
 */
public class MatrixExtraColumnCssTransformTest extends AbstractPageDesignerUnitTest {

	private static final String DOMAIN_MODEL = "domain/ds3714/SimpleData.domain";
	private static final String MODULE_MODEL = "module/Default/module/DS3776.module";

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
	 * DS-3933
	 * 
	 * @throws PartInitException
	 */
	@Test
	public void testDS3933ExtraColumnCssTest() throws PartInitException {
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
			NodeList nodes = document.getElementsByTagName("udp:for-each-row");
			Assert.assertTrue(nodes.getLength() == 1);
			Node aggcell = nodes.item(0);
			NodeList cells = aggcell.getChildNodes();
			Assert.assertTrue(cells.getLength() == 3);
			Node row = cells.item(1);
			Assert.assertTrue(row.getChildNodes().getLength() == 9);
			Node cell = row.getChildNodes().item(7);
			Assert.assertTrue(cell.getChildNodes().getLength() == 3);
			Node label = cell.getChildNodes().item(1);
			NamedNodeMap map = label.getAttributes();
			Node halign = map.getNamedItem("class");
			Assert.assertNotNull("class attribute not found for extra column", halign);
			Assert.assertTrue("green".equals(halign.getNodeValue()));			
		}
	}
}
