package com.odcgroup.page.ui.util.tests;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.draw2d.IFigure;
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
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.filter.CheckboxPropertyFilter;
import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.widgets.table.ITableColumn;
import com.odcgroup.page.ui.edit.WidgetEditPart;
import com.odcgroup.page.ui.editor.DesignEditor;
import com.odcgroup.page.ui.figure.table.TableFigure;
import com.odcgroup.page.ui.tests.util.PageUiAssert;

public class TableColumnCheckboxTest extends AbstractPageDesignerUnitTest {

	private static final String DOMAIN_MODEL = "domain/ds4405/DS4405.domain";
	private static final String MODULE_MODEL = "module/Default/module/DS4949.module";
	private static final String MODULE_MODEL2 = "module/Default/module/DS4949ConditionCheckbox.module";

    @Before
	public void setUp() throws Exception {
    	createModelsProject();
        copyResourceInModelsProject(DOMAIN_MODEL, MODULE_MODEL, MODULE_MODEL2);
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
	public void testDS4949CheckboxTest() throws PartInitException {
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
			WidgetEditPart tableep = (WidgetEditPart) bep.getChildren().get(0);
			IFigure mfig = tableep.getFigure();
			Assert.assertTrue(mfig instanceof TableFigure);
			
			Widget widget = ((TableFigure) mfig).getWidget();				
			widget.setPropertyValue(PropertyTypeConstants.DOCUMENTATION, "xx");
			IWorkbenchPage page = ep.getSite().getPage();
	        page.saveEditor(ep, false);
	        			
			List<?> tableChildren = tableep.getChildren();
			Assert.assertFalse("Table Widget has no columns", tableChildren.isEmpty());
			Assert.assertEquals("Table columns is not equal to 1", 1, tableChildren.size());
			WidgetEditPart tcep = (WidgetEditPart) tableChildren.get(0);
			ITableColumn pcolumn = TableHelper.getTableColumn(tcep.getWidget());
			Assert.assertTrue(pcolumn.isPlaceHolder() && (pcolumn.getCheckbox()!= null));
			rep.getViewer().select(tcep);
			IStatus status = validateModel(tcep.getModel());
			Assert.assertTrue(status.isOK());
		}
	}
	
	/**
	 * @throws PartInitException
	 */
	@Test
	public void testDS4949PropertyFilter() throws PartInitException {
		IFile moduleFile = getProject().getFile(MODULE_MODEL2);
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
			WidgetEditPart tableep = (WidgetEditPart) bep.getChildren().get(0);
			IFigure mfig = tableep.getFigure();
			Assert.assertTrue(mfig instanceof TableFigure);
			        			
			List<?> tableChildren = tableep.getChildren();
			Assert.assertFalse("Table Widget has no columns", tableChildren.isEmpty());
			Assert.assertEquals("Table columns is not equal to 1", 1, tableChildren.size());
			WidgetEditPart tcep = (WidgetEditPart) tableChildren.get(0);
			ITableColumn pcolumn = TableHelper.getTableColumn(tcep.getWidget());
			Assert.assertTrue(pcolumn.isPlaceHolder() && (pcolumn.getCheckbox()!= null));
			Widget widget = pcolumn.getCheckbox();
			Assert.assertTrue("checkbox widget not found in tablecolumn", widget != null);
			
			CheckboxPropertyFilter filter = new CheckboxPropertyFilter();
			List<Property> properties = filter.filter(widget);
			Property daprop = getDomainAttributeProperty(properties);
			Assert.assertNull("domain attribute property not filtered", daprop);
		}
	}
	
	/**
	 * @param properties
	 * @return
	 */
	private Property getDomainAttributeProperty(List<Property> properties) {
		for (Property property : properties) {
			if(property.getTypeName().equals(PropertyTypeConstants.DOMAIN_ATTRIBUTE)) {
				return property;
			}
		}
		return null;
	}


}

