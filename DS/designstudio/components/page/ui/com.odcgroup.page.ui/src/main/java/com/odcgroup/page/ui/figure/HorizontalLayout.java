package com.odcgroup.page.ui.figure;

import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

import com.odcgroup.page.ui.figure.scroll.ScrollableWidgetFigure;
import com.odcgroup.page.ui.figure.table.CompartmentFigure;
import com.odcgroup.page.ui.util.PointUtils;

/**
 * The HorizontalLayout is used with HorizontalBoxes. Each HorizontalBox has its own instance of a HorizontalLayout. It
 * is also used with the root Widget. The calculation of the Bounds of each Widget is performed by descending the Widget
 * tree. Therefore the Bounds of a Widget at the root of the heirarchy is calculated BEFORE the Bounds of a child
 * Widget. Additionally the parent container is responsable for calculating the Bounds of each child Widget. How can the
 * parent container know what size is has to have in order that all the child elements fit within it? To obtain this
 * information it uses the minWidths, minHeights, maxWidths and maxHeights that the child Widgets calculate themselves.
 * Note that during the calculation of the min / max values these Widgets must use NEITHER their Bounds NOR the Bounds
 * of their children (via {@link IFigure#getBounds} since these have not yet been calculated yet (since they are
 * calculated by their parent). Additionally it is useless for them to call setBounds since the parent LayoutManager will
 * overwrite the value.
 * 
 * In resume: The Bounds of a child Widget are calculated by the parent Widget. At a later time the Bounds of the
 * grandchild of the parent Widget are calculated by the child Widget and so on.
 * 
 * By default Widgets contained within a HorizontalBox are given and equal amount of space. Thus if the Box has a width
 * of 400 and it contains 2 Widgets the first Widget will be drawn starting at an x-position of 0 and the second at an
 * x-position of 200. Note that we add a small spacing around each element to ensure that Widgets can be added before
 * other Widgets. Thus supposing that the spacing is 2 pixels the first Widget is allocated a space of 196 pixels (from
 * 2 to 198) and the second Widget a space of 198 pixels (from 202 to 398). Unfortunately some Widgets are too large to
 * fit within this space. In this case, for each Widget whose size exceeds the allocated space we automatically set its
 * width to its minWidth. We then try to allocate the remaining space (the container's width - minus that which has been
 * allocated to the large Widgets) to the remaining Widgets (those whose widths were OK during the first allocation).
 * Unfortunately the remaining space that we now try to allocate to each Widget is smaller than that which was
 * originally allocated. Thus it may be that in this second allocation some of these smaller Widgets exceed their
 * allocated sizes. In this case we also set their widths to their minWidths. This allocation strategy proceeds
 * recursively with the remaining space getting smaller and smaller.
 * 
 * There are two exits. Either: 1. All of the Widgets minWidths are smaller then the allocated widths. This simply means
 * that each Widget is allocated an equal space since none of them are too large. 2. Some of the the minWidths is
 * smaller than the allocated widths. In this case the larger Widgets are given more space since they were allocated
 * space within an early iteration (when the remaining space is still relatively large). 3. None of the Widgets
 * minWidths are smaller than the allocated space. In this case we have a problem since we cannot fit every Widget onto
 * the diagram. Our solution consists of displaying each Widget in full. Therefore the later Widgets overflow the page.
 * We consider that this is abnormal use of the Page Designer.
 * 
 * When the width of the Widget is set we need to test if it Constrainable. In this case the width is set to the
 * allocated space * constraint or minWidth whichever is larger (in other words the minWidth overrides the constraint).
 * 
 * When the Widget is Alignable we set the x and y positions according to their specified alignment.
 * 
 * This functionality is similar to HTML for standard situations. In special cases, such as case 3, our solution
 * diverges from HTML. However we feel that this is not a problem since nobody should use the Page Designer to develop
 * pages which exceed the available width of the page.
 * 
 * @author Gary Hayes
 */
public class HorizontalLayout extends AbstractWidgetLayout {

