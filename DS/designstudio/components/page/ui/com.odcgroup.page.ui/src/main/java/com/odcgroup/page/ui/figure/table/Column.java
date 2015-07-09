package com.odcgroup.page.ui.figure.table;

import java.util.Iterator;

import org.eclipse.draw2d.Graphics;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.edit.PaintUtils;
import com.odcgroup.page.ui.figure.AbstractAlignableWidgetFigure;
import com.odcgroup.page.ui.figure.FigureConstants;
import com.odcgroup.page.ui.figure.FigureContext;
import com.odcgroup.page.ui.figure.IWidgetFigure;

/**
 * Represents a Column.
 * 
 * @author Gary Hayes
 */
public class Column extends AbstractAlignableWidgetFigure {

	/**
	 * Creates a new ColumnFigure.
	 * 
	 * @param widget
	 *            The Widget being displayed by this Figure
	 * @param figureContext
	 *            The context providing information required for displaying the page correctly
	 */
	public Column(Widget widget, FigureContext figureContext) {
		super(widget, figureContext);
		
		setLayoutManager(new ColumnLayout(figureContext));
	}
	
	/**
	 * Gets the minimum width of the figure.
	 * 
	 * @return int The minimum width of the figure
	 */
	public int getMinWidth() {
		int width = PaintUtils.getWidth(getWidget());
		for (Iterator it = getChildren().iterator(); it.hasNext();) {
			IWidgetFigure child = (IWidgetFigure) it.next();
			width = Math.max(width, child.getMinWidth());

		}

		if (getChildren().size() > 0) {
			// Allow a spacing before and after the widest Widget
			width += getFigureConstants().getWidgetSpacing() * 2;
		}

		// We need to provide a minWidth for the box otherwise we would
		// not be able to add Widgets to add
		FigureConstants fc = getFigureConstants();
		if (width < fc.getSimpleWidgetDefaultWidth()) {
			width = fc.getSimpleWidgetDefaultWidth();
		}
		return width;
	}

	/**
	 * Gets the minimum height of the figure.
	 * 
	 * @return int The minimum height of the figure
	 */
	public int getMinHeight() {
		int height = 0;
		for (Iterator it = getChildren().iterator(); it.hasNext();) {
			IWidgetFigure child = (IWidgetFigure) it.next();
			height += child.getMinHeight();
		}

		if (getChildren().size() > 0) {
			// Allow a space above, between and below each Widget
			height += getFigureConstants().getWidgetSpacing()
					* (getChildren().size() + 1);
		}

		// We need to provide a minWidth for the box otherwise we would
		// not be able to add Widgets to add
		FigureConstants fc = getFigureConstants();
		if (height < fc.getSimpleWidgetDefaultHeight()) {
			height = fc.getSimpleWidgetDefaultHeight();
		}
		return height;
	}

	/**
	 * Gets the maximum width of the figure. HorizontalBoxes do not have maximum
	 * widths. Instead they can expand to fill the entire width of the parent
	 * container.
	 * 
	 * @return int The maximum width of the figure
	 */
	public int getMaxWidth() {
		return -1;
	}

	/**
	 * Gets the maximum height of the figure.
	 * 
	 * @return int The maximum height of the figure
	 */
	public int getMaxHeight() {
		int height = 0;
		for (Iterator it = getChildren().iterator(); it.hasNext();) {
			IWidgetFigure child = (IWidgetFigure) it.next();
			height += child.getMaxHeight();
		}

		if (getChildren().size() > 0) {
			// Allow a space above, between and below each Widget
			height += getFigureConstants().getWidgetSpacing()
					* (getChildren().size() + 1);
		}

		// We need to provide a minWidth for the box otherwise we would
		// not be able to add Widgets to add
		FigureConstants fc = getFigureConstants();
		if (height < fc.getSimpleWidgetDefaultHeight()) {
			height = fc.getSimpleWidgetDefaultHeight();
		}
		return height;
	}	
	
	/**
	 * Gets the vertical alignment.
	 * 
	 * @return String The vertical alignment
	 */
	public String getVerticalAlignment() {
		return LEAD; 
	}
	
	/**
	 * Returns true since we wish to use local coordinates.
	 * 
	 * @return boolean True since we wish to use local coordinates
	 */
	protected boolean useLocalCoordinates() {
		return true;
	}	
	
	/**
	 * Paints the specific figure. The Graphics context has already been
	 * translated to the origin of the figure.
	 * Subclasses need to override this method.
	 * 
	 * @param graphics The Graphics context
	 */
	protected void paintSpecificFigure(Graphics graphics) {
		graphics.drawRectangle(0, 0, getBounds().width - 1, getBounds().height - 1);
	}
}
