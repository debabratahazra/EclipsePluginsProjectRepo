package com.odcgroup.page.ui.util.tests;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPart;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.MatrixHelper;
import com.odcgroup.page.model.widgets.matrix.IMatrixContentCell;
import com.odcgroup.page.model.widgets.matrix.IMatrixContentCellItem;
import com.odcgroup.page.ui.edit.WidgetEditPart;
import com.odcgroup.page.ui.edit.matrix.MatrixWidgetEditPart;
import com.odcgroup.page.ui.figure.matrix.MatrixAxisFigure;
import com.odcgroup.page.ui.figure.matrix.MatrixCellFigure;
import com.odcgroup.page.ui.figure.matrix.MatrixContentCellFigure;
import com.odcgroup.page.ui.figure.matrix.MatrixFigure;

/**
 *
 * @author pkk
 */
public class PageDesignerMatrixWidgetTest extends BasePageDesignerTest {
	
	private WidgetEditPart matrixEditPart = null;
	private IFile targetFile = null;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		targetFile = createModuleFile("module/Default/module/", "TestModule");
	}
	

	@After
	public void tearDown() throws Exception {
		setMatrixEditPart(null);
		setModuleEditPart(null);
		setEditorPart(null);
		super.tearDown();
	}
	
	private void createMatrixWidget() {
		if (targetFile != null) {
			openModuleEditor(targetFile);
			WidgetEditPart mep = getModuleEditPart();
			Assert.assertNotNull("Root WidgetEditPart is not found", mep);
			List<?> children = mep.getChildren();
			Assert.assertFalse("Root Widget has no children", children.isEmpty());
			WidgetEditPart bep = (WidgetEditPart) children.get(0);
			
			// get matrix template
			WidgetEditPart wep = createWidget("Matrix", bep);
			Assert.assertNotNull(wep);
			Assert.assertTrue(wep instanceof MatrixWidgetEditPart);
			wep.setSelected(EditPart.SELECTED);
			setMatrixEditPart(wep);
		}
	}

	@Test
	public void testMatrixWidgetNonRegression() throws Exception {	
		createMatrixWidget();
		IFigure figure = getMatrixEditPart().getFigure();
		Assert.assertTrue(figure instanceof MatrixFigure);
		MatrixFigure mFigure = (MatrixFigure) figure;
		List<?> mChildren = mFigure.getChildren();
		Assert.assertEquals(7, mChildren.size());
		
		MatrixAxisFigure xAxis = null;
		MatrixContentCellFigure contentCell = null;
		MatrixCellFigure lastRow = null;
		for (Object object : mChildren) {
			IFigure fig = (IFigure) object;
			if (fig instanceof MatrixAxisFigure) {
				MatrixAxisFigure aFig = (MatrixAxisFigure) fig;
				if(aFig.getMatrixAxis().isXAxis()) {
					xAxis = aFig;
				}
			} else if (fig instanceof MatrixContentCellFigure) {
				contentCell = (MatrixContentCellFigure) fig;
			} else if (fig instanceof MatrixCellFigure) {
				MatrixCellFigure cell = (MatrixCellFigure) fig;
				if(cell.getMatrixCell().isLastRow()) {
					lastRow = cell;
				}
			}
		}
		Assert.assertNotNull(xAxis);
		Assert.assertNotNull(contentCell);
		
		//DS-3630 Matrix contents alignment
		Assert.assertEquals(xAxis.getBounds().x, contentCell.getBounds().x);
		Assert.assertEquals(xAxis.getBounds().x, lastRow.getBounds().x);	
		
		//DS-3704 check for properties id, documentation
		Widget xAxisWidget = xAxis.getWidget();
		Property xAxisIDProp = xAxisWidget.findProperty(PropertyTypeConstants.ID);
		Assert.assertNull("Property ID for "+xAxisWidget.getTypeName()+" is not NULL", xAxisIDProp);
		Assert.assertEquals("Widget ID value for "+xAxisWidget.getTypeName()+" should be empty", "", xAxisWidget.getID());
		
		//DS-3802 preview property
		Property previewProp = xAxisWidget.findProperty(PropertyTypeConstants.PREVIEW_VALUE);
		Assert.assertNull("Property previewValue found for axis", previewProp);
		
		//DS-3722
		Widget cell = contentCell.getWidget();
		Property cellIDProp = cell.findProperty(PropertyTypeConstants.ID);
		Assert.assertNull("Property ID for "+cell.getTypeName()+" is not NULL", cellIDProp);
		Assert.assertEquals("Widget ID value for "+cell.getTypeName()+" should be empty", "", cell.getID());
		String doc = cell.getPropertyValue(PropertyTypeConstants.DOCUMENTATION);
		Assert.assertNull("Property Documentation for "+cell.getTypeName()+" is not NULL", doc);
		
		WidgetEditPart cep = getContentCellEditPart();
		Assert.assertNotNull("Content Cell is not found", cep);
		getRootEditPart().getViewer().select(cep);
		
        IMatrixContentCell cCell = MatrixHelper.getMatrixContentCell(cell);
		
		/*
		ISection section = getCurrentTab().getSectionAtIndex(0);
        Assert.assertTrue(section instanceof MatrixCssClassPropertySection);
        MatrixCssClassPropertySection is = (MatrixCssClassPropertySection) section;
        is.refresh();
        saveDiagram();
        
        String value = cCell.getCssClass().getCssClassType().getValue();
        Assert.assertTrue("ContentCell CSS default classtype is not corporate", "corporate".equals(value));
        */
		
        //DS-3803
        List<IMatrixContentCellItem> cItems = cCell.getComputedItems();
        String columnName = null;
        for (IMatrixContentCellItem item : cItems) {
			columnName = item.getWidget().getPropertyValue("column-name");
			Assert.assertFalse("column name is not found for matrix content item", StringUtils.isEmpty(columnName));
		}
        
		
	}
	
	private WidgetEditPart getContentCellEditPart() {
		List<?> children = getMatrixEditPart().getChildren();
		for (Object object : children) {
			WidgetEditPart ep = (WidgetEditPart) object;
			if (ep.getFigure() instanceof MatrixContentCellFigure) {
				return ep;
			}
		}
		return null;
	}

	/**
	 * @return the matrixEditPart
	 */
	public WidgetEditPart getMatrixEditPart() {
		return matrixEditPart;
	}

	/**
	 * @param matrixEditPart the matrixEditPart to set
	 */
	public void setMatrixEditPart(WidgetEditPart matrixEditPart) {
		this.matrixEditPart = matrixEditPart;
	}

}
