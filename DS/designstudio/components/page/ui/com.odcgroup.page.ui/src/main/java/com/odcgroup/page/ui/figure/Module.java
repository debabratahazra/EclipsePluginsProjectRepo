package com.odcgroup.page.ui.figure;

import org.apache.commons.lang.StringUtils;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;

import com.odcgroup.page.model.Widget;

/**
 * A module adds a caption to the BoxFigure.
 * 
 * @author Gary Hayes
 */
public class Module extends BoxFigure {
	
	/** The module. */
//	private static Image MODULE_HEADER = createImage("/icons/obj16/moduleHeader.png");
	
	/** The width of the image. */
	private static final int IMAGE_WIDTH = 16;
	
	/**
	 * Creates a new Module.
	 * 
	 * @param widget The Widget being displayed by this Figure
	 * @param figureContext The context providing information required for displaying the page correctly
	 */
	public Module(Widget widget, FigureContext figureContext) {
		super(widget, figureContext);
	}
	
	/**
	 * Reset the image to null if the icon has changed.
	 * 
	 * @param name
	 * 		The property name
	 */
	public void afterPropertyChange(String name) {
	}	
	
	/**
	 * Draw the caption. Override the base class version to change the color.
	 * 
	 * @param graphics The Graphics context
	 */
	protected void drawCaption(Graphics graphics) {
		FigureConstants fc = getFigureConstants();
		graphics.setBackgroundColor(fc.getModuleHeaderBackgroundColor());
		
		Rectangle b = getBounds();
		graphics.fillRectangle(0, 0, b.width, getYOffset());
		
		if (! StringUtils.isEmpty(getText())) {	
			graphics.drawText(getText(), IMAGE_WIDTH + 8, 0);
		}

	}
	
	/**
	 * Draws the border for this figure. Override the base-class version to always draw
	 * the border.
	 * 
	 * @param graphics The Graphics context
	 */
	protected void drawBorder(Graphics graphics) {
		FigureConstants fc = getFigureConstants();
		
		Rectangle b = getBounds();
		
		graphics.setLineStyle(fc.getFieldLineStyle());
		graphics.setForegroundColor(fc.getBoxBorderColor());	
		
		// Note that the -1 ensures that the right and bottom lines are drawn,
		// otherwise they would fall outside the bounds of the Module
		graphics.drawRectangle(0, 0, b.width - 1, b.height - 1);
	}	
	
	@Override
	public void preferenceChange() {
		revalidate();
	}	

	/**
	 * Override the base-class version since modules ALWAYS have a header.
	 * 
	 * @return int The y-offset of the scrollable part of the WidgetFigure
	 */
	public int getYOffset() {
		return getFigureConstants().getSimpleWidgetDefaultHeight();
	}	
}
