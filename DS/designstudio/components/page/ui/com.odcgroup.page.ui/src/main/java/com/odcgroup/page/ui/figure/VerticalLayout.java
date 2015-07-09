package com.odcgroup.page.ui.figure;

import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

import com.odcgroup.page.ui.figure.scroll.ScrollableWidgetFigure;
import com.odcgroup.page.ui.util.PointUtils;

/**
 * The VerticalLayout is used with VerticalBoxes. It can calculate the position of new elements to be added to the box.
 * See {@link HorizontalLayout} for a discussion of the general design of the
 * LayoutManagers.
 * 
 * @author Gary Hayes
 */
public class VerticalLayout extends AbstractWidgetLayout {
	
	/**
	 * Creates a new VerticalLayout.
	 * 
	 * @param figureContext The context in which the Layout is being used
	 */
	public VerticalLayout(FigureContext figureContext) {
		super(figureContext);
	}

	/**
	 * Lays out the figure.
	 * 
	 * @param container
	 *            The container to layout
	 */
	public void layout(IFigure container) {
		Point origin = new Point(0, 0);
		Rectangle b = container.getBounds();
		if (container instanceof ScrollableWidgetFigure) {
			ScrollableWidgetFigure sf = (ScrollableWidgetFigure) container;
			Point o = sf.getOrigin();
			origin = new Point(o.x, o.y - sf.getYOffset());
			b = sf.getScrollableBounds();
		}		
		
		// We are using local coordinates. Thus all calculations are performed with
		// respect to the edge of the container.
		
		List widgets = buildWidgetList(container);
		int numberOfWidgets = widgets.size();
		if (numberOfWidgets == 0) {
			// Nothing to recalculate
			return;
		}		

		// Calculate the size and position of each figure
		
		// Note that some of container's width may have
		// already been used, for example for displaying the ScrollBars.
		calculateSizes(widgets, b.width);
		calculatePositions(widgets, origin);
		
		// Calculate the bounds of the WidgetFigureContainer's.
		// These are calculated from the Bounds of the contained Widgets
		// which have just been set in calculateSizes and calculatePositions
		calculateWidgetFigureContainerBounds(container);
	}
	
