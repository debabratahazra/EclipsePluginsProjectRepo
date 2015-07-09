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
import com.odcgroup.page.ui.edit.WidgetEditPart;
import com.odcgroup.page.ui.editor.DesignEditor;
import com.odcgroup.page.ui.tests.util.PageUiAssert;

/**
 * DS-3173
 *
 * @author pkk
 *
 */
public class FragmentDefaultDSTest extends AbstractPageDesignerUnitTest {

	private static final String DOMAIN_MODEL = "domain/ds3173/DS3173.domain";
	private static final String FRAGMENT_MODEL_ONE = "fragment/Default/DS3173/DS3173First.fragment";
	private static final String FRAGMENT_MODEL_TWO = "fragment/Default/DS3173/DS3173Second.fragment";
	private static final String FRAGMENT_MODEL_THREE = "fragment/Default/DS3173/DS3173Third.fragment";

    @Before
	public void setUp() throws Exception {
    	createModelsProject();
        copyResourceInModelsProject(DOMAIN_MODEL, FRAGMENT_MODEL_ONE, FRAGMENT_MODEL_TWO, FRAGMENT_MODEL_THREE);
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
	public void testDS3173FragmentDefaultWithCardinalityMany() throws PartInitException {
		IFile fragmentFile = getProject().getFile(FRAGMENT_MODEL_ONE);
		Assert.assertTrue(fragmentFile.exists());
		IEditorPart ep = openDefaultEditor(fragmentFile);
		PageUiAssert.assertInstanceOfMultiPageEditorPart(ep);
		MultiPageEditorPart mep = (MultiPageEditorPart) ep;
		DesignEditor editor = (DesignEditor) mep.getSelectedPage();
		RootEditPart rep = editor.getViewer().getRootEditPart();
		List<?> list = rep.getChildren();
		if (!list.isEmpty()) {
			WidgetEditPart wep = (WidgetEditPart) list.get(0);
			Assert.assertNotNull("Fragment WidgetEditPart is not found", wep);
			List<?> children = wep.getChildren();
			Assert.assertFalse("Fragment Widget has no children", children.isEmpty());
			
			String cardinality = wep.getWidget().getPropertyValue(PropertyTypeConstants.CARDINALITY);
			Assert.assertTrue("Cardinality is one, should be many", PropertyTypeConstants.CARDINALITY_MANY.equals(cardinality));
			Assert.assertTrue("Not a default fragment", "true".equals(wep.getWidget().getPropertyValue(PropertyTypeConstants.DEFAULT_FRAGMENT)));
			wep.getWidget().setPropertyValue(PropertyTypeConstants.DOCUMENTATION, "xx");
			IWorkbenchPage page = ep.getSite().getPage();
	        page.saveEditor(ep, false);
	        flushEventQueue();
	        
			rep.getViewer().select(wep);				
			IStatus status = validateModel(wep.getModel());
			Assert.assertTrue(status.isOK());				
		}
		
		// open second default fragment
		fragmentFile = getProject().getFile(FRAGMENT_MODEL_TWO);
		Assert.assertTrue(fragmentFile.exists());
		IEditorPart oep = openDefaultEditor(fragmentFile);
		Assert.assertTrue(oep instanceof MultiPageEditorPart);
		MultiPageEditorPart mep2 = (MultiPageEditorPart) oep;
		DesignEditor editor2 = (DesignEditor) mep2.getSelectedPage();
		RootEditPart rep2 = editor2.getViewer().getRootEditPart();
		List<?> list2 = rep2.getChildren();
		if (!list2.isEmpty()) {
			WidgetEditPart wep = (WidgetEditPart) list2.get(0);
			Assert.assertNotNull("Fragment WidgetEditPart is not found", wep);
			List<?> children = wep.getChildren();
			Assert.assertFalse("Fragment Widget has no children", children.isEmpty());
			
			String cardinality = wep.getWidget().getPropertyValue(PropertyTypeConstants.CARDINALITY);
			Assert.assertTrue("Cardinality is many, should be one", PropertyTypeConstants.CARDINALITY_ONE.equals(cardinality));
			Assert.assertTrue("Not a default fragment", "true".equals(wep.getWidget().getPropertyValue(PropertyTypeConstants.DEFAULT_FRAGMENT)));
			wep.getWidget().setPropertyValue(PropertyTypeConstants.DOCUMENTATION, "xx");
			IWorkbenchPage page = ep.getSite().getPage();
	        page.saveEditor(ep, false);
	        flushEventQueue();

			String expectedErrorMsg = "A default fragment with the same domain entity [DS3173:DefaultDS] is already defined";
			rep2.getViewer().select(wep);				
			IStatus status = validateModel(wep.getModel());
			Assert.assertTrue(!status.isOK());	
			Assert.assertEquals(expectedErrorMsg, status.getMessage());			
		}
	}

}
