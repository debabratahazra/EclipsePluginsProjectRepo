package com.odcgroup.page.ui.figure;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;

import com.odcgroup.page.model.Widget;

/**
 * This WidgetFigure translates the Graphics context to the top-left hand corner (the
 * origin) of the WidgetFigure before painting the Figure. It retranslates the Figure back
 * to its old position afterwards.
 * 
 * @author Gary Hayes
 */
abstract public class AbstractTranslatedWidgetFigure extends AbstractWidgetFigure {

	/**
	 * Creates a new AbstractTranslatedWidgetFigure.
	 * 
	 * @param widget The Widget being displayed by this Figure
	 * @param figureContext The context providing information required for displaying the page correctly
	 */
	public AbstractTranslatedWidgetFigure(Widget widget, FigureContext figureContext) {
		super(widget, figureContext);
	}
	
	/**
	 * Paints the RadioButton.
	 * 
	 * @param graphics The Graphics context
	 * @see Figure#paintFigure(Graphics)
	 */
	protected void paintFigure(Graphics graphics) {
		super.paintFigure(graphics);
		
		Rectangle b = getBounds();
		
		graphics.translate(b.x, b.y);
		 
		paintSpecificFigure(graphics);
	
		graphics.translate(-b.x, -b.y);
	}
	
	/**
	 * Paints the specific figure. The Graphics context has already been
	 * translated to the origin of the figure.
	 * Subclasses need to override this method.
	 * 
	 * @param graphics The Graphics context
	 */
	abstract protected void paintSpecificFigure(Graphics graphics);	
}