	/**
	 * Creates a new HorizontalLayout.
	 * 
	 * @param figureContext The context in which the Layout is being used
	 */
	public HorizontalLayout(FigureContext figureContext) {
		super(figureContext);
	}

	/**
	 * Lays out the figure.
	 * 
	 * @param container
	 *            The container to layout
	 */
	@SuppressWarnings("rawtypes")
	public void layout(IFigure container) {
		Point origin = getOrigin(container);
		Rectangle cb = getContainerBounds(container);
		/*if (container instanceof ScrollableWidgetFigure) {
			ScrollableWidgetFigure sf = (ScrollableWidgetFigure) container;
			Point o = sf.getOrigin();
			origin = new Point(o.x, o.y - sf.getYOffset());
			cb = sf.getScrollableBounds();
		}*/
		
		// We are using local coordinates. Thus all calculations are performed
		// with respect to the edge of the container.

		List widgets = buildWidgetList(container);
		int numberOfWidgets = widgets.size();
		if (numberOfWidgets == 0) {
			// Nothing to recalculate
			return;
		}
		
		int width = cb.width;
		if (container instanceof BoxFigure) {
			BoxFigure bf = (BoxFigure) container;
			width = bf.getBoxBoundsWidth();
		}
		
		// Calculate the widths that each child Widget needs to fit correctly
		// into the HorizontalBox. Note that some of container's width may have
		// already been used, for example for displaying the ScrollBars.
		int[] widths = calculateWidths(widgets, width);

		// Calculate the size and position of each figure		
		calculateSizes(widgets, widths, cb.height);
		
		
		calculatePositions(widgets, widths, origin, cb);
		
		// Calculate the bounds of the WidgetFigureContainers.
		// These are calculated from the Bounds of the contained Widgets
		// which have just been set in calculateSizes and calculatePositions
		calculateWidgetFigureContainerBounds(container);
	}
	
	/**
	 * Gets the container bounds.
	 * 
	 * @param container The container
	 * @return Rectangle
	 */
	protected Rectangle getContainerBounds(IFigure container) {
		Rectangle cb = container.getBounds();
		if (container instanceof ScrollableWidgetFigure) {
			ScrollableWidgetFigure sf = (ScrollableWidgetFigure) container;
			cb = sf.getScrollableBounds();
		}
		return cb;
	}
	
	/**
	 * Gets the origin of the figure.
	 * 
	 * @param container The container
	 * @return Point
	 */
	protected Point getOrigin(IFigure container) {
		Point origin = new Point(0, 0);
		if (container instanceof ScrollableWidgetFigure) {
			ScrollableWidgetFigure sf = (ScrollableWidgetFigure) container;
			Point o = sf.getOrigin();
			origin = new Point(o.x, o.y - sf.getYOffset());
		}
		return origin;		
	}

	/**
	 * Calculate the size of each Widget and sets the Bounds. The x and y positions are initialised to 0. These are then
	 * set in {@link HorizontalLayout#calculatePositions}.
	 * 
	 * @param widgets The Widgets to be laid out
	 * @param widths
	 *            The width allocated to each figure
	 * @param containerHeight The height of the container
	 */
	@SuppressWarnings("rawtypes")
	protected void calculateSizes(List widgets, int[] widths, int containerHeight) {
		FigureConstants fc = getFigureConstants();

		for (int i = 0; i < widgets.size(); ++i) {
			if(widgets.get(i) instanceof IWidgetFigure) {
			IWidgetFigure child = (IWidgetFigure) widgets.get(i);

			// Widgets which have a maxWidth of -1 are given all the available
			// space (the spacing has already been taken into account in the calculation).
			// The other Widgets use their maxWidths.
			int width = child.getMaxWidth();
			if (width == -1) {
				width = calculateConstrainedWidth(child, widths[i]);
			}
			// Widgets which have a maxHeight of -1 are given all the available height (minus
			// the spacing). The other Widgets use their maxHeights.
			int height = child.getMaxHeight();
			if (height == -1) {
				height = containerHeight - (fc.getWidgetSpacing() * 2);
			}

			// If the calculated width or height is less than the minimum defined for the
			// Widget update the calculated values with the minimum values. Note that the
			// minWidth takes priority over any constraint which may have been defined.
			int minWidth = child.getMinWidth();
			if (width < minWidth) {
				width = minWidth;
			}
			int minHeight = child.getMinHeight();
			if (height < minHeight) {
				height = minHeight;
			}

			// Initialise the x and y positions to zero. These will be calculated later in
			// calculatePositions.
			Rectangle nb = new Rectangle(0, 0, width, height);
			child.setBounds(nb);
			}
		}
	}
	
