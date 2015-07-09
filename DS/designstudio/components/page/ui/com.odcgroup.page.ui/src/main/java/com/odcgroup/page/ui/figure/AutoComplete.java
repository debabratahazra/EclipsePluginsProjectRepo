package com.odcgroup.page.ui.figure;

import org.apache.commons.lang.StringUtils;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Image;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Widget;

/**
 * Represents an AutoComplete.
 * 
 * @author Gary Hayes
 */
public class AutoComplete extends AbstractAlignableWidgetFigure {
	
	/** The AutoComplete image. */
	private static final Image IMAGE = createImage("/icons/obj16/autocomplete.png"); //$NON-NLS-1$	
	
	/**
	 * Creates a new AutoComplete.
	 * 
	 * @param widget The Widget being displayed by this Figure
	 * @param figureContext The context providing information required for displaying the page correctly
	 */
	public AutoComplete(Widget widget, FigureContext figureContext) {
		super(widget, figureContext);
	}
	
	/**
	 * Gets the minimum width of the figure. 
	 * 
	 * @return int The minimum width of the figure
	 */
	public int getMinWidth() {
		return (int)(getColumns() * getFigureConstants().getColumnScalingFactor()) + getFigureConstants().getSimpleWidgetDefaultWidth();
	}
	
	/**
	 * Gets the minimum height of the figure.
	 * 
	 * @return int The minimum height of the figure
	 */
	public int getMinHeight() {
		return getFigureConstants().getSimpleWidgetDefaultHeight();
	}	
	
	/**
	 * Paints the CaldateField.
	 * 
	 * @param graphics The Graphics context
	 */
	protected void paintSpecificFigure(Graphics graphics) {
		FigureConstants fc = getFigureConstants();
		graphics.setForegroundColor(fc.getFieldColor());
		graphics.setLineStyle(fc.getFieldLineStyle());
		Rectangle b = getBounds();
		// The -1 ensures that the right and bottom lines are drawn, otherwise they wiould fall outside the bounds of the TextField
		graphics.drawRectangle(new Rectangle(0, 0, b.width - getFigureConstants().getSimpleWidgetDefaultHeight() - 1, b.height - 1));
		graphics.drawImage(IMAGE, b.width - 16, 0);		

		// The 1 serves to center the text in the rectangle
		String pv = getPreviewValue();
		if (! StringUtils.isEmpty(pv)) {
			graphics.drawText(pv, 1, 1);
		}
	}

	/**
	 * Gets the number of columns of the CaldateField.
	 * 
	 * @return int The number of columns of the CaldateField.
	 */
	public int getColumns() {
		return getInt(PropertyTypeConstants.COLUMNS);
	}
}