package com.odcgroup.page.ui.figure;

import org.eclipse.draw2d.Graphics;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Widget;

/**
 * Represents a space between two Widgets. This is used to improve
 * the appearance of the page.
 * 
 * @author Gary Hayes
 */
public class Spacer extends AbstractTranslatedWidgetFigure {

	/** The default width of the Spacer. */
	private static final int MIN_SIZE = 4;	
	

	/**
	 * Creates a new Spacer.
	 * 
	 * @param widget The Widget being displayed by this Figure
	 * @param figureContext The context providing information required for displaying the page correctly
	 */
	public Spacer(Widget widget, FigureContext figureContext) {
		super(widget, figureContext);
	}

	/**
	 * Gets the minimum width of the figure.
	 * 
	 * @return int The minimum width of the figure
	 */
	public int getMinWidth() {
		if (getWidth() < MIN_SIZE) {
			return MIN_SIZE;
		}
		return getWidth();
	}
	
	/**
	 * Gets the minimum height of the figure.
	 * 
	 * @return int The minimum height of the figure
	 */
	public int getMinHeight() {
		if (getHeight() < MIN_SIZE) {
			return MIN_SIZE;
		}
		return getHeight();
	}	

	
	/**
	 * Paints the Spacer.
	 * 
	 * @param graphics The Graphics context
	 */
	protected void paintSpecificFigure(Graphics graphics) {
		drawOutline(graphics);
	}
	
	/**
	 * Get the width of the spacer.
	 * 
	 * @return int The width.
	 */
	public int getWidth() {
		// TODO AJQ Correct this. width is now a String since it can contain a %
		return Integer.parseInt(getString(PropertyTypeConstants.WIDTH));
	}

	/**
	 * Get the height of the spacer.
	 * 
	 * @return int The height.
	 */
	public int getHeight() {
		return getInt(PropertyTypeConstants.HEIGHT);
	}	
}