	/**
	 * @param widgets The Widgets to be laid out
	 * @param widths The space allocated to each figure
	 * @param containerBounds the bounds of the container
	 * @return delta
	 */
	@SuppressWarnings("rawtypes")
	protected Point calculateDelta(List widgets, int[] widths, Rectangle containerBounds, int maxHeight, int widgetSpacing) {
		return new Point(0,0);
	}

	/**
	 * Calculate the position of each Widget and sets the Bounds. Note that this method assumes that the sizes of each
	 * figure have already been calculated in {@link HorizontalLayout#calculateSizes} and that
	 * their Bounds have been set.
	 * 
	 * @param widgets The Widgets to be laid out
	 * @param widths
	 *            The space allocated to each figure
	 * @param origin The origin of the figure. This is useful when there are scrollbars
	 * @param containerBounds the bounds of the container
	 */
	@SuppressWarnings("rawtypes")
	protected void calculatePositions(List widgets, int[] widths, Point origin, Rectangle containerBounds) {
		FigureConstants fc = getFigureConstants();
		
		int widgetSpacing = fc.getWidgetSpacing();
		
		// First calculate the maximum height of the tallest Widget
		int maxHeight = calculateMaxHeight(widgets);

		Point delta = calculateDelta(widgets, widths, containerBounds, maxHeight, widgetSpacing);
		int xPos = widgetSpacing - origin.x + delta.x;
		int yPos = widgetSpacing - origin.y + delta.y;
		for (int i = 0; i < widgets.size(); ++i) {
			IWidgetFigure child = (IWidgetFigure) widgets.get(i);

			Rectangle b = child.getBounds();
			b.x = calculateXPosition(child, xPos, widths[i]);
			b.y = calculateYPosition(child, yPos, maxHeight);
			child.setBounds(b);

			xPos += widths[i] + fc.getWidgetSpacing();
		}
	}

	/**
	 * Calculates the x-position of a figure given its alignment.
	 * 
	 * @param figure The figure to calculate the x-position for
	 * @param leftPosition The left-most position that the figure can have
	 * @param allocatedWidth The width which has been allocated to the figure
	 * @return int The x-position to give to the figure
	 */
	protected int calculateXPosition(IFigure figure, int leftPosition, int allocatedWidth) {
		if (!(figure instanceof Alignable)) {
			// No special alignment
			return leftPosition;
		}

		String ha = getHorizontalAlignment(figure);

		if (Alignable.LEAD.equalsIgnoreCase(ha)) {
			return leftPosition;
		} else if (Alignable.CENTER.equalsIgnoreCase(ha)) {
			return leftPosition + (allocatedWidth - figure.getBounds().width) / 2;
		} else if (Alignable.TRAIL.equalsIgnoreCase(ha)) {
			return leftPosition  + allocatedWidth - figure.getBounds().width;
		} else {
			// Unknown alignment
			return leftPosition;
		}
	}
	
	/**
	 * Gets the horizontal alignment of the figure.
	 * 
	 * @param figure
	 * 			The figure
	 * @return String The horizontal alignment
	 */
	protected String getHorizontalAlignment(IFigure figure) {
		Alignable af = (Alignable) figure;
		return af.getHorizontalAlignment();
	}
	
