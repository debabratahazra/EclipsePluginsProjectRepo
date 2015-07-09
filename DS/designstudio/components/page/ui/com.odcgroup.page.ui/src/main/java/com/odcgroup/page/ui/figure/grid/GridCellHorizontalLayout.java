package com.odcgroup.page.ui.figure.grid;

import java.util.List;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.symbols.SymbolsExpander;
import com.odcgroup.page.model.symbols.SymbolsExpanderFactory;
import com.odcgroup.page.ui.figure.BoxFigure;
import com.odcgroup.page.ui.figure.FigureContext;
import com.odcgroup.page.ui.figure.HorizontalLayout;
import com.odcgroup.page.ui.figure.IWidgetFigure;
import com.odcgroup.page.ui.figure.IncludeFigure;

/**
 * @author atr
 * @since DS 1.40.0
 */
public abstract class GridCellHorizontalLayout extends HorizontalLayout {
	
	protected abstract BoxFigure getBoxFigure();
	
	/**
	 * @param widgets
	 * @param widths
	 * @param containerBounds
	 * @param maxHeight
	 * @param widgetSpacing
	 * @return
	 */
	protected Point calculateDelta(List widgets, int[] widths, Rectangle containerBounds, int maxHeight, int widgetSpacing) {
		Point delta = new Point();
		IWidgetFigure firstFig = (IWidgetFigure)widgets.get(0);
		Property prop = firstFig.getWidget().findProperty(PropertyTypeConstants.HORIZONTAL_ALIGNMENT);
		if (prop != null) {
			String align = prop.getValue().trim().toLowerCase();
	        SymbolsExpander expander = SymbolsExpanderFactory.getSymbolsExpander();
	        if (expander != null) {
	        	align = expander.substitute(align, firstFig.getWidget());
	        }
	        if (! "lead".equals(align)) {
				int totalWidgetsWidth = +widgetSpacing;
				for (int kx=0; kx < widths.length;kx++) {
					totalWidgetsWidth += widths[kx] + widgetSpacing;
				}
				delta.x = containerBounds.width - totalWidgetsWidth;
				if ("center".equals(align)) {
					delta.x = delta.x / 2;
				}
			}
		}
	
		prop = firstFig.getWidget().findProperty(PropertyTypeConstants.VERTICAL_ALIGNMENT);
		if (prop != null) {
			String align = prop.getValue().trim().toLowerCase();
			if (! "lead".equals(align)) {
				delta.y = containerBounds.height - maxHeight - 2*widgetSpacing;
				if ("center".equals(align)) {
					delta.y = delta.y / 2;
				}
			}
		}
		return delta;
	}

	/* 
	 * @see com.odcgroup.page.ui.figure.HorizontalLayout#allocateWidths(java.util.List, int, int[], int)
	 */
	protected void allocateWidths(List figures, int availableWidth, int[] widths, int widthToAllocate) {
		
		/** EST-IL POSSIBLE D'OPTIMISER L'ALGORITHME, TROP LENT POUR LA GRID/CELL */
		
		int numberOfWidgets = widths.length;
		int newWidthToAllocate = 0;
		
		BoxFigure fig = getBoxFigure();

		// Allocate the spaceToAllocate to each Widget. If the spaceToAllocate is too small
		// allocateMinimumSpaces returns the number of spaces which were not OK
		int numberNotOk = allocateMinimumWidths(figures, widths, widthToAllocate);
		int totalNotOk = numberNotOk;
		
		// number of boxes
		int widthAllocated = 0;
		int nbBoxFigures = 0;
		for (int i = 0; i < widths.length; ++i) {
			IWidgetFigure wf = (IWidgetFigure) figures.get(i);
			if ((wf instanceof BoxFigure) || (wf instanceof IncludeFigure)) {
				nbBoxFigures++;
				widths[i] = -1;
			} else {
				widthAllocated += widths[i];
			}
		}

		if (nbBoxFigures > 0) { 
			int boxWidth = (availableWidth - widthAllocated) / nbBoxFigures;
			for (int i = 0; i < widths.length; ++i) {
				if (widths[i] == -1) {
					widths[i] = boxWidth;
				}
			}		
		}
		
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
			if (remainingWidth > 0) {
				if (figuresNotSet == 0) {
					figuresNotSet = numberNotOk;
				}
				newWidthToAllocate = remainingWidth / figuresNotSet;
				numberNotOk = allocateMinimumWidths(figures, widths, newWidthToAllocate);
			}
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
					if (widths[i] == -1) {
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
	 * Compares the widthToAllocate to the minWidth of the Widget. 
	 * If the minWidth exceeds the spaceToAllocate this method
	 * 
	 * @param figures
	 *            The List of figures to allocate the space to
	 * @param widths
	 *            The widths to be allocated. This array MUST have the same length as figures
	 * @param widthToAllocate
	 *            The width which we would like to allocate to each Widget
	 * @return The number of figures whose minWidth exceeded the spaceToAllocate
	 */
	protected int allocateMinimumWidths(List figures, int[] widths, int widthToAllocate) {
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
			} else {
				widths[i] = minWidth;
			}
		}

		return numberNotOk;
	}	
	
	/**
	 * @param figureContext
	 */
	public GridCellHorizontalLayout(FigureContext figureContext) {
		super(figureContext);
	}

}
