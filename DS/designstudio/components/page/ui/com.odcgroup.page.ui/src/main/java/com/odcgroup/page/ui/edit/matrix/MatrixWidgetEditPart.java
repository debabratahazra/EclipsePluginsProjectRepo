package com.odcgroup.page.ui.edit.matrix;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.EditPart;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.MatrixHelper;
import com.odcgroup.page.model.widgets.matrix.IMatrix;
import com.odcgroup.page.ui.edit.WidgetEditPart;

/**
 *
 * @author pkk
 *
 */
public class MatrixWidgetEditPart extends WidgetEditPart {
	
	/**
	 * @param widget
	 * @return
	 */
	protected IMatrix getMatrix(Widget widget) {
		return MatrixHelper.getMatrix(widget);
	}

	/**
	 * Gets the children of this Widget.
	 * 
	 * @return List The children of this Widget
	 */
	@SuppressWarnings("unchecked")
	protected List getModelChildren() {
		List children = new ArrayList();
		IMatrix matrix = getMatrix(getWidget());
		children.add(matrix.getXAxis().getWidget());
		children.add(matrix.getYAxis().getWidget());
		children.add(matrix.getMatrixCell().getWidget());
		if (matrix.displayLastColumn()) {
			children.add(matrix.getLastColumn().getWidget());
		}
		if (matrix.displayLastRow()) {
			children.add(matrix.getLastRow().getWidget());
		}
		if (matrix.displayLastCell()) {
			children.add(matrix.getLastCell().getWidget());
		}
		children.add(matrix.getExtraColumn().getWidget());		
		return children;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#reorderChild(org.eclipse.gef.EditPart, int)
	 */
	protected void reorderChild(EditPart editpart, int index) {
		removeChildVisual(editpart);
		List children = getChildren();
		children.remove(editpart);
		addChild(editpart, index);
	}

}
