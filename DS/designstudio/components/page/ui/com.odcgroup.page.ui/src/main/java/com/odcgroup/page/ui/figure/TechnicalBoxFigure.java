package com.odcgroup.page.ui.figure;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.PageUIPlugin;

/**
 * The TechnicalBoxFigure is used when, in design mode, we wish to outline certain
 * technical features but not display them in preview mode. 
 * 
 * @author Gary Hayes
 */
public class TechnicalBoxFigure extends BoxFigure {
	
	/** The border Color. */
	private Color borderColor;
	private static String COLOR_TECHNICALBOX_FIRGURE_BORDER = "COLOR_TECHNICALBOX_FIRGURE_BORDER";
	
	
	@Override
	public void dispose() {
		super.dispose();
	}

	/**
	 * Draw the caption.
	 * 
	 * @param graphics The Graphics context
	 */
	protected void drawCaption(Graphics graphics) {
		// do nothing
	}

	
	/**
	 * Draws a red border around the table (design mode only)
	 * 
	 * @param graphics
	 *            The Graphics context
	 */
	protected void drawBorder(Graphics graphics) {
	    int x=0;
	    if (getFigureContext().isDesignMode()) {
		Rectangle b = getBounds();
		FigureConstants fc = getFigureConstants();
		graphics.setLineStyle(fc.getFieldLineStyle());
		graphics.setForegroundColor(getBorderColor());
		// -1 ensures that the left and bottom parts are drawn
		graphics.drawRectangle(x, 0, b.width - 1, b.height - 1);
	    }
	}

	/**
	 * Draws an visual indicator for the box type.
	 * 
	 * @param graphics
	 */
	protected void drawBoxTypeIndicator(Graphics graphics) {
		// Do nothing
	}
	
	/**
	 * Gets the border color.
	 * 
	 * @return Color The border color
	 */
	public Color getBorderColor() {
		if (borderColor == null) {
		    borderColor = PageUIPlugin.getColor(COLOR_TECHNICALBOX_FIRGURE_BORDER);
		}
		return borderColor;
	}
	
	/**
	 * Sets the border color.
	 * 
	 * @param newBorderColor The border color to set
	 */
	protected void setBorderColor(Color newBorderColor) {
		this.borderColor = newBorderColor;
	}
	
	/**
	 * Creates a new TechnicalBoxFigure.
	 * 
	 * @param widget
	 *            The Widget being displayed by this Figure
	 * @param figureContext
	 *            The context providing information required for displaying the page correctly
	 */
	public TechnicalBoxFigure(Widget widget, FigureContext figureContext) {
		super(widget, figureContext);
		initialize();
	}
	/**
	 * initialize method to register color.
	 */
	private void initialize(){
	    PageUIPlugin.setColorInRegistry(COLOR_TECHNICALBOX_FIRGURE_BORDER , new RGB(0,0,0));
	}
	
}