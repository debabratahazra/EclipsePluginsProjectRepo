package com.odcgroup.page.ui.figure.scroll;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

import com.odcgroup.page.ui.figure.FigureContext;

/**
 * Represents a Y-ScrollBar in a diagram.
 * 
 * @author Gary Hayes
 */
public class YScrollBar extends AbstractScrollBar {
	
	/** The height of the thumb. */
	private static final int THUMB_HEIGHT = 16;
	
	/**
	 * Creates a new yScrollBar.
	 * 
	 * @param figureContext The FigureContext
	 * @param figure The ScrollableWidgetFigure that the ScrollBar is for
	 */
	public YScrollBar(FigureContext figureContext, ScrollableWidgetFigure figure) {
		super(figureContext, figure);
	}
	
	/**
	 * Recalculates the Bounds of the ScrollBar's Figures.
	 */
	protected void recalculateBounds() {
		ScrollableWidgetFigure swf = getScrollableFigure();
	
		Point fo = swf.getOrigin();
		Rectangle b = getBounds();
		
		double scaling = swf.getYScaleFactor();
			
		// Limit the vertical position of the thumb so that it does not scroll
		// off the bottom of the ScrollBar
		int yPos = (int) (fo.y / scaling) + swf.getYOffset();
		int clientHeight = b.height - THUMB_HEIGHT + swf.getYOffset();
		if (yPos > clientHeight) {
			yPos = clientHeight;
		}
		
		Rectangle tb = new Rectangle(b.x, yPos, b.width - b.x, THUMB_HEIGHT);
		getThumb().setBounds(tb);
	}
}