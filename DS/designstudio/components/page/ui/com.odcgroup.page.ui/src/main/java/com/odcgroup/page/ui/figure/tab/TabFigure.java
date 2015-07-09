package com.odcgroup.page.ui.figure.tab;

import org.apache.commons.lang.StringUtils;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.figure.FigureContext;
import com.odcgroup.page.ui.figure.TechnicalBoxFigure;

/**
 * A single Tab of the TabbedPane.
 * 
 * @author atr
 */
public class TabFigure extends TechnicalBoxFigure implements ITabFigure {

	@Override
	public void setText(String newText) {
		super.setText(newText);
		setYOffset(0);
	}
	
	@Override
	public final String getName() {
		String text = getText();
		if (StringUtils.isEmpty(text)) {
			text = "        ";
		}
		return text;
	}

	/**
	 * Creates a new Tab.
	 * 
	 * @param widget
	 *            The Widget being displayed by this Figure
	 * @param figureContext
	 *            The context providing information required for displaying the page correctly
	 */
	public TabFigure(Widget widget, FigureContext figureContext) {
		super(widget, figureContext);
	}

}