package com.odcgroup.page.ui.figure;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.symbols.SymbolsExpander;
import com.odcgroup.page.model.symbols.SymbolsExpanderFactory;

/**
 * The AbstractAlignableWidgetFigure represents Widgets which can
 * be aligned.
 * 
 * @author Gary Hayes
 */
abstract public class AbstractAlignableWidgetFigure extends AbstractTranslatedWidgetFigure implements Alignable {

	/**
	 * Creates a new AbstractAlignableWidgetFigure.
	 * 
	 * @param widget The Widget being displayed by this Figure
	 * @param figureContext The context providing information required for displaying the page correctly
	 */
	public AbstractAlignableWidgetFigure(Widget widget, FigureContext figureContext) {
		super(widget, figureContext);
	}	
	
	/**
	 * Gets the horizontal alignment.
	 * 
	 * @return String The horizontal alignment
	 */
	public String getHorizontalAlignment() {
		String value = getString(PropertyTypeConstants.HORIZONTAL_ALIGNMENT);
        SymbolsExpander expander = SymbolsExpanderFactory.getSymbolsExpander();
        if (expander != null) {
        	value = expander.substitute(value, getWidget());
        }	
        return value;
	}

	/**
	 * Gets the vertical alignment.
	 * 
	 * @return String The vertical alignment
	 */
	public String getVerticalAlignment() {
		return getString(PropertyTypeConstants.VERTICAL_ALIGNMENT);
	}
}
