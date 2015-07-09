package com.odcgroup.page.ui.figure.conditional;

import org.eclipse.draw2d.FigureUtilities;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.figure.FigureContext;
import com.odcgroup.page.ui.figure.TechnicalBoxFigure;
import com.odcgroup.page.ui.figure.tab.ConditionalBodyBoxType;

/**
 * The body of a single condition of a ConditionalWidget.
 * 
 * @author atr
 */
public class ConditionalBodyFigure extends TechnicalBoxFigure implements IConditionFigure {

	@Override
	public final String getName() {
		return getString("technicalName");
	}

	/**
	 * Creates a new ConditionalBodyFigure.
	 * 
	 * @param widget
	 *            The Widget being displayed by this Figure
	 * @param figureContext
	 *            The context providing information required for displaying the page correctly
	 */
	public ConditionalBodyFigure(Widget widget, FigureContext figureContext) {
		super(widget, figureContext);
		setBoxType(new ConditionalBodyBoxType(this));
	}

	@Override
	public int getPixelWidth() {
		String text = getWidget().getPropertyValue(PropertyTypeConstants.TECHNICAL_NAME);
		int wspace = getFigureConstants().getWidgetSpacing() * 2;
		text = "?< "+text+" >";
		int width = FigureUtilities.getTextWidth(text, getFigureConstants().getCaptionFont(true))+wspace+5;
		return width;
	}
	
	

}
