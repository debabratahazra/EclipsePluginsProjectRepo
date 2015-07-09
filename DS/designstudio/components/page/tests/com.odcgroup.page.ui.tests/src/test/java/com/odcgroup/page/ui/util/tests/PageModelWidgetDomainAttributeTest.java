package com.odcgroup.page.ui.util.tests;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.gef.RootEditPart;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.edit.WidgetEditPart;
import com.odcgroup.page.ui.editor.DesignEditor;
import com.odcgroup.page.ui.tests.util.PageUiAssert;

/**
 *
 * @author pkk
 *
 */
public class PageModelWidgetDomainAttributeTest extends AbstractPageDesignerUnitTest {

	private static final String DOMAIN_MODEL = "domain/ds4035/DS4035.domain";
	private static final String MODULE_MODEL = "module/Default/module/DS4035.module";

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
	public void testDS4305WidgetDomainAttribute() throws PartInitException {
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
			WidgetEditPart tep = (WidgetEditPart) bep.getChildren().get(0);
			
			
			tep.getWidget().setPropertyValue(PropertyTypeConstants.DOCUMENTATION, "xx");
			IWorkbenchPage page = ep.getSite().getPage();
	        page.saveEditor(ep, false);
//	        flushEventQueue();
	        
	        Assert.assertTrue(tep.getChildren().size() == 3);
			Widget column = ((WidgetEditPart)tep.getChildren().get(1)).getWidget();			
			
			String expectedErrorMsg = "The referenced attribute TEST_DURATION is not found in Dataset DS4035:DS4035DS";
			IStatus status = validateModel(column);
			Assert.assertTrue(!status.isOK());
			Assert.assertEquals(expectedErrorMsg, status.getMessage());
			
		}
	}
}
