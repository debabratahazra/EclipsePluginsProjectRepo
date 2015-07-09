package com.odcgroup.page.ui.figure.table;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.figure.AbstractAlignableWidgetFigure;
import com.odcgroup.page.ui.figure.FigureConstants;
import com.odcgroup.page.ui.figure.FigureContext;
import com.odcgroup.page.ui.figure.PaintIterator;

/**
 * This class represent the udp:item figure
 * 
 * @author Alexandre Jaquet
 * @author Gary Hayes
 */
public class Item extends AbstractAlignableWidgetFigure {
	
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
	public Item(Widget widget, FigureContext figureContext) {
		super(widget, figureContext);
		
		createPreviewValues();
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
		int minWidth = MIN_SIZE;
		for (String s : getPreviewValues()) {
			minWidth = Math.max(minWidth, calculateTextSize(s).width);
		}
		
		return minWidth;
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
	 * Paints the Label.
	 * 
	 * @param graphics The Graphics context
	 */
	protected void paintSpecificFigure(Graphics graphics) {
		FigureConstants fc = getFigureConstants();
		graphics.setForegroundColor(fc.getFieldColor());
		
		int ci = getCurrentIndex();
		if (ci != -1 && getPreviewValues().size() > ci) {
			String s = getPreviewValues().get(ci);
			if (StringUtils.isEmpty(s)) {
				// Enable the Item to be selected more easily
				s = "     ";
			}
			graphics.drawText(s, 0, 0);		
		}
		drawOutline(graphics);
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