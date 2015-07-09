package com.odcgroup.page.ui.figure;

import org.apache.commons.lang.StringUtils;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.edit.PaintUtils;


/**
 * Represents a FileChooser.
 * 
 * @author Alexandre Jaquet
 * @author Gary Hayes
 */
public class FileChooser extends AbstractAlignableWidgetFigure {
		
	
	/** The width of the button. */
	private static final int BUTTON_WIDTH = 60;
	
	/**
	 * Creates a new FileChooser.
	 * 
	 * @param widget The Widget being displayed by this Figure
	 * @param figureContext The context providing information required for displaying the page correctly
	 */
	public FileChooser(Widget widget, FigureContext figureContext) {
		super(widget, figureContext);
	}
	
	/**
	 * Gets the minimum width of the figure. 
	 * 
	 * @return int The minimum width of the figure
	 */
	public int getMinWidth() {
		return (int) (getFigureConstants().getColumnScalingFactor() * getColumns()) + BUTTON_WIDTH + PaintUtils.getWidth(getWidget());
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
	 * Gets the number of columns of the FileChooser.
	 * 
	 * @return int The number of columns.
	 */
	public int getColumns() {
		return getInt(PropertyTypeConstants.COLUMNS);
	}
	
	/**
	 * Paints the FileChooser.
	 * 
	 * @param graphics The Graphics context
	 */
	protected void paintSpecificFigure(Graphics graphics) {
	    if(PaintUtils.isWidgethAlignLead(getWidget())){
		PaintUtils.paintIcons(getWidget(), graphics,getMinWidth() - PaintUtils.getWidth(getWidget()));
	    }else {
		PaintUtils.paintIcons(getWidget(), graphics,0);
	    }
		FigureConstants fc = getFigureConstants();
		graphics.setForegroundColor(fc.getFieldColor());
		graphics.setLineStyle(fc.getFieldLineStyle());
		graphics.setBackgroundColor(fc.getFileChooserButtonBackgroundColor());
		// The -1 ensures that the right and bottom lines are drawn, otherwise they wiould fall outside the bounds of the TextField
		Rectangle b = getBounds();
		
		// TextField
		graphics.drawRectangle(new Rectangle(0, 0, getColumnWidth() - 1, b.height - 1));		
		
		// Button
		graphics.fillRectangle(new Rectangle(getColumnWidth() + 1, 0, BUTTON_WIDTH, b.height));
		graphics.drawRectangle(new Rectangle(getColumnWidth() + 1, 0, BUTTON_WIDTH - 2, b.height - 1));
		
		graphics.drawText("  Browse...  ", new Point(getFigureConstants().getColumnScalingFactor() * getColumns() + 1,0));
	
		// The 1 serves to center the text in the rectangle
		String pv = getPreviewValue();
		if (! StringUtils.isEmpty(pv)) {
			graphics.drawText(pv, 1, 1);
		}
	}	
	
	/**
	 * Gets the Column Width. This is the number of columns * the scaling factor.
	 * 
	 * @return The column width (in pixels)
	 */
	private int getColumnWidth() {
		return (int) getFigureConstants().getColumnScalingFactor() * getColumns();
	}
}
