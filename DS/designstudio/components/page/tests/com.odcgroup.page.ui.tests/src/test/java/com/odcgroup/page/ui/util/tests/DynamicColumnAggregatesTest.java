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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.odcgroup.page.ui.edit.WidgetEditPart;
import com.odcgroup.page.ui.editor.DesignEditor;
import com.odcgroup.page.ui.tests.util.PageUiAssert;

/**
 * DS-3907
 *
 * @author pkk
 *
 */
public class DynamicColumnAggregatesTest extends AbstractPageDesignerUnitTest {

	private static final String DOMAIN_MODEL = "domain/ds3458/DS3458.domain";
	private static final String MODULE_MODEL = "module/Default/module/DS3907.module";

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
	 * DS-3907
	 * 
	 * @throws PartInitException
	 */
	@Test
	public void testDS3907DynamicColumnAggregation() throws PartInitException {
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
			NodeList nodes = document.getElementsByTagName("udp:aggregation");
			Assert.assertEquals(1, nodes.getLength());
			Node aggcell = nodes.item(0);
			NodeList cells = aggcell.getChildNodes();
			List<Node> aggregates = filterTextNodes(cells);
			Assert.assertTrue(aggregates.size() == 6);
			Node cell = aggregates.get(5);
			Assert.assertTrue(cell.getNodeName().equals("udp:dynamic-columns"));
			List<Node> dAggregates = filterTextNodes(cell.getChildNodes());
			Assert.assertTrue(dAggregates.size() == 2);
			Node attr = null;
			for (Node node : dAggregates) {
				attr = node.getAttributes().getNamedItem("aggregation-computation");
				Assert.assertTrue("Dynamic column aggregation should be max", attr.getNodeValue().equals("max"));
			}		
		}
	}
	
	/**
	 * @param cells
	 */
	private List<Node> filterTextNodes(NodeList cells) {
		Node node = null;
		List<Node> nodes = new ArrayList<Node>();
		for(int i =0; i < cells.getLength();i++) {
			node = cells.item(i);
			if(!node.getNodeName().equals("#text")) {
				nodes.add(node);
			}
		}
		return nodes;		
	}

}
