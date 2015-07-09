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
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.widgets.matrix.IMatrix;
import com.odcgroup.page.ui.edit.WidgetEditPart;
import com.odcgroup.page.ui.editor.DesignEditor;
import com.odcgroup.page.ui.figure.matrix.MatrixContentCellFigure;
import com.odcgroup.page.ui.figure.matrix.MatrixFigure;
import com.odcgroup.page.ui.properties.table.TableColumnAttributePropertySourceAdapter;
import com.odcgroup.page.ui.tests.util.PageUiAssert;

/**
 * DS-3809
 *
 * @author pkk
 *
 */
public class MatrixContentCellItemFormatTest extends AbstractPageDesignerUnitTest {

	private static final String DOMAIN_MODEL = "domain/ds3714/SimpleData.domain";
	private static final String MODULE_MODEL = "module/Default/module/DS3804.module";

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
	public void testDS3809ContentCellItemFormat() throws PartInitException {
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
			WidgetEditPart item = (WidgetEditPart)exchildren.get(0);
			rep.getViewer().select(item);
			
			Widget cellItem = item.getWidget();
			List<Property> properties = cellItem.getProperties();
			Assert.assertNull("Property [type] found for matrix contentcell item", getProperty(properties, "type"));
			Assert.assertNotNull("Property [format] not found for matrix contentcell item", getProperty(properties, PropertyTypeConstants.FORMAT));
			
		}
	}
	
	/**
	 * @param properties
	 * @param typeName
	 * @return
	 */
	private Property getProperty(List<Property> properties, String typeName) {
		for (Property property : properties) {
			if (typeName.equals(property.getTypeName())) {
				return property;
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
     
	@Test
	public void testDS5142setColumnNametoMatrixContentCellItem(){
	    copyResourceInModelsProject("module/Default/module/DS5142.module");
	    IFile moduleFile = getProject().getFile("module/Default/module/DS5142.module");
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
			
			((MatrixFigure) mfig).getMatrix();
	        
			WidgetEditPart exep = getMatrixContentCell(matrixep);
			Assert.assertNotNull("Matrix ContentCell is not found", exep);
			List<?> exchildren = exep.getChildren();
			Assert.assertTrue(exchildren.size() == 2);
			WidgetEditPart item = (WidgetEditPart)exchildren.get(1);
			rep.getViewer().select(item);
			Widget cellItem = item.getWidget();
			Assert.assertNotNull("Property [column name] found for matrix contentcell item",cellItem.findProperty("column-name") );
			Property namePro=cellItem.findProperty("column-name");
			Assert.assertNotNull(namePro);
			TableColumnAttributePropertySourceAdapter tableSource=new TableColumnAttributePropertySourceAdapter(namePro,item.getCommandStack());
			tableSource.setPropertyValue("newdsFix");
			item.getCommandStack().markSaveLocation();
			String newValue=cellItem.getPropertyValue("column-name");
			Assert.assertEquals("newdsFix", newValue);
			
			
		}	
	    
	}
}

