package com.odcgroup.page.ui.figure.matrix;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.MatrixHelper;
import com.odcgroup.page.model.widgets.matrix.IMatrixContentCell;
import com.odcgroup.page.ui.PageUIPlugin;
import com.odcgroup.page.ui.figure.BoxFigure;
import com.odcgroup.page.ui.figure.FigureConstants;
import com.odcgroup.page.ui.figure.FigureContext;

/**
 * TODO: Document me!
 *
 * @author pkk
 *
 */
public class MatrixContentCellFigure extends BoxFigure {
	
	/** The border Color. */
	private Color borderColor;
	private static final String MATRIX_CONTENTCELL_FIGURE_BORDER_COLOR = "MATRIX_CONTENTCELL_FIGURE_BORDER_COLOR";
	/**
	 * @param widget
	 * @param figureContext
	 */
	public MatrixContentCellFigure(Widget widget, FigureContext figureContext) {
		super(widget, figureContext);
		initialize();
	}
	
	/**
	 * @return
	 */
	public IMatrixContentCell getMatrixContentCell() {
		return MatrixHelper.getMatrixContentCell(getWidget());
	}
	
	/**
	 * Draw the caption.
	 * 
	 * @param graphics The Graphics context
	 */
	protected void drawCaption(Graphics graphics) {		
		decorateColumnHeader(graphics, "cells");			
	}	
	
	/**
	 * @param graphics
	 * @param caption
	 */
	private void decorateColumnHeader(Graphics graphics, String caption) {	
		int x = 10;
		graphics.setFont(getFigureConstants().getCaptionFont(false));
		graphics.drawText(caption, x, 0);		
	}

	/**
	 * Draws an visual indicator for the box type.
	 * 
	 * @param graphics
	 */
	protected void drawBoxTypeIndicator(Graphics graphics) {
		// Do nothing
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
	
	/* (non-Javadoc)
	 * @see com.odcgroup.page.ui.figure.BoxFigure#getHeight()
	 */
	public int getHeight() {
		return 0;
	}
	
	/**
	 * Gets the width of the box in pixels. If the width is expressed
	 * in percentage this returns 0.
	 * 
	 * @return int The width of the box in pixels
	 */
	public int getPixelWidth() {
		int width  = ((getParent().getBounds().width - 1) * 25 / 100);
		return width;
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
			graphics.setLineStyle(fc.getOutlineBorderLineStyle());
			// -1 ensures that the left and bottom parts are drawn
			graphics.drawRectangle(0, 0, b.width - 1, b.height - 1);
			
			graphics.setLineStyle(fc.getFieldLineStyle());
			graphics.setForegroundColor(getBorderColor());
			graphics.drawRectangle(5, 15, b.width -10, b.height -20);
		}
	}
	
	/**
	 * @see org.eclipse.draw2d.Figure#paintChildren(org.eclipse.draw2d.Graphics)
	 */
	protected void paintChildren(Graphics graphics) {
		IFigure child;
		int childY = 15;
		int space = getFigureConstants().getWidgetSpacing();
		for (int i = 0; i < getChildren().size(); i++) {
			child = (IFigure)getChildren().get(i);
			Rectangle bounds = child.getBounds();
			bounds.y = childY+space;
			graphics.clipRect(bounds);
			child.paint(graphics);
			graphics.restoreState();
			childY += bounds.height+space;
		}
	}
	
	/**
	 * Gets the border color.
	 * 
	 * @return Color The border color
	 */
	public Color getBorderColor() {
		if (borderColor == null) {
			borderColor = PageUIPlugin.getColor(MATRIX_CONTENTCELL_FIGURE_BORDER_COLOR);
		}
		return borderColor;
	}
	
	/**
	 * Sets the border color.
	 * 
	 * @param newBorderColor The border color to set
	 */
	protected void setBorderColor(Color newBorderColor) {
		this.borderColor = newBorderColor;
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.page.ui.figure.AbstractWidgetFigure#dispose()
	 */
	public void dispose() {
		super.dispose();
	}	
	
	/*
	 * @see com.odcgroup.page.ui.figure.BoxFigure#setBoxType(java.lang.String)
	 */
	public void setBoxType(String newBoxType) {
		setBoxType(new MatrixCellVerticalBoxType(this));
	}
	/**
	 * initialize method to register color.
	 */
	private void initialize(){
	    PageUIPlugin.setColorInRegistry(MATRIX_CONTENTCELL_FIGURE_BORDER_COLOR, new RGB(0, 128, 255));
	}
}
