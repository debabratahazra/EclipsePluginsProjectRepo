package com.odcgroup.page.ui.figure.table;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.figure.AbstractWidgetLayout;
import com.odcgroup.page.ui.figure.Constrainable;
import com.odcgroup.page.ui.figure.FigureConstants;
import com.odcgroup.page.ui.figure.FigureContext;
import com.odcgroup.page.ui.figure.HorizontalLayout;
import com.odcgroup.page.ui.figure.IWidgetFigure;
import com.odcgroup.page.ui.figure.WidgetFigureContainer;
import com.odcgroup.page.ui.util.PointUtils;

/**
 * The Layout for a Table body.
 * 
 * @author Gary Hayes
 */
public class TableBodyLayout extends AbstractWidgetLayout {
	
	/**
	 * Creates a new TableLayout.
	 * 
	 * @param figureContext The context in which the Layout is being used
	 */
	public TableBodyLayout(FigureContext figureContext) {
		super(figureContext);
	}


	/**
	 * Lays out the figure.
	 * 
	 * @param container
	 *            The container to layout
	 */
	public void layout(IFigure container) {
		// We are using local coordinates. Thus all calculations are performed
		// with respect to the edge of the container
		List widgets = getVisibleChildren(container);
		
		
		int numberOfWidgets = widgets.size();
		if (numberOfWidgets == 0) {
			// Nothing to recalculate
			return;
		}
		
		Rectangle cb = container.getBounds();
		
		// Calculate the widths that each child Widget needs to fit correctly
		// into the HorizontalBox. Note that some of container's width may have
		// already been used, for example for displaying the ScrollBars.
		int[] widths = calculateWidths(widgets, cb.width);

		// Calculate the size and position of each figure		
		calculateWidths(widgets, widths);
		calculateHeights(widgets, cb.height);
		calculatePositions(widgets, widths);
		
		// Calculate the bounds of the WidgetFigureContainers.
		// These are calculated from the Bounds of the contained Widgets
		// which have just been set in calculateSizes and calculatePositions
		calculateWidgetFigureContainerBounds(container);
	}

	/**
	 * Calculate the width of each Widget and sets the Bounds. The x and y positions are initialised to 0. These are then
	 * set in {@link TableBodyLayout#calculatePositions}.
	 * 
	 * @param widgets The Widgets to be laid out
	 * @param widths
	 *            The width allocated to each figure
	 */
	private void calculateWidths(List widgets, int[] widths) {
		for (int i = 0; i < widgets.size(); ++i) {
			IWidgetFigure child = (IWidgetFigure) widgets.get(i);

			// Widgets which have a maxWidth of -1 are given all the available
			// space (the spacing has already been taken into account in the calculation).
			// The other Widgets use their maxWidths.
			int width = child.getMaxWidth();
			if (width == -1) {
				width = calculateConstrainedWidth(child, widths[i]);
			}

			// If the calculated width or height is less than the minimum defined for the
			// Widget update the calculated values with the minimum values. Note that the
			// minWidth takes priority over any constraint which may have been defined.
			int minWidth = child.getMinWidth();
			if (width < minWidth) {
				width = minWidth;
			}

			// Initialise the x and y positions to zero. These will be calculated later in
			// calculatePositions.
			Rectangle nb = new Rectangle(0, 0, width, 0);
			child.setBounds(nb);
		}
	}
	
	/**
	 * Calculate the height of each Widget and sets the Bounds. The x and y positions are initialised to 0. These are then
	 * set in {@link HorizontalLayout#calculatePositions}.
	 * 
	 * @param widgets The Widgets to be laid out
	 * @param containerHeight The height of the container
	 */
	private void calculateHeights(List widgets, int containerHeight) {
		FigureConstants fc = getFigureConstants();
		
		// First calculate the largest minimum height required for a Column.
		int largestMinHeight = 0;
		for (int i = 0; i < widgets.size(); ++i) {
			IWidgetFigure child = (IWidgetFigure) widgets.get(i);
			largestMinHeight = Math.max(largestMinHeight, child.getMinHeight());
		}	

		for (int i = 0; i < widgets.size(); ++i) {
			IWidgetFigure child = (IWidgetFigure) widgets.get(i);

			// Widgets which have a maxHeight of -1 are given all the available height (minus
			// the spacing). The other Widgets use their maxHeights.
			int height = child.getMaxHeight();
			if (height == -1) {
				height = containerHeight - (fc.getWidgetSpacing() * 2);
			}

			if (height < largestMinHeight) {
				height = largestMinHeight;
			}

			Rectangle b = child.getBounds();
			b.height = height;
		}
	}	

