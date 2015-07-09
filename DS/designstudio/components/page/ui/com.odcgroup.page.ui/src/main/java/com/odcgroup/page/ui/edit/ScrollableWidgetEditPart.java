package com.odcgroup.page.ui.edit;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.Request;
import org.eclipse.gef.requests.SelectionRequest;

import com.odcgroup.page.ui.figure.scroll.ScrollableWidgetFigure;
import com.odcgroup.page.ui.figure.scroll.XScrollBar;
import com.odcgroup.page.ui.figure.scroll.XScrollBarDragTracker;
import com.odcgroup.page.ui.figure.scroll.YScrollBar;
import com.odcgroup.page.ui.figure.scroll.YScrollBarDragTracker;

/**
 * An EditPart for Widgets which are not the root element of the diagram and contain ScrollBars.
 * Note that we assume that the different containers use local coordinates.
 * 
 * @author Gary Hayes
 */
public class ScrollableWidgetEditPart extends WidgetEditPart {

	/**
	 * Overrides the base-class version to determine if we are selecting the Figure or the ScrollBar associated with it.
	 * 
	 * @param request
	 *            The request
	 * @return DragTracker The DragTracker
	 */
	public DragTracker getDragTracker(Request request) {
		SelectionRequest sr = (SelectionRequest) request;
		Point mp = sr.getLocation();

		ScrollableWidgetFigure swf = (ScrollableWidgetFigure) getFigure();
		
		if (hasClickedXThumb(swf, mp)) {
			return new XScrollBarDragTracker(swf);
		}
		
		if (hasClickedYThumb(swf, mp)) {
			return new YScrollBarDragTracker(swf);
		}
		
		return super.getDragTracker(request);
	}
	
	/**
	 * Returns true if the mouse was clicked over the X-ScrollBar's thumb.
	 * 
	 * @param scrollableFigure The ScrollableWidgetFigure
	 * @param p The Point to test
	 * @return boolean True if the mouse was clicked over the X-ScrollBar's thumb
	 */
	private boolean hasClickedXThumb(ScrollableWidgetFigure scrollableFigure, Point p) {
		XScrollBar xsb = scrollableFigure.getXScrollBar();
		
		if (xsb == null) {
			// No ScrollBar. The result is obviously false
			return false;
		}
		
		return contains(scrollableFigure, xsb.getThumb().getBounds(), p);
	}
	
	/**
	 * Returns true if the mouse was clicked over the Y-ScrollBar's thumb.
	 * 
	 * @param scrollableFigure The ScrollableWidgetFigure
	 * @param p The Point to test
	 * @return boolean True if the mouse was clicked over the Y-ScrollBar's thumb
	 */
	private boolean hasClickedYThumb(ScrollableWidgetFigure scrollableFigure, Point p) {
		YScrollBar ysb = scrollableFigure.getYScrollBar();
		
		if (ysb == null) {
			// No ScrollBar. The result is obviously false
			return false;
		}
			
		return contains(scrollableFigure, ysb.getThumb().getBounds(), p);
	}	
		
	/**
	 * Returns true if the Rectanglwe contains the Point. This test
	 * is relative to the figure passed as an argument. The Rectangle
	 * is displaced relative to the absolute position of the Figure
	 * before performing the test.
	 * 
	 * @param scrollableFigure The figure
	 * @param r The Rectangle
	 * @param p The Point to test
	 * @return boolean True if the Rectangle contains the Point
	 */
	private boolean contains(ScrollableWidgetFigure scrollableFigure, Rectangle r, Point p) {
		Rectangle sb = r.getCopy();	
		Point sp = translatePointToAbsolute(scrollableFigure);
		sb.x = sb.x + sp.x;
		sb.y = sb.y + sp.y;
			
		if (sb.contains(p)) {
			return true;
		}		
		
		return false;
	}
	
	/**
	 * Translates the point to absolute coordinates.
	 * Note that we assume that each container is using
	 * local coordinates.
	 * 
	 * @param figure The figure containing the Point
	 * @return Point The translated Point
	 */
	private Point translatePointToAbsolute(IFigure figure) {
		int x = 0;
		int y = 0;
		IFigure f = figure;
		while (f.getParent() != null) {
			Rectangle fb = f.getBounds();
			x = x + fb.x;
			y = y + fb.y;
			
			f = f.getParent();
		}
		
		return new Point(x, y);
	}
}