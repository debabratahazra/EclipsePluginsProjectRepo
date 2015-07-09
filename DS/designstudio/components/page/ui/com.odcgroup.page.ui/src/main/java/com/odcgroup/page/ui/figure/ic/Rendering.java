package com.odcgroup.page.ui.figure.ic;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Point;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.figure.AbstractTranslatedWidgetFigure;
import com.odcgroup.page.ui.figure.FigureContext;

/**
 * Represents the <ic:rendering> tag.
 * 
 * @author Gary Hayes
 */
public class Rendering extends AbstractTranslatedWidgetFigure {

	/** The image. */
	//private static final Image IMAGE = createImage("/icons/obj16/renderingSmall.png"); 

	/** The width of the image. */	
	//private static final int IMAGE_WIDTH = 199;
	
	/** The height of the image. */	
	//private static final int IMAGE_HEIGHT = 27;
	
	/** The message to display. */
	private static final String MESSAGE = "« Input Control Messages Area »";
	
	/**
	 * Creates a new Rendering.
	 * 
	 * @param widget The Widget being displayed by this Figure
	 * @param figureContext The context providing information required for displaying the page correctly
	 */
	public Rendering(Widget widget, FigureContext figureContext) {
		super(widget, figureContext);
	}

	/**
	 * Gets the minimum width of the figure. This is equal to the
	 * width of the image.
	 * 
	 * @return int The minimum width of the figure
	 */
	public int getMinWidth() {
		if (getFigureContext().isPreviewMode()) {
			return 0;
		}		
		return calculateTextSize(MESSAGE).width;
	}
	
	/**
	 * Gets the maximum width of the figure. This is equal to the
	 * width of the image.
	 * 
	 * @return int The maximum width of the figure
	 */
	public int getMaxWidth() {
		if (getFigureContext().isPreviewMode()) {
			return 0;
		}		
		return 0;
	}	
	
	/**
	 * Gets the minimum height of the figure.
	 * 
	 * @return int The minimum height of the figure
	 */
	public int getMinHeight() {
		if (getFigureContext().isPreviewMode()) {
			return 0;
		}
		return getFigureConstants().getSimpleWidgetDefaultHeight();
	}	
	
	/**
	 * Paints the RadioButton.
	 * 
	 * @param graphics The Graphics context
	 * @see Figure#paintFigure(Graphics)
	 */
	protected void paintSpecificFigure(Graphics graphics) {
		if (getFigureContext().isPreviewMode()) {
			return;
		}
		
		//FigureConstants fc = getFigureConstants();
		//Rectangle b = getBounds();
		graphics.drawText(MESSAGE, new Point(0, 0));
		
		//graphics.setBackgroundColor(fc.getRenderingBackgroundColor());
		//graphics.fillRectangle(0, 0, b.width, b.height);
		//graphics.drawImage(IMAGE, new Point(0, 0));
	}
}