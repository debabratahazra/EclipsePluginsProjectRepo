package com.odcgroup.page.ui.figure.scroll;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * Represents a figure shich can be scrolled.
 * 
 * @author Gary Hayes
 */
public interface ScrollableWidgetFigure extends IFigure {
	
	/** The width (or height of a ScrollBar. */
	public static final int SCROLL_BAR_SIZE = 12;	
	
	/**
	 * Gets the origin of the figure.
	 * 
	 * @return Point The origin of the figure
	 */
	public Point getOrigin();	
	
	/**
	 * Sets the origin of the figure.
	 * 
	 * @param origin The origin to set
	 */
	public void setOrigin(Point origin);
	
	/**
	 * Gets the y-offset of the scrollable part of the WidgetFigure.
	 * 
	 * @return Y The y-offset of the scrollable part of the WidgetFigure
	 */
	public int getYOffset();	
	
	/**
	 * Gets the Scrollable bounds. This is the part of the WidgetFigure
	 * which is scrollable.
	 * 
	 * @return Rectangle The scrollable bounds
	 */
	public Rectangle getScrollableBounds();
	
	/**
	 * Gets the XScrollBar.
	 * 
	 * @return XScrollBar The XScrollBar
	 */
	public XScrollBar getXScrollBar();
	
	/**
	 * Gets the YScrollBar.
	 * 
	 * @return YScrollBar The YScrollBar
	 */
	public YScrollBar getYScrollBar();	
	
	/**
	 * Gets the scale factor to be used for the X-axis. This is
	 * (ChildrensWidth - FigureWidth) / FigureWidth.
	 * 
	 * @return double The scale factor to be used for the X-axis
	 */
	public double getXScaleFactor();
	
	
	/**
	 * Gets the scale factor to be used for the Y-axis. This is
	 * (ChildrensHeight - FigureHeight) / FigureHeight.
	 * 
	 * @return double The scale factor to be used for the Y-axis
	 */
	public double getYScaleFactor();	
}