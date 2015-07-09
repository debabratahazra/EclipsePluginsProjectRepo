package com.odcgroup.page.ui.figure.scroll;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

import com.odcgroup.page.ui.figure.FigureContext;

/**
 * Represents a X-ScrollBar in a diagram.
 * 
 * @author Gary Hayes
 */
public class XScrollBar extends AbstractScrollBar {
	
	/** The height of the thumb. */
	private static final int THUMB_WIDTH = 16;
	
	/**
	 * Creates a new XScrollBar.
	 * 
	 * @param figureContext The FigureContext
	 * @param figure The ScrollableWidgetFigure that the ScrollBar is for
	 */
	public XScrollBar(FigureContext figureContext, ScrollableWidgetFigure figure) {
		super(figureContext, figure);
	}
	
	/**
	 * Recalculates the Bounds of the ScrollBar's Figures.
	 */
	protected void recalculateBounds() {
		ScrollableWidgetFigure swf = getScrollableFigure();
		Point fo = swf.getOrigin();
		Rectangle b = getBounds();
		
		double scaling = swf.getXScaleFactor();
		
		// Limit the horizontal position of the thumb so that it does not scroll
		// off the right-hand side of the ScrollBar
		int xPos = (int) (fo.x / scaling);
		if (xPos > b.width - THUMB_WIDTH) {
			xPos = b.width - THUMB_WIDTH;
		}		
		
		Rectangle tb = new Rectangle(xPos, b.y, THUMB_WIDTH, b.height - b.y);
		getThumb().setBounds(tb);
	}	
}