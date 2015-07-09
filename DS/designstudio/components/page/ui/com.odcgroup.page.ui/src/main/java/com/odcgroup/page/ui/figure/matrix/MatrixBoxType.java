package com.odcgroup.page.ui.figure.matrix;

import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LayoutManager;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.MatrixHelper;
import com.odcgroup.page.model.widgets.matrix.IMatrix;
import com.odcgroup.page.ui.figure.AbstractBoxType;
import com.odcgroup.page.ui.figure.BoxFigure;
import com.odcgroup.page.ui.figure.FigureConstants;
import com.odcgroup.page.ui.figure.FigureContext;
import com.odcgroup.page.ui.figure.IWidgetFigure;

/**
 * @author pkk
 * @since DS 1.40.0
 */
public class MatrixBoxType extends AbstractBoxType {	
	
	
	/** The absolute box type character used to display. */
	private static String BOX_TYPE_TO_DISPLAY = "";
	
	/*
	 * @see com.odcgroup.page.ui.figure.BoxType#createLayoutManager(com.odcgroup.page.ui.figure.FigureContext)
	 */
	public LayoutManager createLayoutManager(FigureContext figureContext) {
		/*
		GridLayout gd = new GridLayout(4, true);
		if (figureContext.isPreviewMode()) {
			gd.marginHeight= 0;
			gd.marginWidth = 1;
			gd.horizontalSpacing = 1;
			gd.verticalSpacing = 0;
		} else {
			gd.marginHeight= 3;
			gd.marginWidth = 9;
			gd.horizontalSpacing = 1;
			gd.verticalSpacing = 1;
		}
		*/
		MatrixLayout gd = new MatrixLayout();
		return gd;
	}

	/*
	 * @see com.odcgroup.page.ui.figure.BoxType#getBoxType()
	 */
	public String getBoxType() {
		return BOX_TYPE_TO_DISPLAY;
	}

	/*
	 * @see com.odcgroup.page.ui.figure.BoxType#getMaxHeight()
	 */
	public int getMaxHeight() {
		if (getHeight() > 0) {
			return getHeight();
		}
		
		int height = 0;	
		List<?> children = getChildren();
		for (Object object : children) {
			if (object instanceof MatrixAxisFigure) {
				MatrixAxisFigure axis = (MatrixAxisFigure) object;
				if (axis.getMatrixAxis().isXAxis()) {
					height += axis.getHeight();
				}
			} else if (object instanceof MatrixContentCellFigure) {
				height += ((MatrixContentCellFigure) object).getMinHeight();
			} else if (object instanceof MatrixCellFigure) {
				MatrixCellFigure cell = (MatrixCellFigure) object;
				if(cell.getMatrixCell().isLastRow()) {
					height += cell.getMinHeight();
				}
			}
		}
		
		if (children.size() > 0) {
			height += getFigureConstants().getWidgetSpacing() * 2;
		}		
		
		FigureConstants fc = getFigureConstants();
		if (height < fc.getSimpleWidgetDefaultHeight()) {
			height = fc.getSimpleWidgetDefaultHeight();
		}
		
		return height;
	}

	/*
	 * @see com.odcgroup.page.ui.figure.BoxType#getMaxWidth()
	 */
	public int getMaxWidth() {
		if (getPixelWidth() > 0) {
			return getPixelWidth();
		}		
		
		return -1;
	}

	/*
	 * @see com.odcgroup.page.ui.figure.BoxType#getMinHeight()
	 */
	public int getMinHeight() {
		if (getHeight() > 0) {
			return getHeight();
		}
		
		int height = 0;		
		List<?> children = getChildren();
		boolean lastRowFound = false;
		for (Object object : children) {
			if (object instanceof MatrixAxisFigure) {
				MatrixAxisFigure axis = (MatrixAxisFigure) object;
				if (axis.getMatrixAxis().isXAxis()) {
					height += axis.getHeight();
				}
			} else if (object instanceof MatrixContentCellFigure) {
				height += ((MatrixContentCellFigure) object).getMinHeight();
			} else if (object instanceof MatrixCellFigure) {
				MatrixCellFigure cell = (MatrixCellFigure) object;
				if(cell.getMatrixCell().isLastRow()) {
					height += cell.getMinHeight();
					lastRowFound = true;
				} 
				if (!lastRowFound && cell.getMatrixCell().isLastCell()) {
					height += cell.getMinHeight();					
				}
			}
		}
		
		if (children.size() > 0) {
			height += getFigureConstants().getWidgetSpacing() * 2;
		}		
		
		FigureConstants fc = getFigureConstants();
		if (height < fc.getSimpleWidgetDefaultHeight()) {
			height = fc.getSimpleWidgetDefaultHeight();
		}
		
		return height;
	}

	/**
	 * @see com.odcgroup.page.ui.figure.BoxType#getMinWidth()
	 */
	public int getMinWidth() {
		int width = 0;
		FigureConstants fc = getFigureConstants();
		IFigure parent = getBoxFigure().getParent();
		if (parent instanceof BoxFigure) {
			BoxFigure parentBox = (BoxFigure) parent;
			Widget widget = parentBox.getWidget();
			String type = widget.getPropertyValue(PropertyTypeConstants.BOX_TYPE);
			if (type.equals(PropertyTypeConstants.BOX_TYPE_VERTICAL)) {
				width = fc.getSimpleWidgetDefaultWidth();
			} else {			
				int parentWidth = parentBox.getBounds().width;
				int peerWidth = 0;
				int grids = 0;
				if (parentWidth > fc.getSimpleWidgetDefaultWidth()) {
					for (IWidgetFigure peer : parentBox.getAllChildren()) {
						if(peer instanceof MatrixFigure) {
							grids++;
						} else {
							peerWidth += (100 + 18);
						}
					}
					int availableWidth = parentWidth - peerWidth;
					width = (grids > 0) ? (availableWidth/grids)-18 : availableWidth - 18;
				} 
			}
		}
		if (width < getPixelWidth()) {
			width = getPixelWidth();
		}
		return width;
	}
	
	/**
	 * @return
	 */
	protected IMatrix getMatrix() {
		return MatrixHelper.getMatrix(getBoxFigure().getWidget());
	}

	/**
	 * @param box
	 */
	public MatrixBoxType(BoxFigure box) {
		super(box);
	}

}
