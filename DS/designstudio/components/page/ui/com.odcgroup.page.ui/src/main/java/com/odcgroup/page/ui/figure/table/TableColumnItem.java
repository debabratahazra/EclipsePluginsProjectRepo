package com.odcgroup.page.ui.figure.table;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.symbols.SymbolsExpander;
import com.odcgroup.page.model.symbols.SymbolsExpanderFactory;
import com.odcgroup.page.ui.edit.PaintUtils;
import com.odcgroup.page.ui.figure.AbstractAlignableWidgetFigure;
import com.odcgroup.page.ui.figure.FigureConstants;
import com.odcgroup.page.ui.figure.FigureContext;
import com.odcgroup.page.ui.figure.PaintIterator;

/**
 * This class represent the udp:item figure
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class TableColumnItem extends AbstractAlignableWidgetFigure {
	
	/** The minimum size of the Item */
	private final static int MIN_SIZE = 5;	
		
	/** 
	 * The parsed preview values.
	 * These are comma separated values taken from the property 'preview'.
	 */
	private List<String> previewValues;
	
	/**
	 * Creates a new Item.
	 * 
	 * @param widget The Widget being displayed by this Figure
	 * @param figureContext The context providing information required for displaying the page correctly
	 */
	public TableColumnItem(Widget widget, FigureContext figureContext) {
		super(widget, figureContext);
		
		createPreviewValues();
	}
	
	/**
	 * Gets the horizontal alignment.
	 * 
	 * @return String The horizontal alignment
	 */
	public String getHorizontalAlignment() {
		String value = getString("item-halign");
        SymbolsExpander expander = SymbolsExpanderFactory.getSymbolsExpander();
        if (expander != null) {
        	value = expander.substitute(value, getWidget());
        }	
        return value;
	}
	
	/**
	 * Implements the required treatment after the property have been
	 * changed.
	 * 
	 * @param name
	 * 		The property name
	 */
	public void afterPropertyChange(String name) {
		previewValues = null; 
	}	

	/**
	 * Gets the minimum width of the figure.
	 * 
	 * @return int The minimum width of the figure
	 */
	public int getMinWidth() {		
		final String empty = "               ";
		int minWidth = calculateTextSize(empty).width;
		for (String s : getPreviewValues()) {
			minWidth = Math.max(minWidth, calculateTextSize(StringUtils.isEmpty(s) ? empty : s).width);
		}
		
		return minWidth + PaintUtils.getWidth(getWidget());
	}	

	/**
	 * Gets the minimum height of the figure.
	 * 
	 * @return int The minimum height of the figure
	 */
	public int getMinHeight() {
	    if( PaintUtils.getWidth(getWidget()) > 0){
		return getFigureConstants().getSimpleWidgetDefaultHeight() + PaintUtils.IMAGE_HEIGHT;
	    }
	    return getFigureConstants().getSimpleWidgetDefaultHeight();
	}
	
	/**
	 * Paints the RadioButton.
	 * 
	 * @param graphics The Graphics context
	 * @see Figure#paintFigure(Graphics)
	 */
	protected void paintFigure(Graphics graphics) {
		super.paintFigure(graphics);
		
		Rectangle b = getBounds();
		graphics.translate(b.x, b.y);
		 
		paintSpecificFigure(graphics);
	
		graphics.translate(-b.x, -b.y);
	}
	
	/**
	 * Paints the Label.
	 * 
	 * @param graphics The Graphics context
	 */
	protected void paintSpecificFigure(Graphics graphics) {
		PaintUtils.paintIcons(getWidget(), graphics, 0);
	        FigureConstants fc = getFigureConstants();
		graphics.setForegroundColor(fc.getFieldColor());
		int y=0;
		if (getPreviewValues().size() > 0) {
			String s = getPreviewValues().get(0);
			if (StringUtils.isEmpty(s)) {
				// Enable the Item to be selected more easily
				s = "     ";
			}
			if( PaintUtils.getWidth(getWidget())>0){
			    y= PaintUtils.IMAGE_HEIGHT;
			}
			graphics.drawText(s, 0,y);
		}
		drawOutline(graphics);
	}
	
	@Override
	protected void drawOutline(Graphics graphics) {
		if (getFigureContext().isDesignMode() == false) {
			// Nothing to display
			return;
		}

		setGraphicsForOutline(graphics);

		// The -1 ensures that the right and bottom lines are drawn, otherwise
		// they would fall outside the bounds of the TextField
		Rectangle b = getBounds();
		graphics.drawRectangle(new Rectangle(0, 0, b.width - 1, b.height - 1));
	}
	
	/**
	 * Gets the current index. This is obtained from the first parent Widget
	 * which implements the interfaace PaintIterator.
	 * 
	 * @return int The current index
	 */
	private int getCurrentIndex() {
		IFigure f = getParent();
		while (f != null) {
			if (f instanceof PaintIterator) {
				PaintIterator pi = (PaintIterator) f;
				return pi.getCurrentIndex();
			}
			
			f = f.getParent();
		}
		
		// Not found
		return -1;
	}
	
	/**
	 * Gets the preview values.
	 * 
	 * @return List of String's
	 */
	private List<String> getPreviewValues() {
		if (previewValues == null) {
			previewValues = createPreviewValues();
		}
		return previewValues;
	}
}