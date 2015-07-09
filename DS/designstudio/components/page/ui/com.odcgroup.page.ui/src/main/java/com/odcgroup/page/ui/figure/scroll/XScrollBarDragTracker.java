package com.odcgroup.page.ui.figure.scroll;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * Implements drag-tracking for the X-ScrollBar.
 * 
 * @author Gary Hayes
 */
public class XScrollBarDragTracker extends AbstractScrollBarDragTracker {

	/**
	 * Creates a new XScrollBarDragTracker.
	 * 
	 * @param scrollableFigure
	 *            The Figure being scrolled
	 */
	public XScrollBarDragTracker(ScrollableWidgetFigure scrollableFigure) {
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
		
		double scaling = swf.getXScaleFactor();

		Point newOrigin = swf.getOrigin().getCopy();
		
		int yScollBarWidth = 0;
		if (swf.getYScrollBar() != null) {
			yScollBarWidth = ScrollableWidgetFigure.SCROLL_BAR_SIZE;
		}		
		
		if (p.x < b.x) {
			// We have scrolled to the minimum position
		} else if (p.x >= b.x + b.width - yScollBarWidth) {
			// We have scrolled to the maximum position
			newOrigin.x = (int) ((b.width - yScollBarWidth) * scaling);
		} else {
			newOrigin.x = (int) ((p.x - b.x) * scaling);
		}

		swf.setOrigin(newOrigin);

		// Layout the figure taking into account the changes
		swf.getLayoutManager().layout(swf);
		
		swf.invalidateTree();
		swf.repaint();		

		return true;
	}
}
