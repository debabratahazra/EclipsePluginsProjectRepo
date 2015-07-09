package com.odcgroup.page.ui.figure.table;

import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.PageUIPlugin;
import com.odcgroup.page.ui.edit.PaintUtils;
import com.odcgroup.page.ui.figure.AbstractAlignableWidgetFigure;
import com.odcgroup.page.ui.figure.AbstractWidgetLayout;
import com.odcgroup.page.ui.figure.FigureConstants;
import com.odcgroup.page.ui.figure.FigureContext;
import com.odcgroup.page.ui.figure.IWidgetFigure;

/**
 * Represents the header of a Column.
 * 
 * @author Gary Hayes 
 * @author Alexandre Jaquet
 */
public class ColumnHeader extends AbstractAlignableWidgetFigure {

	/** The spacing between figures. */
	private int SPACING = 4;
	
	/** The width of the sort figure. */
	private static int SORT_FIGURE_WIDTH = 8;
	
	/** The position of the sort figure from the end of the Column Header. */
	private static int SORT_FIGURE_POSITION = 21;
        private static final String COLUMNHEADER_FOREGROUND_COLOR = "COLUMNHEADER_FOREGROUND_COLOR";
	/**
	 * Creates a new ColumnFigure.
	 * 
	 * @param widget
	 *            The Widget being displayed by this Figure
	 * @param figureContext
	 *            The context providing information required for displaying the page correctly
	 */
	public ColumnHeader(Widget widget, FigureContext figureContext) {
		super(widget, figureContext);
		setLayoutManager(new ColumnHeaderLayoutManager(figureContext));
		initialize();
		
	}

	/**
	 * Gets the minimum height of the figure. The layouts always set the height to be greater than or equal to the
	 * minimum height.
	 * 
	 * @return int The minimum width of the figure
	 */
	public int getMinHeight() {
		if (!showColumnHeader()) {
			return 0;
		}
		return 16;
	}

	/**
	 * Gets the minimum width of the figure. The layouts always set the width to be greater than or equal to the minimum
	 * width.
	 * 
	 * @return int The minimum width of the figure
	 */
	public int getMinWidth() {
		if (!showColumnHeader()) {
			return 0;
		}

		int minWidth = calculateTextSize(getText()).width;

		for (Iterator it = getChildren().iterator(); it.hasNext();) {
			IWidgetFigure awf = (IWidgetFigure) it.next();
			int mw = awf.getMinWidth();
			if (mw > 0) {
				minWidth += mw + SPACING;
			}
		}
				
		if (hasSortDirection()) {
			// The 4 allows for a gap between the caption and the sort triangle
			minWidth += SORT_FIGURE_WIDTH + SORT_FIGURE_POSITION + 4;
		}

		return minWidth + PaintUtils.getWidth(getWidget());
	}

	/**
	 * Gets the maximum width of the figure. By default this is equal to the minimum width. Returning -1 indicates that
	 * the Figure does not have a maximum width and can be resized.
	 * 
	 * @return int The maximum width of the figure
	 */
	public int getMaxWidth() {
		return -1;
	}

	/**
	 * Gets the width of the caption of the column.
	 * 
	 * @return The width of the caption of the column
	 */
	private int getTextWidth() {
		return calculateTextSize(getText()).width;
	}

	/**
	 * Override the base-class version to draw the column.
	 * 
	 * @param graphics
	 *            The graphics context
	 */
	public void paintSpecificFigure(Graphics graphics) {
	    int x=0;
		 PaintUtils.paintIcons(getWidget(), graphics,getMinWidth() - PaintUtils.getWidth(getWidget()));
		if (!showColumnHeader()) {
			return;
		}

		FigureConstants fc = getFigureConstants();
		Color forgroundColor = fc.getColumnBackgroundColor();
		graphics.setBackgroundColor(forgroundColor);
		graphics.fillRectangle(x, 0, getBounds().width, getBounds().height);
		drawCaption(graphics);
		drawSortDirection(graphics);
	}

	/**
	 * Draw the caption of the column.
	 * 
	 * @param graphics
	 *            The graphics context
	 */
	private void drawCaption(Graphics graphics) {
		graphics.drawText(getText(), getXPosition(getText()), 0);
	}
	
