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

import com.odcgroup.page.ui.edit.WidgetEditPart;
import com.odcgroup.page.ui.editor.DesignEditor;
import com.odcgroup.page.ui.figure.matrix.MatrixExtraColumnFigure;
import com.odcgroup.page.ui.tests.util.PageUiAssert;
import com.odcgroup.workbench.ui.tests.util.AbstractDSUIUnitTest;

/**
 *
 * @author pkk
 *
 */
public class MatrixExtraColumnTest extends AbstractDSUIUnitTest {

	private static final String DOMAIN_MODEL = "domain/ds3714/SimpleData.domain";
	private static final String MODULE_MODEL = "module/Default/module/DS3714_MatrixExtraColumn.module";

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
	public void testDS3714ColumnsInMatrixExtraColumn() throws PartInitException {
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
			WidgetEditPart exep = getExtraColumnWidget(matrixep);
			Assert.assertNotNull("Matrix ExtraColumn is not found", exep);
			List<?> exchildren = exep.getChildren();
			Assert.assertTrue(exchildren.size() == 2);
			WidgetEditPart col1 = (WidgetEditPart)exchildren.get(0);
			WidgetEditPart col2 = (WidgetEditPart)exchildren.get(1);
			col1.getFigure().getBounds();
			col2.getFigure().getBounds();
			//Assert.assertEquals("Columns in ExtraColumn are horizontally aligned", cb1.y, cb2.y);
			//Assert.assertEquals("Columns in ExtraColumn are horizontally aligned", cb1.height, cb2.height);
		}
	}
	
	/**
	 * @param matrix
	 * @return
	 */
	private WidgetEditPart getExtraColumnWidget(WidgetEditPart matrix) {
		List<?> children = matrix.getChildren();
		for (Object object : children) {
			WidgetEditPart ep = (WidgetEditPart) object;
			if (ep.getFigure() instanceof MatrixExtraColumnFigure) {
				return ep;
			}
		}
		return null;
	}

}
