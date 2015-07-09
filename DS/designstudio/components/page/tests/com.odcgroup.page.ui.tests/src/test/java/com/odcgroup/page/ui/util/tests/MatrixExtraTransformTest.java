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

/**
 * DS-3762
 *
 * @author pkk
 *
 */
public class MatrixExtraTransformTest extends AbstractPageDesignerUnitTest {

	private static final String DOMAIN_MODEL = "domain/ds3714/SimpleData.mml";
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
	 * @throws PartInitException
	 */
	@Test
	public void testDS3761MaxGroupings() throws PartInitException {
		IFile moduleFile = getProject().getFile(MODULE_MODEL);
		Assert.assertTrue(moduleFile.exists());
		IEditorPart ep = openDefaultEditor(moduleFile);
		if (ep instanceof MultiPageEditorPart) {
			MultiPageEditorPart mep = (MultiPageEditorPart) ep;
			DesignEditor editor = (DesignEditor) mep.getSelectedPage();
			RootEditPart rep = editor.getViewer().getRootEditPart();

			List<?> list = rep.getChildren();
			if (!list.isEmpty()) {
				WidgetEditPart wep = (WidgetEditPart) list.get(0);
				Assert.assertNotNull("Module WidgetEditPart is not found", wep);
				Document document = fetchGenDocument(getOfsProject(), wep.getWidget());
				// max-groupings for x-axis
				NodeList nodes = document.getElementsByTagName("udp:keep");
				Assert.assertTrue(nodes.getLength() == 5);
				Node keep = nodes.item(4);
				Assert.assertNotNull(keep);
				Assert.assertEquals("instrument", keep.getTextContent());
			}
		}

	}

}


