package com.odcgroup.page.ui.figure.matrix;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.MatrixHelper;
import com.odcgroup.page.model.widgets.matrix.IMatrixCellItem;
import com.odcgroup.page.ui.edit.PaintUtils;
import com.odcgroup.page.ui.figure.BoxFigure;
import com.odcgroup.page.ui.figure.FigureContext;

/**
 *
 * @author pkk
 *
 */
public class MatrixCellItemFigure extends BoxFigure {

	/**
	 * @param widget
	 * @param figureContext
	 */
	public MatrixCellItemFigure(Widget widget, FigureContext figureContext) {
		super(widget, figureContext);
	}
	
	/**
	 * @return
	 */
	public IMatrixCellItem getMatrixCellItem() {
		return MatrixHelper.getMatrixCellItem(getWidget());
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.page.ui.figure.BoxFigure#getHeight()
	 */
	public int getHeight() {
		return 20;
	}	
	
	/**
	 * Draw the caption.
	 * 
	 * @param graphics The Graphics context
	 */
	protected void drawCaption(Graphics graphics) {
		String caption = "";
		if (getMatrixCellItem().isComputed()) {
			caption = getMatrixCellItem().getColumnName();
		} else {
			caption = getMatrixCellItem().getDomainAttribute().getValue();
		}
		graphics.setFont(getFigureConstants().getCaptionFont(false));		
		Rectangle b = getBounds();
		int size = calculateTextSize(caption, graphics.getFont()).width;
		int iconsWidth=PaintUtils.getWidth(getWidget());
		PaintUtils.paintIcons(getWidget(), graphics, b.width-iconsWidth);
		int x = b.width - (size+10+iconsWidth);
		graphics.drawText(caption, x, 2);			
	}	

	/**
	 * Draws an visual indicator for the box type.
	 * 
	 * @param graphics
	 */
	protected void drawBoxTypeIndicator(Graphics graphics) {
		//do nothing
	}
}
