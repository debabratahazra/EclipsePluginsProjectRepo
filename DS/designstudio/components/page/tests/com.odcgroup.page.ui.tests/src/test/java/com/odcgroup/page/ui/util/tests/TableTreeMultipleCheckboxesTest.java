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
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.widgets.table.ITableColumn;
import com.odcgroup.page.ui.edit.WidgetEditPart;
import com.odcgroup.page.ui.editor.DesignEditor;
import com.odcgroup.page.ui.figure.table.TableFigure;
import com.odcgroup.page.ui.tests.util.PageUiAssert;

/**
 * @author pkk
 *
 */
public class TableTreeMultipleCheckboxesTest extends AbstractPageDesignerUnitTest {

	private static final String DOMAIN_MODEL = "domain/ds3458/DS3458.domain";
	private static final String MODULE_MODEL = "module/widget/tabletree/DS4166.module";

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
	public void testDS4166MultiCheckboxTests() throws PartInitException {
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
	        /*
			rep.getViewer().select(tableep);
			String expectedErrorMsg = "Filter on table is not allowed " +
					"when Placeholder column with checkbox is designed";
			IStatus status = validateModel(tableep.getModel());
			Assert.assertTrue(!status.isOK());
			Assert.assertEquals(expectedErrorMsg, status.getMessage());
			*/
			
			List<?> tableChildren = tableep.getChildren();
			Assert.assertFalse("Table Widget has no columns", tableChildren.isEmpty());
			Assert.assertEquals("Table columns is not equal to 3", 3, tableChildren.size());
			WidgetEditPart tcep = (WidgetEditPart) tableChildren.get(0);
			ITableColumn pcolumn = TableHelper.getTableColumn(tcep.getWidget());
			Assert.assertTrue(pcolumn.isPlaceHolder() && (pcolumn.getCheckbox()!= null));
			rep.getViewer().select(tcep);
			String expectedErrorMsg = "Placeholder column [placeholder_n_t56] cannot " +
					"use Display grouping and checkbox together";
			IStatus status = validateModel(tcep.getModel());
			Assert.assertTrue(!status.isOK());
			Assert.assertEquals(expectedErrorMsg, status.getMessage());
		}
	}


}
