package com.odcgroup.page.ui.figure;

import org.eclipse.draw2d.Graphics;

import com.odcgroup.page.model.Widget;

/**
 * A glue is a Widget which is used to 'push' oter Widgets left and right
 * in a container.
 * 
 * @author Gary Hayes
 */
public class Glue extends AbstractTranslatedWidgetFigure {

	/**
	 * Creates a new Glue.
	 * 
	 * @param widget The Widget being displayed by this Figure
	 * @param figureContext The context providing information required for displaying the page correctly
	 */
	public Glue(Widget widget, FigureContext figureContext) {
		super(widget, figureContext);
	}
	
	/**
	 * Gets the minimum width of the figure.
	 * 
	 * @return int The minimum width of the figure
	 */
	public int getMinWidth() {
		return getFigureConstants().getSimpleWidgetDefaultWidth();
	}
	
	/**
	 * Gets the minimum height of the figure.
	 * 
	 * @return int The minimum height of the figure
	 */
	public int getMinHeight() {
		return getFigureConstants().getSimpleWidgetDefaultHeight();
	}	
	
	/**
	 * Gets the maximum width of the figure. HorizontalBoxes do not have
	 * maximum widths. Instead they can expand to fill the entire width
	 * of the parent container.
	 * 
	 * @return int The maximum width of the figure
	 */
	public int getMaxWidth() {
		return -1;
	}	
	
	/**
	 * Paints the Glue.
	 * 
	 * @param graphics The Graphics context
	 */
	protected void paintSpecificFigure(Graphics graphics) {
		drawOutline(graphics);
	}
}
