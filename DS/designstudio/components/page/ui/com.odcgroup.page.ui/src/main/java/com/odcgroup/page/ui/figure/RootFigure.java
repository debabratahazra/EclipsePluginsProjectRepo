package com.odcgroup.page.ui.figure;

import com.odcgroup.page.model.Widget;


/**
 * The root figure represent an empty widget who represents the renderer info of the Page widget
 *
 * @author Gary Hayes
 */
public class RootFigure extends AbstractWidgetFigure {
	
	/**
	 * Creates a new RootFigure.
	 * 
	 * @param widget The Widget being displayed by this Figure
	 * @param figureContext The context providing information required for displaying the page correctly
	 */
	public RootFigure(Widget widget, FigureContext figureContext) {
		super(widget, figureContext);
		
		setLayoutManager(new VerticalLayout(figureContext));
	}	
	
	/**
	 * Gets the minimum width of the figure. The layouts always set the width to
	 * be greater than or equal to the minimum width.
	 * 
	 * 
	 * @return int 0 The minimum width of the figure
	 */
	public int getMinWidth() {
		return 0;
	}

	/**
	 * Gets the minimum height of the figure. The layouts always set the height
	 * to be greater than or equal to the minimum height.
	 * 
	 * @return int 0 The minimum height of the figure
	 */
	public int getMinHeight() {
		return 0;		
	}

}
