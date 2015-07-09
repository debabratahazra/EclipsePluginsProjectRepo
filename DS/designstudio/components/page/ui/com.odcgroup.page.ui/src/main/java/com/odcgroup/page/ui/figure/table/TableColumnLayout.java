package com.odcgroup.page.ui.figure.table;

import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.widgets.table.ITableColumnItem;
import com.odcgroup.page.ui.figure.Alignable;
import com.odcgroup.page.ui.figure.BoxFigure;
import com.odcgroup.page.ui.figure.FigureConstants;
import com.odcgroup.page.ui.figure.FigureContext;
import com.odcgroup.page.ui.figure.HorizontalLayout;
import com.odcgroup.page.ui.figure.IWidgetFigure;

/**
 * @author pkk
 *
 */
@SuppressWarnings("rawtypes")
public class TableColumnLayout extends HorizontalLayout {
	
	/**
	 * @param figureContext
	 */
	public TableColumnLayout(FigureContext figureContext) {
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
		Rectangle cb = getContainerBounds(container);
		if (container instanceof TableColumnFigure) {
			TableColumnFigure sf = (TableColumnFigure) container;
			Point o = sf.getOrigin();
			origin = new Point(o.x, o.y - sf.getColumnItemY());
			cb = sf.getScrollableBounds();
		}		
		
		// We are using local coordinates. Thus all calculations are performed with
		// respect to the edge of the container.
		
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

	/* (non-Javadoc)
	 * @see com.odcgroup.page.ui.figure.HorizontalLayout#calculatePositions(java.util.List, int[], org.eclipse.draw2d.geometry.Point, org.eclipse.draw2d.geometry.Rectangle)
	 */
	protected void calculatePositions(List widgets, int[] widths, Point origin, Rectangle containerBounds) {
		FigureConstants fc = getFigureConstants();
		int widgetSpacing = fc.getWidgetSpacing();
		
		// First calculate the maximum height of the tallest Widget
		int maxHeight = calculateMaxHeight(widgets);

		Point delta = calculateDelta(widgets, widths, containerBounds, maxHeight, widgetSpacing);
		int xPos = widgetSpacing - origin.x + delta.x;
		int yPos = widgetSpacing - origin.y + delta.y;
		int rightpos = widgetSpacing - origin.x + delta.x+containerBounds.width;
		boolean tableGrPresentStatus = false;

		int colheight = 0;
		for (int i = 0; i < widgets.size(); ++i) {
			IWidgetFigure child = (IWidgetFigure) widgets.get(i);
			if(child instanceof CompartmentFigure) {
				xPos = child.getBounds().x;
			}
			Rectangle b = child.getBounds();
			boolean newline = isNewLine(child);
			if (i ==0 || tableGrPresentStatus) {
				yPos += Math.round(b.height/2);				
				yPos += fc.getWidgetSpacing();
				if(tableGrPresentStatus) {
					yPos += fc.getWidgetSpacing()*2;
				}
				xPos = widgetSpacing - origin.x + delta.x;
				tableGrPresentStatus = false;
			} else {
				int width = 0;
				if (i>0) {
					width = widths[i-1];
				}
				if (!newline) {
					colheight = Math.max(colheight, child.getMinHeight()+widgetSpacing*2);
				}
				xPos += width + fc.getWidgetSpacing();				
			}

			if (newline) {
				yPos += colheight;
				maxHeight -= colheight - child.getMinWidth() - 2*widgetSpacing;
				xPos = widgetSpacing - origin.x + delta.x;
				colheight = 0;
			}
			b.x = calculateXPosition(child, xPos, rightpos, widths[i]);
			b.y = calculateYPosition(child, yPos, maxHeight);
			if(child instanceof CompartmentFigure) {
				tableGrPresentStatus = true;
			}
			child.setBounds(b);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.page.ui.figure.HorizontalLayout#calculateXPosition(org.eclipse.draw2d.IFigure, int, int)
	 */
	protected int calculateXPosition(IFigure figure, int leftPosition, int rightPosition, int allocatedWidth) {
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
	 * @param figure
	 * @return
	 */
	private boolean isNewLine(IWidgetFigure figure) {
		if (figure instanceof TableColumnItem) {
			ITableColumnItem item = TableHelper.getTableColumnItem(figure.getWidget());
			return item.isNewLine();
		} 
		return false;
	}
	
	

}
