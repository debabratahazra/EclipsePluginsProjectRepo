package com.odcgroup.page.ui.figure;

import org.eclipse.draw2d.Graphics;

/**
 * This decorates a Figure. It can be used to provide additional images
 * to the figures, for example, to highlight errors etc.
 * 
 * @author Gary Hayes
 */
public interface WidgetFigureDecorator {

	/**
	 * Decorates the figure. This is called after paint.
	 * 
	 * @param figure The figure to decorate
	 * @param graphics the Graphics context
	 */
	public void paint(IWidgetFigure figure, Graphics graphics);	
}
