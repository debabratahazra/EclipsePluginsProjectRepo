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
 * DS-3792	
 *
 * @author pkk
 *
 */
public class MatrixAxisSortTest extends AbstractPageDesignerUnitTest {

	private static final String DOMAIN_MODEL = "domain/ds3714/SimpleData.domain";
	private static final String MODULE_MODEL = "module/Default/module/DS3792.module";

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
	 * DS-3792
	 * 
	 * @throws PartInitException
	 */
	@Test
	public void testDS3792SortMatrixAxisAggregations() throws PartInitException {
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

			//DS-3792 - munge columns
			NodeList nodes = document.getElementsByTagName("udp:compute");
			Assert.assertTrue(nodes.getLength() == 1);
			Node compu = nodes.item(0);
			Assert.assertNotNull(compu);
			Node nameAttr = compu.getAttributes().getNamedItem("name");
			Assert.assertNotNull(nameAttr);
			Assert.assertEquals("sort column name is not as expected", "asset_rank_sum_my_return", nameAttr.getNodeValue());
			
			//  sort-column
			nodes = document.getElementsByTagName("udp:sort-column");
			Assert.assertTrue(nodes.getLength() == 2);
			Node sortcol = nodes.item(1);
			Assert.assertEquals("sort column name is not as expected", "asset_rank_sum_my_return", sortcol.getTextContent());
			
		
		}
	}
}