	/**
	 * Calculate the position of each Widget and sets the Bounds. Note that this method assumes that the sizes of each
	 * figure have already been calculated in {@link HorizontalLayout#calculateSizes} and that
	 * their Bounds have been set.
	 * 
	 * @param widgets The Widgets to be laid out
	 * @param widths
	 *            The space allocated to each figure
	 */
	private void calculatePositions(List widgets, int[] widths) {
		FigureConstants fc = getFigureConstants();
		
		// First calculate the maximum height of the tallest Widget
		int maxHeight = calculateMaxHeight(widgets);

		int xPos = fc.getWidgetSpacing();
		int yPos = fc.getWidgetSpacing();
		for (int i = 0; i < widgets.size(); ++i) {
			IWidgetFigure child = (IWidgetFigure) widgets.get(i);

			Rectangle b = child.getBounds();
			b.x = xPos;
			b.y = yPos + (maxHeight - child.getBounds().height) / 2;
			child.setBounds(b);

			xPos += widths[i] + fc.getWidgetSpacing();
		}
	}	

	/**
	 * Calculates the width that each Widget is given.
	 * 
	 * @param widgets The WidgetFigures to calculate the spaces for
	 * @return int[] The width to be allocated to each Widget
	 * @param containerWidth The width of the container
	 */
	private int[] calculateWidths(List widgets, int containerWidth) {
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
		
		// Some Widgets should not take up any space (for example Hidden Widgets).
		// Unfortunately a simple algorithm allocates them the same amount of space as
		// the other Widgets. Here we test for invisible Widgets and give them a
		// minimum width.
		int numberOfInvisibleWidgets = 0;
		for (int kx=0; kx < spaces.length; kx++) {
			IWidgetFigure wf = (IWidgetFigure) widgets.get(kx);
			if (! wf.isVisualElement()) {
				numberOfInvisibleWidgets++;
				spaces[kx] = wf.getMinWidth();
				availableWidth = availableWidth - wf.getMinWidth();
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
	 * Creates an array of the same length at the number of children as the List of widgets and initialises each
	 * element to -1.
	 * 
	 * @param widgets The List of Widgets to initialise the widths for
	 * @return int[] The initialised array of spaces
	 */
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
	private void allocateWidths(List figures, int availableWidth, int[] widths, int widthToAllocate) {
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
			newWidthToAllocate = remainingWidth / figuresNotSet;
			numberNotOk = allocateMinimumWidths(figures, widths, newWidthToAllocate);
			totalNotOk += numberNotOk;
		}

		if (totalNotOk == 0) {
			// All the Widgets fit into spaceToAllocate. This is the typical scenario
			// whereby the each Widget is allocated the same space
			for (int i = 0; i < widths.length; ++i) {
				// Take into account non-visual elements. These shouldhave the width
				// which has previously been calculated
				IWidgetFigure wf = (IWidgetFigure) figures.get(i);
				if (wf.isVisualElement()) {
					widths[i] = widthToAllocate;
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
	private int allocateMinimumWidths(List figures, int[] widths, int widthToAllocate) {
		int numberNotOk = 0;
		for (int i = 0; i < widths.length; ++i) {
			if (widths[i] != -1) {
				// Already allocated. We don't want to allocate the space a second time
				continue;
			}

			IWidgetFigure wf = (IWidgetFigure) figures.get(i);
			int minWidth = wf.getMinWidth();
			if (minWidth > widthToAllocate) {
				widths[i] = minWidth;
				numberNotOk += 1;
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
	private void calculateWidgetFigureContainerBounds(IFigure container) {
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
	
	/**
	 * Calculates the maximum height of the tallest child figure.
	 * 
	 * @param widgets The List of Widgets to calculate the maximum height for
	 * @return int The maximum height of the tallest child figure
	 */
	private int calculateMaxHeight(List widgets) {
		int maxHeight = 0;
		for (Iterator it = widgets.iterator(); it.hasNext();) {
			IFigure child = (IFigure) it.next();
			maxHeight = Math.max(maxHeight, child.getBounds().height);
		}
		
		return maxHeight;
	}	
	
	
	/**
	 * Gets the child Columns which are visible.
	 * 
	 * @param container The parent Container (TableBody)
	 * @return List of Widget's
	 */
	public List getVisibleChildren(IFigure container) {
		List children = container.getChildren();
		if (getFigureContext().isDesignMode()) {
			return children;
		} else {
			List result = new ArrayList();
			for (int i = 0; i < children.size(); ++i) {
				IWidgetFigure awf = (IWidgetFigure) children.get(i);
				Widget column = awf.getWidget();
				Widget header = column.getContents().get(0);
				if ("true".equals(header.getPropertyValue(PropertyTypeConstants.VISIBLE))) {
					result.add(awf);
				}
			}
			return result;
		}
	}	
}