package com.odcgroup.page.ui.util.tests;

import java.util.List;

import org.eclipse.core.resources.IFile;
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

import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.widgets.table.ITableColumn;
import com.odcgroup.page.model.widgets.table.ITableGroup;
import com.odcgroup.page.ui.edit.WidgetEditPart;
import com.odcgroup.page.ui.editor.DesignEditor;
import com.odcgroup.page.ui.figure.table.TableFigure;
import com.odcgroup.page.ui.tests.util.PageUiAssert;

public class TableGroupTest extends AbstractPageDesignerUnitTest {

	private static final String DOMAIN_MODEL = "domain/ds4989/DS4989.domain";
	private static final String MODULE_MODEL = "module/Default/module/DS4989.module";	

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
	public void testDS4989TableGroupTest() throws PartInitException {
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
			
			IWorkbenchPage page = ep.getSite().getPage();
	        page.saveEditor(ep, false);
	        			
			List<?> tableChildren = tableep.getChildren();
			Assert.assertFalse("Table Widget has no columns", tableChildren.isEmpty());
			Assert.assertEquals("Table columns is not equal to 3", 3, tableChildren.size());
			WidgetEditPart tcep = (WidgetEditPart) tableChildren.get(1);
			ITableColumn pcolumn = TableHelper.getTableColumn(tcep.getWidget());
			Assert.assertTrue(pcolumn.isPlaceHolder() && (pcolumn.getGroups()!= null));
			List<ITableGroup> groups = pcolumn.getGroups();
			ITableGroup group1 = groups.get(0);
			Assert.assertTrue("Group1 can not move down", group1.canMoveDown());
			Assert.assertFalse("Group1 can move up", group1.canMoveUp());
			Assert.assertTrue("Group1 can not move left", group1.canMoveLeft());
			Assert.assertTrue("Group1 can not move right", group1.canMoveRight());
			
			ITableGroup group2 = groups.get(1);
			Assert.assertTrue("Group2 can not move down", group2.canMoveUp());
			Assert.assertFalse("Group2 can move down", group2.canMoveDown());
			Assert.assertTrue("Group2 can not move left", group2.canMoveLeft());
			Assert.assertTrue("Group2 can not move right", group2.canMoveRight());
			rep.getViewer().select(tcep);
		}
	}
}

