package com.odcgroup.page.ui.figure.matrix;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.MatrixHelper;
import com.odcgroup.page.model.widgets.matrix.IMatrix;
import com.odcgroup.page.model.widgets.matrix.IMatrixCell;
import com.odcgroup.page.ui.PageUIPlugin;
import com.odcgroup.page.ui.figure.AbstractWidgetFigure;
import com.odcgroup.page.ui.figure.BoxFigure;
import com.odcgroup.page.ui.figure.FigureConstants;
import com.odcgroup.page.ui.figure.FigureContext;

/**
 *
 * @author pkk
 *
 */
public class MatrixFigure extends BoxFigure {
	
	/** The border Color. */
	private Color borderColor;
        private static final String MATRIX_FIGURE_BORDER_COLOR = "MATRIX_FIGURE_BORDER_COLOR";
	/**
	 * @param widget
	 * @param figureContext
	 */
	public MatrixFigure(Widget widget, FigureContext figureContext) {
		super(widget, figureContext);
		setYOffset(getFigureConstants().getSimpleWidgetDefaultHeight());
		initialize();
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.page.ui.figure.BoxFigure#hasXScrollBar()
	 */
	protected boolean hasXScrollBar() {
		return false;
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.page.ui.figure.BoxFigure#hasYScrollBar()
	 */
	protected boolean hasYScrollBar() {
		return false;
	}
	
	/**
	 * @return
	 */
	public IMatrix getMatrix() {
		return MatrixHelper.getMatrix(getWidget());
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.page.ui.figure.BoxFigure#setBoxType(java.lang.String)
	 */
	public void setBoxType(String newBoxType) {
		setBoxType(new MatrixBoxType(this));
	}
	
	/**
	 * @see com.odcgroup.page.ui.figure.BoxFigure#paintClientArea(org.eclipse.draw2d.Graphics)
	 */
	protected void paintClientArea(Graphics graphics) {
		super.paintClientArea(graphics);
		graphics.setFont(getFigureConstants().getCaptionFont(false));
		graphics.setForegroundColor(ColorConstants.black);
	}	
		
	/**
	 * Gets the border color.
	 * 
	 * @return Color The border color
	 */
	public Color getBorderColor() {
		if (borderColor == null) {
			borderColor = PageUIPlugin.getColor(MATRIX_FIGURE_BORDER_COLOR);	
		}
		return borderColor;
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.page.ui.figure.AbstractWidgetFigure#dispose()
	 */
	@Override
	public void dispose() {
		super.dispose();
	}

	/**
	 * Sets the border color.
	 * 
	 * @param newBorderColor The border color to set
	 */
	protected void setBorderColor(Color newBorderColor) {
		this.borderColor = newBorderColor;	
	}
	
	/**
	 * Draws a red border around the table (design mode only)
	 * 
	 * @param graphics
	 *            The Graphics context
	 */
	protected void drawBorder(Graphics graphics) {
		if (getFigureContext().isDesignMode()) {
			Rectangle b = getBounds();
			FigureConstants fc = getFigureConstants();
			graphics.setLineStyle(fc.getFieldLineStyle());
			graphics.setForegroundColor(getBorderColor());
			// -1 ensures that the left and bottom parts are drawn
			graphics.drawRectangle(0, 0, b.width - 1, b.height - 1);
		}
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.ui.figure.AbstractWidgetFigure#getChildren()
	 */
	@SuppressWarnings("unchecked")
	public List getChildren() {
		List children =  super.getChildren();
		List<AbstractWidgetFigure> list = new ArrayList<AbstractWidgetFigure>();
		MatrixAxisFigure xAxis = getAxisFigure(children, true);
		list.add(xAxis);
		MatrixAxisFigure yAxis = getAxisFigure(children, false);
		list.add(yAxis);
		MatrixContentCellFigure contentCell = getContentCellFigure(children);
		list.add(contentCell);
		MatrixCellFigure lastColumn = getCellFigure(children, IMatrixCell.CELLTYPE_LASTCOLUMN);
		if (getMatrix().displayLastColumn() && lastColumn != null) {
			list.add(lastColumn);
		}
		MatrixExtraColumnFigure extraColumn = getExtraColumnFigure(children);
		if (extraColumn != null) {
			list.add(extraColumn);
		}
		MatrixCellFigure lastRow = getCellFigure(children, IMatrixCell.CELLTYPE_LASTROW); // hspan 2, align end
		if (getMatrix().displayLastRow() && lastRow != null) {
			list.add(lastRow);
		}
		MatrixCellFigure lastCell = getCellFigure(children, IMatrixCell.CELLTYPE_LASTCELL);
		if (getMatrix().displayLastCell() && lastCell != null) {
			list.add(lastCell);
		}		
		return list;
	}	
	
	/**
	 * @param children
	 * @return
	 */
	MatrixExtraColumnFigure getExtraColumnFigure(List<?> children) {
		for (Object object : children) {
			if (object instanceof MatrixExtraColumnFigure) {
				return (MatrixExtraColumnFigure) object;
			}
		}
		return null;
	
		
	}

	/**
	 * @param children
	 * @return
	 */
	MatrixContentCellFigure getContentCellFigure(List<?> children) {
		for (Object object : children) {
			if (object instanceof MatrixContentCellFigure) {
				return (MatrixContentCellFigure) object;
			}
		}
		return null;
	}
	
	/**
	 * @param children
	 * @param xAxis
	 * @return
	 */
	@SuppressWarnings("unchecked")
	MatrixAxisFigure getAxisFigure(List children, boolean xAxis) {
		for (Object object : children) {
			if (object instanceof MatrixAxisFigure) {
				MatrixAxisFigure axis = (MatrixAxisFigure) object;
				if (xAxis && axis.getMatrixAxis().isXAxis()) {
					return axis;
				} else if (!xAxis && axis.getMatrixAxis().isYAxis()) {
					return axis;
				}
			}
		}
		return null;
	}
	
	/**
	 * @param children
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	MatrixCellFigure getCellFigure(List children, String type) {
		for (Object object : children) {
			if (object instanceof MatrixCellFigure) {
				MatrixCellFigure cell = (MatrixCellFigure) object;
				if (type.equals(cell.getMatrixCell().getMatrixCellType())) {
					return cell;
				}
			}
		}
		return null;
	}		
	

	/* (non-Javadoc)
	 * @see com.odcgroup.page.ui.figure.AbstractWidgetFigure#notifyPropertyChange(java.lang.String)
	 */
	@Override
	public void notifyPropertyChange(String name) {
		if (name.equals("displayLastColumn") || name.equals("displayLastRow") || name.equals("displayLastCell")) {
			invalidateTree();
		}
	}

	/**
	 * initialize method to register color.
	 */
	private void initialize(){
	    PageUIPlugin.setColorInRegistry(MATRIX_FIGURE_BORDER_COLOR, new RGB(128, 0, 0));
	}
}
