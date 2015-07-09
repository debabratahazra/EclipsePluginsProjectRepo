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
 * @author pkk
 *
 */
public class MatrixWeightedMeanGenTest extends AbstractPageDesignerUnitTest {

	private static final String DOMAIN_MODEL = "domain/ds3714/SimpleData.domain";
	private static final String MODULE_MODEL = "module/Default/module/DS4422.module";

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
	 * DS-4422
	 * 
	 * @throws PartInitException
	 */
	@Test
	public void testMatrixWeightedMeanGen() throws PartInitException {
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

			//check if the column defined as weighted-mean weight is found in keep
			NodeList nodes = document.getElementsByTagName("udp:keep");
			Assert.assertTrue(nodes.getLength() == 4);
			Node keep = nodes.item(3);
			Assert.assertNotNull(keep);
			Assert.assertEquals("Matrix weighted-mean weight is not found", "mkt_val", keep.getTextContent());
			
			NodeList aggs = document.getElementsByTagName("udp:aggregate");
			Assert.assertTrue(aggs.getLength() == 4);
			Node agg = aggs.item(1);
			Node attr = agg.getAttributes().getNamedItem("aggregated-column");
			Assert.assertNotNull(attr);
			Assert.assertEquals("aggregation for mkt_val is not defined", "mkt_val", attr.getNodeValue());	
			agg = aggs.item(3);
			attr = agg.getAttributes().getNamedItem("aggregated-column");
			Assert.assertNotNull(attr);
			Assert.assertEquals("aggregation for mkt_val is not defined", "mkt_val", attr.getNodeValue());			
			
		}

	}
	


}