	/**
	 * Draws the sort direction arrow.
	 * 
	 * @param graphics The Graphics context
	 */
	private void drawSortDirection(Graphics graphics) {
		if (hasSortDirection()) {
			String s = getSortDirection();		
			Rectangle b = getBounds();
			// 	Translate the graphics to just before the end of the Widget
			graphics.translate(b.width - SORT_FIGURE_POSITION, 4);				
			graphics.setForegroundColor(PageUIPlugin.getColor(COLUMNHEADER_FOREGROUND_COLOR));
			if (s.equals(PropertyTypeConstants.SORT_ASCENDING)) {
				// draw a "up" triangle
				int [] triangle = { SORT_FIGURE_WIDTH / 2, 0, SORT_FIGURE_WIDTH, SORT_FIGURE_WIDTH, 0, SORT_FIGURE_WIDTH}; 
				graphics.drawPolygon(triangle);
			} else {
				// draw a "down" triangle
				int [] triangle = { 0, 0, SORT_FIGURE_WIDTH, 0, SORT_FIGURE_WIDTH / 2, SORT_FIGURE_WIDTH}; 
				graphics.drawPolygon(triangle);
			}
			graphics.drawText(getSortRank(), SORT_FIGURE_WIDTH + 3, -3);
			// Compensate the previous translation
			graphics.translate(-b.width + SORT_FIGURE_POSITION, -4);
		}	
	}

	/**
	 * Get the bounds to draw the caption or the checkbox. It's must be on the center of the column.
	 * 
	 * @param text
	 *            The column header caption
	 * @return int The X position
	 */
	private int getXPosition(String text) {
		int childWidth = calculateTextSize(getText()).width;
		Rectangle bounds = getBounds();
		int width = (bounds.width - childWidth) / 2;
		return width;
	}

	/**
	 * Manager to layout the children of the ColumnHeader
	 * 
	 * @author Gary Hayes
	 * 
	 */
	private class ColumnHeaderLayoutManager extends AbstractWidgetLayout {

		/**
		 * Creates a new ColumnHeaderLayoutManager.
		 * 
		 * @param figureContext
		 *            The context in which the Layout is being used
		 */
		public ColumnHeaderLayoutManager(FigureContext figureContext) {
			super(figureContext);
		}

		/**
		 * Layout the given figure.
		 * 
		 * @param figure
		 *            The figure to layout
		 */
		public void layout(IFigure figure) {
			ColumnHeader header = (ColumnHeader) figure;
			int textWidth = header.getTextWidth();
			Rectangle b = header.getBounds();
			for (Iterator it = header.getChildren().iterator(); it.hasNext();) {
				IWidgetFigure awf = (IWidgetFigure) it.next();
				awf.setBounds(new Rectangle(((b.width + textWidth) / 2) + b.x + SPACING, b.y, awf.getMinWidth(), awf
						.getMinHeight()));
			}

		}

		/**
		 * Calculate the index of the container.
		 * 
		 * @param container
		 *            The container
		 * @param location
		 *            The location
		 * @return int
		 */
		public int calculateIndex(IFigure container, Point location) {
			return 0;
		}
	}

	/**
	 * Returns true if the ColumnHedaers should be shown or not. Note that the ColumnHeader is ALWAYS shown in Design
	 * Mode otherwise the user would not be able to modify its properties.
	 * 
	 * @return boolean True if the ColumnHeader should be shown or not
	 */
	private boolean showColumnHeader() {
		if (getFigureContext().isDesignMode()) {
			return true;
		}

		Widget table = getTableWidget();
		String s = table.getPropertyValue(PropertyTypeConstants.SHOW_COLUMN_HEADER);
		if ("true".equals(s)) {
			return true;
		}
		return false;
	}

	/**
	 * Returns "ascending" if this is the default sort column AND the direction is ascending. Returns "descending" if
	 * this is the default sort column AND the direction is descending. Returns an empty String otherwise.
	 * 
	 * @return String
	 */
	private String getSortDirection() {	
		String sortDirection = getWidget().getPropertyValue(PropertyTypeConstants.DEFAULT_SORT_DIRECTION);
		return sortDirection;
	}

	/** 
	 * Returns the rank of the current ColumnHeader
	 * 
	 * @return String returns the rank of the specified Column
	 * 	
	 */
	private String getSortRank() {
		String rank = this.getWidget().getPropertyValue(PropertyTypeConstants.DEFAULT_SORT_RANK);
		return rank;
	}
	
	
	/**
	 * Does we have a sort direction ?
	 * 
	 * @return boolean returns true if the ColumnHeader have a sort direction set
	 */
	private boolean hasSortDirection() {
		String sortDirection = this.getWidget().getPropertyValue(PropertyTypeConstants.DEFAULT_SORT_RANK);
		if (!StringUtils.isEmpty(sortDirection) && ! sortDirection.equals("0")) {
				return true;
			}
		return false;
	}
	/**
	 * Gets the parent Table.
	 * 
	 * @return Widget The parent Table
	 */
	private Widget getTableWidget() {
		// The Table is the grand-parent of the ColumnHeader
		return getWidget().getParent().getParent();
	}
	/**
	 * initialize method to register color.
	 */
	private void initialize(){
	    PageUIPlugin.setColorInRegistry(COLUMNHEADER_FOREGROUND_COLOR, new RGB( 232, 232, 232));
	}
}