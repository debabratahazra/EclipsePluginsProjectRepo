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

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.edit.WidgetEditPart;
import com.odcgroup.page.ui.editor.DesignEditor;
import com.odcgroup.page.ui.tests.util.PageUiAssert;

public class ConditionalWidgetPropertyTest extends AbstractPageDesignerUnitTest {

	private static final String MODULE_WIDGET = "module/Default/module/DS5596Module.module";

	@Before
	public void setUp() throws Exception {
		createModelsProject();
		copyResourceInModelsProject(MODULE_WIDGET);
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
	public void testDS5047ModuleCodeWidgetPropertySave() throws PartInitException {
		IFile moduleFile = getProject().getFile(MODULE_WIDGET);
		Assert.assertTrue(moduleFile.exists());
		IEditorPart ep = openDefaultEditor(moduleFile);
		setXspProperty(ep);
	}

	/**
	 * @param ep
	 */
	private void setXspProperty(IEditorPart ep) {
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
			WidgetEditPart conditionalWidget = (WidgetEditPart) bep.getChildren().get(0);
			Assert.assertNotNull("code widget widget not found", conditionalWidget);
			
			WidgetEditPart xspConditionalBodyWidget = (WidgetEditPart) conditionalWidget.getChildren().get(0);
			Assert.assertNotNull("code widget widget not found", xspConditionalBodyWidget);
			
			Widget xspWidget = xspConditionalBodyWidget.getWidget();
			Assert.assertNotNull(xspWidget);
			Assert.assertEquals("XSP", xspWidget.getPropertyValue(PropertyTypeConstants.CONDITION_LANG));
			Assert.assertEquals("false", xspWidget.getPropertyValue(PropertyTypeConstants.JAVA_CODE));
			xspWidget.setPropertyValue(PropertyTypeConstants.JAVA_CODE, "true");
			Assert.assertEquals("true", xspWidget.getPropertyValue(PropertyTypeConstants.JAVA_CODE));
			
			
			WidgetEditPart dslConditionalBodyWidget = (WidgetEditPart) conditionalWidget.getChildren().get(1);
			Assert.assertNotNull("code widget widget not found", dslConditionalBodyWidget);
			
			Widget dslWidget = dslConditionalBodyWidget.getWidget();
			Assert.assertNotNull(dslWidget);
			Assert.assertEquals("DSEL", dslWidget.getPropertyValue(PropertyTypeConstants.CONDITION_LANG));
			Assert.assertEquals("true", dslWidget.getPropertyValue(PropertyTypeConstants.JAVA_CODE));
			dslWidget.setPropertyValue(PropertyTypeConstants.JAVA_CODE, "false");
			Assert.assertEquals("false", dslWidget.getPropertyValue(PropertyTypeConstants.JAVA_CODE));
		}
	}

}