	/**
	 * Calculate the size of each Widget and sets the Bounds. The x and y positions are initialised to 0. These are then
	 * set in {@link VerticalLayout#calculatePositions}.
	 * 
	 * @param widgets The Widgets to be laid out
	 * @param containerWidth The width of the container
	 */
	protected void calculateSizes(List widgets, int containerWidth) {
		FigureConstants fc = getFigureConstants();

		// The width of each Widget is equal to its minWidth UNLESS its maxWidth = -1.
		// In this case it is equal to the width of the VerticalLayout (minus the spacing).
		int allocatedWidth = containerWidth - 2 * fc.getWidgetSpacing();
		
		for (Iterator it = widgets.iterator(); it.hasNext();) {
			IWidgetFigure child = (IWidgetFigure) it.next();

			int width = 0;
			if (child.getMaxWidth() == -1) {
				width = calculateConstrainedWidth(child, allocatedWidth);
			}

			if (width < child.getMinWidth()) {
				width = child.getMinWidth();
			}

			int height = child.getMinHeight();

			// Initialise the x and y positions to zero. These will be calculated later in
			// calculatePositions.
			Rectangle nb = new Rectangle(0, 0, width, height);
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

		// We begin at widgetSpacing in order to add an
		// equal spacing between the VerticalBox and the Widgets that
		// it contains. If we do not do this we will be unable to add new
		// widgets since they would take up all the space of the Box.
		int xPos = fc.getWidgetSpacing() - origin.x;
		int yPos = fc.getWidgetSpacing() - origin.y;
		
		//int maxWidth = calculateMaxWidth(container);

		for (Iterator it = widgets.iterator(); it.hasNext();) {
			IWidgetFigure child = (IWidgetFigure) it.next();

			Rectangle b = child.getBounds();
			// The horizontal alignment does not affect the position
			// of the Widget in the OCS vbox. If it does we would call
			// calculateXPosition.
			
			// OCS-27353
			// b.x = xPos; 
			b.x = calculateXPosition(child, xPos, child.getParent().getBounds().width);
			
			// The vertical alignment does not affect the position
			// of the Widget in the OCS vbox
			b.y = yPos; 
			child.setBounds(b);

			yPos += b.height;

			// Add the spacing between the Widgets
			yPos += fc.getWidgetSpacing();
		}
	}
	
	/**
	 * Calculates the maximum width of the widest child figure.
	 * 
	 * @param container The container to calculate the maximum width for
	 * @return int The maximum width of the widest child figure
	 */
	/*private int calculateMaxWidth(IFigure container) {
		int maxWidth = 0;
		for (Iterator it = container.getChildren().iterator(); it.hasNext();) {
			IFigure child = (IFigure) it.next();
			maxWidth = Math.max(maxWidth, child.getBounds().width);
		}
		
		return maxWidth;
	}*/	
	
	/**
	 * Calculates the x-position of a figure given its alignment.
	 * 
	 * @param figure The figure to calculate the y-position for
	 * @param leftPosition The top-most position that the figure can have
	 * @param allocatedWidth The height which has been allocated to the figure
	 * @return int The x-position to give to the figure
	 */
	private int calculateXPosition(IFigure figure, int leftPosition, int allocatedWidth) {
		if (!(figure instanceof Alignable)) {
			// No special alignment
			return leftPosition;
		}

		Alignable af = (Alignable) figure;
		String ha = af.getHorizontalAlignment();

		if (Alignable.LEAD.equalsIgnoreCase(ha)) {
			return leftPosition;
		} else if (Alignable.CENTER.equalsIgnoreCase(ha)) {
			return leftPosition + (allocatedWidth - figure.getBounds().width) / 2;
		} else if (Alignable.TRAIL.equalsIgnoreCase(ha)) {
			return -leftPosition  + allocatedWidth - figure.getBounds().width;
		} else {
			// Unknown alignment
			return leftPosition;
		}
	}	

	/**
	 * Calculates the index of the IFigure.
	 * 
	 * @param container
	 *            The container of the new Figure
	 * @param location
	 *            The location of the CreateRequest
	 * @return int The index of the new Widget
	 */
	public int calculateIndex(IFigure container, Point location) {
		Point p = PointUtils.convertToLocalCoordinates(container, location);

		List children = container.getChildren();
		for (int i = 0; i < children.size(); ++i) {
			IFigure child = (IFigure) children.get(i);
			Rectangle r = child.getBounds();
			int yPos = r.y;

			if (yPos > p.y) {
				// The user has clicked before this Widget
				return i;
			}
		}

		// The Widget should be added to the end of the collection
		return children.size();
	}

	/**
	 * Gets the constrainted width given the allocated space. Note that we only use the constaint if it lies within
	 * reasonable bounds to avoid, for example, NullPointerExceptions etc.
	 * 
	 * @param figure
	 *            The figure to get the constrained width for
	 * @param allocatedSpace
	 *            The space which has been allocated to the figure
	 * @return int The real width to give to the figure
	 */
	private int calculateConstrainedWidth(IFigure figure, int allocatedSpace) {
		int width = allocatedSpace;
		// If the figure is constrainable then instead of taking the allocatedSpace width
		// it takes the allocatedSpace * constraint.
		if (figure instanceof Constrainable) {
			Constrainable c = (Constrainable) figure;
			int constraint = c.getConstraints();
			if (constraint >= 5 && constraint <= 100) {
				width = width * constraint / 100;
			}
		}

		return width;
	}
	
	/**
	 * Calculates the Bounds of the WidgetFigureContainer which are children of the container.
	 * The Bounds go from the top-left of their first Widget to the bottom-right of their last Widget.
	 * 
	 * @param container The container whocse children we need to recalculate the Bounds for
	 */
	protected void calculateWidgetFigureContainerBounds(IFigure container) {
		for (Iterator it = container.getChildren().iterator(); it.hasNext();) {
			IWidgetFigure child = (IWidgetFigure) it.next();
			if (child instanceof WidgetFigureContainer) {
				WidgetFigureContainer cwf = (WidgetFigureContainer) child;
				List wf = cwf.getWidgetFigures();
				if (wf.size() == 0) {
					continue;
				}
				IFigure firstFigure = (IFigure) wf.get(0);
				IFigure lastFigure = (IFigure) wf.get(wf.size() -1);
				
				Rectangle ffb = firstFigure.getBounds();
				Rectangle lfb = lastFigure.getBounds();
			
				int width = calculateMaxWidth(wf);
				int height = lfb.y + lfb.height - ffb.y;
				
				if (width < child.getMinWidth()) {
					width = child.getMinWidth();
				}
				
				if (height < child.getMinHeight()) {
					height = child.getMinHeight();
				}
				
				Rectangle b = new Rectangle();
				
				b.x = calculateMinX(wf);
				b.y = calculateMinY(wf);			
				b.width = width;
				b.height = height;
				
				child.setBounds(b);
			}
		}
	}	
	
	/**
	 * Calculates the maximum width of the widest child figure.
	 * 
	 * @param widgets The List of Widgets to calculate the maximum width for
	 * @return int The maximum width of the widest child figure
	 */
	private int calculateMaxWidth(List widgets) {
		int maxWidth = 0;
		for (Iterator it = widgets.iterator(); it.hasNext();) {
			IFigure child = (IFigure) it.next();
			maxWidth = Math.max(maxWidth, child.getBounds().width);
		}
		
		return maxWidth;
	}	
}