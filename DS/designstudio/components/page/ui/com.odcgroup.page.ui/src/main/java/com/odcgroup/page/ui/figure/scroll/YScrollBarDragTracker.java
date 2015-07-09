package com.odcgroup.page.ui.figure.scroll;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * Implements drag-tracking for the Y-ScrollBar.
 * 
 * @author Gary Hayes
 */
public class YScrollBarDragTracker extends AbstractScrollBarDragTracker {

	/**
	 * Creates a new YScrollBarDragTracker.
	 * 
	 * @param scrollableFigure
	 *            The Figure being scrolled
	 */
	public YScrollBarDragTracker(ScrollableWidgetFigure scrollableFigure) {
		super(scrollableFigure);
	}
	
	/**
	 * Handles the drag.
	 * 
	 * @return boolean True if the drag was handled
	 */
	protected boolean handleDragInProgress() {
		Point p = getLocation();
		
		ScrollableWidgetFigure swf = getScrollableWidgetFigure();
		Rectangle b = swf.getScrollableBounds().getCopy();
		swf.translateToAbsolute(b);

		double scaling = swf.getYScaleFactor();
		
		Point newOrigin = swf.getOrigin().getCopy();
		
		int xScollBarHeight = 0;
		if (swf.getXScrollBar() != null) {
			xScollBarHeight = ScrollableWidgetFigure.SCROLL_BAR_SIZE;
		}
		
		if (p.y < b.y) {
			// We have scrolled to the minimum position
		} else if (p.y >= b.y + b.height - xScollBarHeight) {
			// We have scrolled to the maximum position
			newOrigin.y = (int) ((b.height - xScollBarHeight) * scaling);
		} else {
			newOrigin.y = (int) ((p.y - b.y) * scaling);
		}

		swf.setOrigin(newOrigin);

		// Layout the figure taking into account the changes
		swf.getLayoutManager().layout(swf);
		
		swf.invalidateTree();
		swf.repaint();

		return true;
	}
}