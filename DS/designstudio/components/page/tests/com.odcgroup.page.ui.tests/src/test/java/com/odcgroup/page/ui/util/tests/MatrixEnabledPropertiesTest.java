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
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.edit.WidgetEditPart;
import com.odcgroup.page.ui.editor.DesignEditor;
import com.odcgroup.page.ui.figure.matrix.MatrixAxisFigure;
import com.odcgroup.page.ui.figure.matrix.MatrixContentCellFigure;
import com.odcgroup.page.ui.tests.util.PageUiAssert;
import com.odcgroup.workbench.ui.tests.util.AbstractDSUIUnitTest;

/**
 *
 * @author pkk
 *
 */
public class MatrixEnabledPropertiesTest extends AbstractDSUIUnitTest {

	private static final String DOMAIN_MODEL = "domain/ds3714/SimpleData.domain";
	private static final String MODULE_MODEL = "module/Default/module/DS3772.module";

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
	public void testDS3770EnabledProperties() throws PartInitException {
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
			Assert.assertNotNull("Matrix widget not found", matrixep);
			
			// x-axis
			WidgetEditPart aep = getMatrixAxisWidget(matrixep, true);
			Assert.assertNotNull("Matrix x-axis not found", aep);	
			rep.getViewer().select(aep);
			Widget axisWidget = aep.getWidget();
			List<Property> properties = axisWidget.getProperties();
			Assert.assertNull("enabled property is found on x-axis", getProperty(properties, PropertyTypeConstants.ENABLED));
			Assert.assertNull("enabledIsBasedOn property is found on x-axis", getProperty(properties, "enabledIsBasedOn"));
			
			WidgetEditPart cep = getMatrixContentCell(matrixep);
			Assert.assertTrue(cep.getChildren().size() == 1);
			WidgetEditPart itemep = (WidgetEditPart) cep.getChildren().get(0);
			rep.getViewer().select(itemep);
			Widget itemWidget = itemep.getWidget();
			List<Property> itemProp = itemWidget.getProperties();
			Assert.assertNotNull("enabled property is not found on matrixcontentcellitem", getProperty(itemProp, PropertyTypeConstants.ENABLED));
			Assert.assertNotNull("enabled property is not found on matrixcontentcellitem", getProperty(itemProp, "enabledIsBasedOn"));
			
		}
	}
	
	/**
	 * @param properties
	 * @param type
	 * @return
	 */
	private Property getProperty(List<Property> properties, String type) {
		for (Property property : properties) {
			if (property.getTypeName().equals(type)) {
				return property;
			}
		}
		return null;
	}
	
	/**
	 * @param matrix
	 * @return
	 */
	private WidgetEditPart getMatrixAxisWidget(WidgetEditPart matrix, boolean xAxis) {
		List<?> children = matrix.getChildren();
		for (Object object : children) {
			WidgetEditPart ep = (WidgetEditPart) object;
			if (ep.getFigure() instanceof MatrixAxisFigure) {
				MatrixAxisFigure axisfig = (MatrixAxisFigure) ep.getFigure();
				if (xAxis && axisfig.getMatrixAxis().isXAxis()) {
					return ep;
				} else if (!xAxis && axisfig.getMatrixAxis().isYAxis()) {
					return ep;
				}
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
