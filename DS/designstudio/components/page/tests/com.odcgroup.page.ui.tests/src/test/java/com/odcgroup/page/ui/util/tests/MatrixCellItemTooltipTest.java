package com.odcgroup.page.ui.util.tests;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.RootEditPart;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.page.model.util.MatrixHelper;
import com.odcgroup.page.model.widgets.matrix.IMatrixContentCellItem;
import com.odcgroup.page.ui.edit.WidgetEditPart;
import com.odcgroup.page.ui.editor.DesignEditor;
import com.odcgroup.page.ui.figure.matrix.MatrixContentCellFigure;
import com.odcgroup.page.ui.figure.matrix.MatrixContentCellItemFigure;
import com.odcgroup.page.ui.figure.matrix.MatrixFigure;
import com.odcgroup.page.ui.tests.util.PageUiAssert;
import com.odcgroup.workbench.ui.tests.util.AbstractDSUIUnitTest;

/**
 * DS-3787
 * @author pkk
 *
 */
public class MatrixCellItemTooltipTest extends AbstractDSUIUnitTest {

	private static final String DOMAIN_MODEL = "domain/ds3714/SimpleData.domain";
	private static final String MODULE_MODEL = "module/Default/module/DS3787.module";

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
	public void testDS3737ComputationItemMakeAmountParameters() throws PartInitException {
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
			WidgetEditPart matrixep = (WidgetEditPart) bep.getChildren().get(0);
			IFigure mfig = matrixep.getFigure();
			Assert.assertTrue(mfig instanceof MatrixFigure);				
	        
			WidgetEditPart exep = getMatrixContentCell(matrixep);
			Assert.assertNotNull("Matrix ContentCell is not found", exep);
			rep.getViewer().select(exep);

			Assert.assertTrue("No items were found in MatrixContentCell", !exep.getChildren().isEmpty());
			WidgetEditPart itemep = getMatrixContentCellItem(exep, 0);
			Assert.assertNotNull("MatrixContentCellItem is not found", itemep);
			IMatrixContentCellItem item = MatrixHelper.getMatrixContentCellItem(itemep.getWidget());
			Assert.assertFalse("Tooltip not found", item.getWidget().getToolTips().isEmpty());
		}
	}
	
	/**
	 * @param matrix
	 * @return
	 */
	private WidgetEditPart getMatrixContentCell(WidgetEditPart matrix) {
		List<?> children = matrix.getChildren();
		for (Object object : children) {
			WidgetEditPart ep = (WidgetEditPart) object;
			if (ep.getFigure() instanceof MatrixContentCellFigure) {
				return ep;
			}
		}
		return null;
	}
	
	/**
	 * @param matrixContentCell
	 * @param index
	 * @return
	 */
	private WidgetEditPart getMatrixContentCellItem(WidgetEditPart matrixContentCell, int index) {
		List<?> children = matrixContentCell.getChildren();
		Object obj = children.get(index);
		if(obj instanceof WidgetEditPart) {
			WidgetEditPart ep = (WidgetEditPart) obj;
			if (ep.getFigure() instanceof MatrixContentCellItemFigure) {
				return ep;
			}
		}
		return null;
		
	}

}
