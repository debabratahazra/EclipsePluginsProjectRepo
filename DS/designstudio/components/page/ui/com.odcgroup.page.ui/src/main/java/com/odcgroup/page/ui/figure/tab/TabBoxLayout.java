package com.odcgroup.page.ui.figure.tab;

import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

import com.odcgroup.page.ui.figure.FigureConstants;
import com.odcgroup.page.ui.figure.FigureContext;
import com.odcgroup.page.ui.figure.IWidgetFigure;
import com.odcgroup.page.ui.figure.VerticalLayout;
import com.odcgroup.page.ui.figure.conditional.ConditionalBodyFigure;
import com.odcgroup.page.ui.figure.scroll.ScrollableWidgetFigure;

/**
 *
 */
public class TabBoxLayout extends VerticalLayout {

	/**
	 * @param figureContext
	 */
	public TabBoxLayout(FigureContext figureContext) {
		super(figureContext);
	}
	
	/**
	 * Lays out the figure.
	 * 
	 * @param container
	 *            The container to layout
	 */
	@SuppressWarnings("unchecked")
	public void layout(IFigure container) {
		Point origin = new Point(0, 0);
		Rectangle b = container.getBounds();
		if (container instanceof ScrollableWidgetFigure) {
			ScrollableWidgetFigure sf = (ScrollableWidgetFigure) container;
			Point o = sf.getOrigin();
			origin = new Point(o.x, o.y - sf.getYOffset());
			b = sf.getScrollableBounds();
		}	
		int width = b.width;
		if (!getFigureContext().isPreviewMode()) {
			width = width - 9;
		}		
		
		List widgets = buildWidgetList(container);
		int numberOfWidgets = widgets.size();
		if (numberOfWidgets == 0) {
			return;
		}		

		calculateSizes(widgets, width);
		calculatePositions(widgets, origin);
		
	}
	
	/**
	 * Calculate the size of each Widget and sets the Bounds. The x and y positions are initialised to 0. These are then
	 * set in {@link VerticalLayout#calculatePositions}.
	 * 
	 * @param widgets The Widgets to be laid out
	 * @param containerWidth The width of the container
	 */
	protected void calculateSizes(List widgets, int containerWidth) {
		int allocatedWidth = containerWidth;
		for (Iterator it = widgets.iterator(); it.hasNext();) {
			IWidgetFigure child = (IWidgetFigure) it.next();	
			int height = child.getMinHeight();
			if (child instanceof TabFigure || child instanceof ConditionalBodyFigure) {
				allocatedWidth = containerWidth - 9;
			} else {
				allocatedWidth += 9;
			}
			Rectangle nb = new Rectangle(0, 0, allocatedWidth, height);
			child.setBounds(nb);
		}
	}

	/**
	 * Calculate the position of each Widget and sets the Bounds. Note that this method assumes that the sizes of each
	 * figure have already been calculated in {@link VerticalLayout#calculateSizes} and that
	 * their Bounds have been set.
	 * 
	 * @param widgets The Widgets to be laid out
	 * @param origin The origin of the figure. This is useful when there are scrollbars
	 */
	protected void calculatePositions(List widgets, Point origin) {
		FigureConstants fc = getFigureConstants();

		int xPos = fc.getWidgetSpacing() - origin.x;
		int yPos = fc.getWidgetSpacing() - origin.y;
		if (getFigureContext().isDesignMode()) {
			xPos = 9;
		}
		for (Iterator it = widgets.iterator(); it.hasNext();) {
			IWidgetFigure child = (IWidgetFigure) it.next();
			Rectangle b = child.getBounds();
			if (child instanceof TabFigure || child instanceof ConditionalBodyFigure) {
				b.x = xPos;
			} else {
				b.x = 0;		
			}
			b.y = yPos; 
			child.setBounds(b);
			yPos += b.height;
			yPos += fc.getWidgetSpacing();
		}
	}	
	
}
