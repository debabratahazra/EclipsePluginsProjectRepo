package com.odcgroup.page.ui.figure.table;

import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

import com.odcgroup.page.ui.figure.AbstractWidgetLayout;
import com.odcgroup.page.ui.figure.Constrainable;
import com.odcgroup.page.ui.figure.FigureConstants;
import com.odcgroup.page.ui.figure.FigureContext;
import com.odcgroup.page.ui.figure.IWidgetFigure;
import com.odcgroup.page.ui.figure.VerticalLayout;

/**
 * Layout for the table figure it's calculate the bounds of TableHeader figure
 * and the TableBody
 * 
 * @author Alexandre Jaquet
 *
 */
@SuppressWarnings("rawtypes")
public class TableLayout extends AbstractWidgetLayout {	
	
	/** Height constant integer value. */
	public final static int SPACING_VALUE = 15;
	
	/** The x position of the figures. */
	private static int X_POS = 8;
	
	/** The y position of the figures. */
	private static int Y_POS = 8;

	/**
	 * Creates a new VerticalLayout.
	 * 
	 * @param figureContext The context in which the Layout is being used
	 */
	public TableLayout(FigureContext figureContext) {
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
	}
	
	/**
	 * Calculate the size of each Widget and sets the Bounds. The x and y positions are initialised to 0. These are then
	 * set in {@link VerticalLayout#calculatePositions}.
	 * 
	 * @param widgets The Widgets to be laid out
	 * @param containerWidth The width of the container
	 */
	private void calculateSizes(List widgets, int containerWidth) {
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
			width = width - SPACING_VALUE;
			// Initialize the x and y positions to zero. These will be calculated later in
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
	 * @param figures The Figures to be laid out
	 * @param origin The origin of the figure. This is useful when there are scrollbars
	 */
	private void calculatePositions(List<IFigure> figures, Point origin) {

		// We begin to set the x and y position of the figure 
		// to add the correct spacing between the table container
		// and the figures.
		int xPos = X_POS;
		int yPos = Y_POS - origin.y;
		for (IFigure w : figures) {
			Rectangle bounds = w.getBounds();
			w.setBounds(new Rectangle(xPos, yPos, bounds.width, bounds.height));
			yPos += bounds.height;
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
		return -1;
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

}