	/**
	 * Calculates the y-position of a figure given its alignment.
	 * 
	 * @param figure The figure to calculate the y-position for
	 * @param topPosition The top-most position that the figure can have
	 * @param allocatedHeight The height which has been allocated to the figure
	 * @return int The y-position to give to the figure
	 */
	protected int calculateYPosition(IFigure figure, int topPosition, int allocatedHeight) {
		int centeredPosition = topPosition + (allocatedHeight - figure.getBounds().height) / 2;
		if (!(figure instanceof Alignable)) {
			// No special alignment
			return centeredPosition;
		}

		Alignable af = (Alignable) figure;
		String va = af.getVerticalAlignment();

		if (Alignable.LEAD.equalsIgnoreCase(va)) {
			return topPosition;
		} else if (Alignable.CENTER.equalsIgnoreCase(va)) {
			return centeredPosition;
		} else if (Alignable.TRAIL.equalsIgnoreCase(va)) {
			return topPosition  + allocatedHeight - figure.getBounds().height;
		} else {
			// Unknown alignment
			return centeredPosition;
		}
	}	

	/**
	 * Calculates the width that each Widget is given.
	 * 
	 * @param widgets The WidgetFigures to calculate the spaces for
	 * @return int[] The width to be allocated to each Widget
	 * @param containerWidth The width of the container
	 */
	@SuppressWarnings("rawtypes")
	protected int[] calculateWidths(List widgets, int containerWidth) {
		FigureConstants fc = getFigureConstants();

		int[] spaces = initialiseWidths(widgets);
		if (spaces.length == 0) {
			// Nothing to calculate
			return spaces;
		}

		int numberOfWidgets = widgets.size();

		// The available space is equal to the width of the container minus the spacing.
		// 1 Widget needs two spacings (before and after the Widget).
		// 2 Widgets need three spacings (before, between and after).
		// Thus the total spacing is SPACING * (numberOfWidgets + 1).
		int availableWidth = containerWidth - (fc.getWidgetSpacing() * (numberOfWidgets + 1));
          
		// A glue "pushes" other Widgets to the left and right. In this
		// case all the other Widgets have their minWidths and the glue has the
		// rest. If there are two glue's the first one has the priority
		if (containsGlue(widgets)) {
			calculateWidthsForGlue(widgets, availableWidth, spaces);
			return spaces;
		}
		
		// Some Widgets should not take up any space (for example Hidden Widgets).
		// Unfortunately a simple algorithm allocates them the same amount of space as
		// the other Widgets. Here we test for invisible Widgets and give them a
		// minimum width.
		int numberOfInvisibleWidgets = 0;
		for (int kx=0; kx < spaces.length; kx++) {
			if(widgets.get(kx) instanceof IWidgetFigure) {
				IWidgetFigure wf = (IWidgetFigure) widgets.get(kx);
				if (! wf.isVisualElement()) {
					numberOfInvisibleWidgets++;
					spaces[kx] = wf.getMinWidth();
					availableWidth = availableWidth - spaces[kx];
				}
			}
		}

		// spacePerWidget is the space which we would like to allocate to each Widget.
		int divisor = numberOfWidgets - numberOfInvisibleWidgets;
		if (divisor == 0) {
			divisor = 1;
		}
		
		int spacePerWidget = availableWidth / divisor;
		allocateWidths(widgets, availableWidth, spaces, spacePerWidget);
		return spaces;
	}
	
	/**
	 * True if the List of Widgets contains a Glue figure.
	 * 
	 * @param widgets
	 *            The List of WidgetFigures to test
	 * @return boolean
	 */
	@SuppressWarnings("rawtypes")
	private boolean containsGlue(List widgets) {
		for (Iterator it = widgets.iterator(); it.hasNext();) {
			IFigure child = (IFigure) it.next();
			if (child instanceof Glue) {
				return true;
			}
		}

		// Not found
		return false;
	}

