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
import com.odcgroup.page.ui.edit.WidgetEditPart;
import com.odcgroup.page.ui.editor.DesignEditor;
import com.odcgroup.page.ui.figure.table.TableFigure;

/**
 * DS-3731
 *
 * @author pkk
 *
 */
public class TableGroupSortColumnConstraintTest extends AbstractPageDesignerUnitTest {

	private static final String DOMAIN_MODEL = "domain/ds3553/DS3553.mml";
	private static final String MODULE_MODEL = "module/Default/module/DS3731.module";

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
	public void testDS3731TableColumnGroupSortColumnConstraint() throws PartInitException {
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
		        flushEventQueue();
		        
				rep.getViewer().select(tableep);
				
				String expectedErrorMsg = "The sorting column of a grouping has to be " +
						"added in the table (we recommend to add it as a Table Extra)";
				IStatus status = validateModel(tableep.getModel());
				Assert.assertTrue(!status.isOK());
				Assert.assertEquals(expectedErrorMsg, status.getMessage());
				
			}
		}
	}

}
