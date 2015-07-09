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
 * DS-3553
 *
 * @author pkk
 *
 */
public class FilterSetEnumerationTest extends AbstractPageDesignerUnitTest {

	private static final String DOMAIN_MODEL = "domain/ds3553/DS3553.domain";
	private static final String MODULE_MODEL = "module/Default/module/DS3553.module";

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
	public void testDS3553ValueOfEnumerationAttribute() throws PartInitException {
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
			NodeList nodes = document.getElementsByTagName("xgui:param");
			Assert.assertTrue(nodes.getLength() == 8);
			Node queryVal = getQueryValueOneNode(nodes);
			Assert.assertNotNull(queryVal);				
			Assert.assertEquals("NewValue2,NewValue", queryVal.getTextContent());
		}
	}
	
	/**
	 * @param params
	 * @return
	 */
	public Node getQueryValueOneNode(NodeList params) {
		Node node = null;
		for (int ii =0; ii < params.getLength();ii++){
			node = params.item(ii);
			Node name = node.getAttributes().getNamedItem("name");
			if ("Query.fs_012.f1.value1".equals(name.getNodeValue())) {
				return  node;
			}
		}
		return null;
	}

}