	/**
	 * Calculate the widths when the widgets contains a Glue.
	 * 
	 * @param figures
	 *            The List of figures to allocate the widths to
	 * @param availableWidth
	 *            The amount of width which is available to the Widgets (in other words the total width minus the
	 *            spacings)
	 * @param widths
	 *            The widths to be allocated. This array MUST have the same length as figures
	 */
	@SuppressWarnings("rawtypes")
	private void calculateWidthsForGlue(List figures, int availableWidth, int[] widths) {
		int glueIndex = -1;
		int totalUsedWidths = 0;
		for (int i = 0; i < figures.size(); ++i) {
			IWidgetFigure wf = (IWidgetFigure) figures.get(i);
			// glueIndex == -1 is used to ensure that, in the event that there
			// are two Glues the first Glue is chosen
			if (wf instanceof Glue && glueIndex == -1) {
				glueIndex = i;
			} else {
				int w = wf.getMinWidth();
				widths[i] = w;
				totalUsedWidths += w;
			}
		}

		// Now allocate the space to the Glue
		widths[glueIndex] = availableWidth - totalUsedWidths;
	}

	/**
	 * Creates an array of the same length at the number of children as the List of widgets and initialises each
	 * element to -1.
	 * 
	 * @param widgets The List of Widgets to initialise the widths for
	 * @return int[] The initialised array of spaces
	 */
	@SuppressWarnings("rawtypes")
	private int[] initialiseWidths(List widgets) {
		int size = widgets.size();
		int[] widths = new int[size];

		// Initialise the widths of each element in the array to -1
		for (int i = 0; i < widths.length; ++i) {
			widths[i] = -1;
		}

		return widths;
	}

	/**
	 * This method allocates the width of each Widget. It is a recursive method call.
	 * 
	 * @param figures
	 *            The List of figures to allocate the widths to
	 * @param availableWidth
	 *            The amount of widths which is available to the Widgets (in other words the total width minus the
	 *            spacings)
	 * @param widths
	 *            The widths to be allocated. This array MUST have the same length as figures
	 * @param widthToAllocate
	 *            The width which we would like to allocate to each Widget
	 */
	@SuppressWarnings("rawtypes")
	protected void allocateWidths(List figures, int availableWidth, int[] widths, int widthToAllocate) {
		int numberOfWidgets = widths.length;
		int newWidthToAllocate = 0;

		// Allocate the spaceToAllocate to each Widget. If the spaceToAllocate is too small
		// allocateMinimumSpaces returns the number of spaces which were not OK
		int numberNotOk = allocateMinimumWidths(figures, widths, widthToAllocate);
		int totalNotOk = numberNotOk;
		
		// If at least on Widget was not OK in the previous iteration we need to perform
		// another iteration (unless all the Widgets are not OK)
		while (numberNotOk > 0 && totalNotOk < numberOfWidgets) {
			// allocateMinimumSize sets the space of each Widget whose minimum size
			// exceeds the spaceToAllocate. For all other Widgets the space it still
			// in its initial value of -1.

			// Calculate:
			// The total space already allocated to Widgets
			// The number of Widgets whose space has not yet been allocated
			int totalUsedWidth = 0;
			int figuresNotSet = 0;
			for (int i = 0; i < widths.length; ++i) {
				int w = widths[i];
				if (w != -1) {
					totalUsedWidth += w;
				} else {
					figuresNotSet += 1;
				}
			}

			// Calculate the remaining space. This enables to calculate the newSpaceToAllocate
			// Note that the newSpaceToAllocate is always < the spaceToAllocate since in the previous
			// call to allocateMinimumSpaces we have allocated space to large Widgets
			int remainingWidth = availableWidth - totalUsedWidth;
			if(figuresNotSet > 0) {
			    newWidthToAllocate = remainingWidth / figuresNotSet;
			} else {
			    newWidthToAllocate = remainingWidth ;  
			}
			numberNotOk = allocateMinimumWidths(figures, widths, newWidthToAllocate);
			totalNotOk += numberNotOk;
		}
		if (totalNotOk == 0) {
			// All the Widgets fit into spaceToAllocate. This is the typical scenario
			// whereby the each Widget is allocated the same space
			for (int i = 0; i < widths.length; ++i) {
				// Take into account non-visual elements. These shouldhave the width
				// which has previously been calculated
				if(figures.get(i) instanceof IWidgetFigure) {
				IWidgetFigure wf = (IWidgetFigure) figures.get(i);
				if (wf.isVisualElement()) {
					widths[i] = widthToAllocate;
				}
				}
			}
		} else if (totalNotOk == numberOfWidgets) {
			// None of the Widgets fit into the spaceToAllocate (or newSpaceToAllocate).
			// In this case all the Widgets have already been assigned their minWidths.
		} else {
			// In this scenario some of the Widgets have already been allocated space. The remaining
			// Widgets need to be allocated newSpaceToAllocate (since allocateMinimumSize only
			// allocates the space to those that exceed newSpaceToAllocate).
			for (int i = 0; i < widths.length; ++i) {
				if (widths[i] == -1) {
					widths[i] = newWidthToAllocate;
				}
			}
		}
		
	}

