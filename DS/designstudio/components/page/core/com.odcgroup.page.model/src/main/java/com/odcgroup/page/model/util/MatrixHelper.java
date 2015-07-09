package com.odcgroup.page.model.util;

import com.odcgroup.page.metamodel.DataType;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.widgets.matrix.IMatrix;
import com.odcgroup.page.model.widgets.matrix.IMatrixAxis;
import com.odcgroup.page.model.widgets.matrix.IMatrixCell;
import com.odcgroup.page.model.widgets.matrix.IMatrixCellItem;
import com.odcgroup.page.model.widgets.matrix.IMatrixComputationItem;
import com.odcgroup.page.model.widgets.matrix.IMatrixContentCell;
import com.odcgroup.page.model.widgets.matrix.IMatrixContentCellItem;
import com.odcgroup.page.model.widgets.matrix.IMatrixExtra;
import com.odcgroup.page.model.widgets.matrix.IMatrixExtraColumn;
import com.odcgroup.page.model.widgets.matrix.IMatrixExtraColumnItem;
import com.odcgroup.page.model.widgets.matrix.IMatrixUtilities;
import com.odcgroup.page.model.widgets.matrix.IStyleProvider;
import com.odcgroup.page.model.widgets.matrix.impl.MatrixUtilities;

/**
 *
 * @author pkk
 *
 */
public class MatrixHelper {
	
	private static final IMatrixUtilities MATRIXUTIL = new MatrixUtilities();
	
	/**
	 * @param widget the widget to be adapted
	 * @return a {@link IMatrix} adapter given a matrix widget
	 * 
	 * @throws IllegalArgumentException if the given widget is not a matrix widget
	 */
	public static IMatrix getMatrix(Widget widget) {
		return MATRIXUTIL.getFactory().adaptMatrixWidget(widget);
	}
	
	/**
	 * @param widget
	 * @return
	 */
	public static IMatrixAxis getMatrixAxis(Widget widget) {
		return MATRIXUTIL.getFactory().adaptMatrixAxisWidget(widget);
	}
	
	/**
	 * @param widget
	 * @return
	 */
	public static IMatrixCell getMatrixCell(Widget widget) {
		return MATRIXUTIL.getFactory().adaptMatrixCellWidget(widget);
	}
	
	/**
	 * @param widget
	 * @return
	 */
	public static IMatrixContentCell getMatrixContentCell(Widget widget) {
		return MATRIXUTIL.getFactory().adaptMatrixContentCellWidget(widget);
	}
	
	/**
	 * @param widget
	 * @return
	 */
	public static IMatrixContentCellItem getMatrixContentCellItem(Widget widget) {
		return MATRIXUTIL.getFactory().adaptMatrixContentCellItemWidget(widget);
	}
	
	/**
	 * @param widget
	 * @return
	 */
	public static IMatrixComputationItem getMatrixComputationItem(Widget widget) {
		if (widget.getTypeName().equals(WidgetTypeConstants.MATRIX_CONTENTCELLITEM)) {
			return MATRIXUTIL.getFactory().adaptMatrixContentCellItemWidget(widget);
		} else {
			throw new IllegalArgumentException("This is not a MatrixComputation widget");
		}
	}
	
	/**
	 * @param widget
	 * @return
	 */
	public static IMatrixCellItem getMatrixCellItem(Widget widget) { 
		return MATRIXUTIL.getFactory().adaptMatrixCellItemWidget(widget);
	}
	
	/**
	 * @param widget
	 * @return
	 */
	public static IMatrixExtraColumnItem getMatrixExtraColumnItem(Widget widget) {
		return MATRIXUTIL.getFactory().adapatMatrixExtraColumnItemWidget(widget);
	}
	
	/**
	 * @param widget
	 * @return
	 */
	public static IMatrixExtraColumn getMatrixExtraColumn(Widget widget) {
		return MATRIXUTIL.getFactory().adapatMatrixExtraColumnWidget(widget);
	}
	
	/**
	 * @param widget
	 * @return
	 */
	public static IStyleProvider getStyleProvider(Widget widget) {
		String type = widget.getTypeName();
		if(type.equals("MatrixContentCell")) {
			return MATRIXUTIL.getFactory().adaptMatrixContentCellWidget(widget);
		} else if(type.equals("MatrixCellItem")) {
			return MATRIXUTIL.getFactory().adaptMatrixCellItemWidget(widget);		
		}
		return null;
	}
	
	/**
	 * @return
	 */
	public static DataType getCssClassTypes() {
		return MATRIXUTIL.getCssClassType();
	}
	
	/**
	 * @return
	 */
	public static DataType getAggregationTypes() {
		return MATRIXUTIL.getAggregationType();
	}
	
	/**
	 * @return
	 */
	public static IMatrixExtra createMatrixExtra() {
		return MATRIXUTIL.getFactory().createTableExtra();
	}
	
	/**
	 * @return
	 */
	public static Widget getParentMatrixContentCell(Widget parent) {
		String type = parent.getTypeName();
		if (type.equals(WidgetTypeConstants.MATRIX_CONTENTCELL)) {
			return parent;
		} else {
			if (parent.getParent() == null) {
				return null;
			}
			return getParentMatrixContentCell(parent.getParent());
		}
	}

}
