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

public class CodeWidgetPropertySaveTest extends AbstractPageDesignerUnitTest {

	private static final String FRAGMENT_WIDGET = "fragment/ds5047/DS5047Fragment.fragment";
	private static final String MODULE_WIDGET = "module/Default/module/DS5047Module.module";

	@Before
	public void setUp() throws Exception {
		createModelsProject();
		copyResourceInModelsProject(FRAGMENT_WIDGET, MODULE_WIDGET);
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
		setCodeProperty(ep);
	}

	/**
	 * @throws PartInitException
	 */
	@Test
	public void testDS5047FragmentCodeWidgetPropertySave() throws PartInitException {
		IFile fragmentFile = getProject().getFile(FRAGMENT_WIDGET);
		Assert.assertTrue(fragmentFile.exists());
		IEditorPart ep = openDefaultEditor(fragmentFile);
		setCodeProperty(ep);
	}
	
	/**
	 * @param ep
	 */
	private void setCodeProperty(IEditorPart ep) {
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
			WidgetEditPart codeWidget = (WidgetEditPart) bep.getChildren().get(0);
			Assert.assertNotNull("code widget widget not found", codeWidget);
			
			Widget widget = codeWidget.getWidget();
			Assert.assertNotNull(widget);
			Assert.assertEquals("True", widget.getPropertyValue(PropertyTypeConstants.CODE));
			widget.setPropertyValue(PropertyTypeConstants.CODE, "false");
			Assert.assertEquals("false", widget.getPropertyValue(PropertyTypeConstants.CODE));
		}
	}

}
