package com.odcgroup.page.ui.figure;

import com.odcgroup.page.model.Widget;

/**
 * A fragment. This does not display anything by itself.
 * 
 * @author Gary Hayes
 */
public class Fragment extends BoxFigure {

	/**
	 * Creates a new Fragment.
	 * 
	 * @param widget The Widget being displayed by this Figure
	 * @param figureContext The context providing information required for displaying the page correctly
	 */
	public Fragment(Widget widget, FigureContext figureContext) {
		super(widget, figureContext);
		
		setLayoutManager(new VerticalLayout(figureContext));
	}	
}
