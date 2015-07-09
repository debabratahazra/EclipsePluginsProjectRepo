package com.odcgroup.page.ui.figure;

import com.odcgroup.page.model.Widget;

/**
 * A layer. This does not display anything by itself.
 * 
 * @author atr
 */
public class Layer extends BoxFigure {

	/**
	 * Creates a new Layer.
	 * 
	 * @param widget The Widget being displayed by this Figure
	 * @param figureContext The context providing information required for displaying the layer correctly
	 */
	public Layer(Widget widget, FigureContext figureContext) {
		super(widget, figureContext);
		
		setLayoutManager(new VerticalLayout(figureContext));
	}	
}