	/**
	 * Compares the widthToAllocate to the minWidth of the Widget. If the minWidth exceeds the spaceToAllocate this
	 * method
	 * 
	 * @param figures
	 *            The List of figures to allocate the space to
	 * @param widths
	 *            The widths to be allocated. This array MUST have the same length as figures
	 * @param widthToAllocate
	 *            The width which we would like to allocate to each Widget
	 * @return The number of figures whose minWidth exceeded the spaceToAllocate
	 */
	@SuppressWarnings("rawtypes")
	protected int allocateMinimumWidths(List figures, int[] widths, int widthToAllocate) {
		int numberNotOk = 0;
		for (int i = 0; i < widths.length; ++i) {
			if (widths[i] != -1) {
				// Already allocated. We don't want to allocate the space a second time
				continue;
			}
			if(figures.get(i) instanceof IWidgetFigure) {
			IWidgetFigure wf = (IWidgetFigure) figures.get(i);
			int minWidth = wf.getMinWidth();
			if (minWidth > widthToAllocate) {
				widths[i] = minWidth;
				numberNotOk += 1;
			}
			}
		}

		return numberNotOk;
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
	@SuppressWarnings("rawtypes")
	public int calculateIndex(IFigure container, Point location) {
		Point p = PointUtils.convertToLocalCoordinates(container, location);

		List children = container.getChildren();
		for (int i = 0; i < children.size(); ++i) {
			IFigure child = (IFigure) children.get(i);
			Rectangle r = child.getBounds();
			int xPos = r.x;

			if (xPos > p.x) {
				// The user has clicked before this Widget
				return i;
			}
		}

		// The Widget should be added to the end of the collection
		return children.size();
	}

	/**
	 * Gets the constrainted width given the allocated space. Note that we only use the constraint if it lies within
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
	@SuppressWarnings("rawtypes")
	protected void calculateWidgetFigureContainerBounds(IFigure container) {
		for (Iterator it = container.getChildren().iterator(); it.hasNext();) {
			Object obj = it.next();
			if(obj instanceof IWidgetFigure) {
			IWidgetFigure child = (IWidgetFigure) obj;
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
			
				int width = lfb.x + lfb.width - ffb.x;
				int height = calculateMaxHeight(wf);
				
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
	}	
	
	/**
	 * Calculates the maximum height of the tallest child figure.
	 * 
	 * @param widgets The List of Widgets to calculate the maximum height for
	 * @return int The maximum height of the tallest child figure
	 */
	@SuppressWarnings("rawtypes")
	protected int calculateMaxHeight(List widgets) {
		int maxHeight = 0;
		for (Iterator it = widgets.iterator(); it.hasNext();) {
			IFigure child = (IFigure) it.next();
			maxHeight = Math.max(maxHeight, child.getBounds().height);
		}
		
		return maxHeight;
	}	
}
