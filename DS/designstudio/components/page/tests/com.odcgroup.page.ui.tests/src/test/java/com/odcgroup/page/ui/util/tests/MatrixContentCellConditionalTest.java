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

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.util.MatrixHelper;
import com.odcgroup.page.model.widgets.matrix.IMatrix;
import com.odcgroup.page.model.widgets.matrix.IMatrixContentCellItem;
import com.odcgroup.page.ui.edit.WidgetEditPart;
import com.odcgroup.page.ui.editor.DesignEditor;
import com.odcgroup.page.ui.figure.conditional.ConditionalFigure;
import com.odcgroup.page.ui.figure.matrix.MatrixContentCellFigure;
import com.odcgroup.page.ui.figure.matrix.MatrixContentCellItemFigure;
import com.odcgroup.page.ui.figure.matrix.MatrixFigure;
import com.odcgroup.page.ui.tests.util.PageUiAssert;

/**
 * DS-3911 ConditionalWidget in MatrixContentCell Tests
 *
 * @author pkk
 *
 */
public class MatrixContentCellConditionalTest extends AbstractPageDesignerUnitTest {

	private static final String DOMAIN_MODEL = "domain/ds3714/SimpleData.domain";
	private static final String MODULE_MODEL = "module/Default/module/DS3911.module";

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
	public void testDS3911ContentCellConditionalWidget() throws PartInitException {
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
			
			IMatrix matrix = ((MatrixFigure) mfig).getMatrix();
			matrix.getWidget().setPropertyValue(PropertyTypeConstants.DOCUMENTATION, "xx");
			IWorkbenchPage page = ep.getSite().getPage();
	        page.saveEditor(ep, false);
	        flushEventQueue();
	        
			WidgetEditPart exep = getMatrixContentCell(matrixep);
			Assert.assertNotNull("Matrix ContentCell is not found", exep);
			List<?> exchildren = exep.getChildren();
			Assert.assertTrue(exchildren.size() == 1);
			WidgetEditPart box = (WidgetEditPart)exchildren.get(0);
			WidgetEditPart cep = getConditionalWidget(box);
			Assert.assertNotNull("ConditionalWidget not found", cep);
			Assert.assertTrue(cep.getChildren().size() == 2);
			WidgetEditPart cb = (WidgetEditPart) cep.getChildren().get(1);
			WidgetEditPart ciep = getMatrixContentCellItem(cb);
			Assert.assertNotNull("MatrixContentCellItem not found", ciep);
			IMatrixContentCellItem item = MatrixHelper.getMatrixContentCellItem(ciep.getWidget());
			Assert.assertNotNull(item);
			Assert.assertTrue(item.isComputed());				
		}
	}
	
	private WidgetEditPart getMatrixContentCellItem(WidgetEditPart ep) {
		List<?> children = ep.getChildren();
		for (Object object : children) {
			WidgetEditPart cep = (WidgetEditPart) object;
			if (cep.getFigure() instanceof MatrixContentCellItemFigure) {
				return cep;
			}
		}
		return null;		
	}
	
	/**
	 * @param ep
	 * @return
	 */
	private WidgetEditPart getConditionalWidget(WidgetEditPart ep) {
		List<?> children = ep.getChildren();
		for (Object object : children) {
			WidgetEditPart cep = (WidgetEditPart) object;
			if (cep.getFigure() instanceof ConditionalFigure) {
				return cep;
			}
		}
		return null;
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

}
