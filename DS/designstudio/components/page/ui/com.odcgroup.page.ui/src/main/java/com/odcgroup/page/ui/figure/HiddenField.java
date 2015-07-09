package com.odcgroup.page.ui.figure;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Point;

import com.odcgroup.page.model.Widget;

/**
 * Represents a hidden field.
 * 
 * @author Gary Hayes
 */
public class HiddenField extends AbstractTranslatedWidgetFigure implements WidgetFigureContainer {

	/**
	 * Creates a new HiddenField.
	 * 
	 * @param widget The Widget being displayed by this Figure
	 * @param figureContext The context providing information required for displaying the page correctly
	 */
	public HiddenField(Widget widget, FigureContext figureContext) {
		super(widget, figureContext);
	}
	
	/**
	 * Gets the WidgetFigures. These are the child Widgets which need to be laid out.
	 * 
	 * @return List of WidgetFigures
	 */
	public List getWidgetFigures() {
		List widgetList = new ArrayList();
		if (getFigureContext().isDesignMode()) {
			// Only display the hidden widget in design mode
			widgetList.add(this);
		}
		return widgetList;
	}	
	
	/**
	 * Gets the minimum width of the figure.
	 * 
	 * @return int The minimum width of the figure
	 */
	public int getMinWidth() {
		if (getFigureContext().isDesignMode()) {
			return getFigureConstants().getInvisibleWidgetSize()+8;
		}
		return 0;
	}

	/**
	 * Gets the minimum height of the figure.
	 * 
	 * @return int The minimum height of the figure
	 */
	public int getMinHeight() {
		if (getFigureContext().isDesignMode()) {
			return getFigureConstants().getInvisibleWidgetSize()+8;
		}
		return 0;
	}

	/**
	 * Paints the HiddenField.
	 * 
	 * @param graphics
	 *            The Graphics context
	 */
	protected void paintSpecificFigure(Graphics graphics) {
		if (! getFigureContext().isDesignMode()) {
			// Nothing to paint
			return;
		}		
		
		FigureConstants fc = getFigureConstants();
		graphics.setForegroundColor(fc.getHiddenFeaturesColor());
		graphics.drawText("[H]", new Point(2, 0));
	}
	
	/**
	 * Returns true if the Widget figure represents a visual element.
	 * This returns false.
	 * 
	 * @return boolean True if the Widget figure represents a visual element
	 */
	public boolean isVisualElement() {
		return false;
	}	
}
