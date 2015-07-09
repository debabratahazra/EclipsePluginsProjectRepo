package com.odcgroup.page.ui.util.tests;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.RootEditPart;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.ui.views.properties.PropertySheet;
import org.eclipse.ui.views.properties.tabbed.ITabDescriptor;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.model.widgets.table.ITableColumn;
import com.odcgroup.page.ui.edit.WidgetEditPart;
import com.odcgroup.page.ui.editor.DesignEditor;
import com.odcgroup.page.ui.figure.table.TableFigure;
import com.odcgroup.page.ui.tests.util.PageUiAssert;
import com.odcgroup.workbench.ui.tests.util.AbstractDSUIUnitTest;

/**
 * DS-3922
 *
 * @author pkk
 *
 */
public class DynamicColumnComputationTabTest extends AbstractDSUIUnitTest {

	private static final String DOMAIN_MODEL = "domain/ds3714/SimpleData.domain";
	private static final String MODULE_MODEL = "module/Default/module/DS3922.module";

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
	public void testDS3905DynamicColumnTranslation() throws PartInitException {
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
			IFigure mfig = tep.getFigure();
			Assert.assertTrue(mfig instanceof TableFigure);				
	        
			ITable table = TableHelper.getTable(tep.getWidget());
			List<String> cols = TableHelper.getDynamicColumnNames(table);
			Assert.assertTrue(cols.size()==1);
			ITableColumn column = table.getColumn(cols.get(0));
			Assert.assertTrue("column is not dynamic", column.isDynamic());
			Assert.assertTrue(tep.getChildren().size() == 3);
			WidgetEditPart dcep = (WidgetEditPart) tep.getChildren().get(2);
			Assert.assertTrue(dcep.getWidget().equals(column.getWidget()));
			rep.getViewer().select(dcep);
			
			IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
	        Assert.assertNotNull(workbenchWindow);
			IViewPart view = workbenchWindow.getActivePage().showView("org.eclipse.ui.views.PropertySheet");
	    	Assert.assertNotNull(view);
	        TabbedPropertySheetPage propertySheetPage = null;
	        if (view instanceof PropertySheet) {
	            PropertySheet ps = (PropertySheet) view;
	            propertySheetPage = (TabbedPropertySheetPage) ps.getCurrentPage();
	            ITabDescriptor[] tabs = propertySheetPage.getActiveTabs();
	            List<String> tabNames = new ArrayList<String>();
	            for (ITabDescriptor tab : tabs) {
					tabNames.add(tab.getLabel());
				}
	            Assert.assertFalse("Computation tab found for the dynamic column", tabNames.contains("Computation"));
	        }
		}
	}
}