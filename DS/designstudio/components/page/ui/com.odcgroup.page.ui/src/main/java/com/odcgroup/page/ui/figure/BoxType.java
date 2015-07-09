package com.odcgroup.page.ui.figure;

import org.eclipse.draw2d.LayoutManager;

/**
 * Interface for the different types of boxes.
 * 
 * @author Gary Hayes
 */
public interface BoxType {

	/**
	 * Creates a LayoutManager for this type of box.
	 * 
	 * @param figureContext The FigureContext
	 * @return LayoutManager The newly created LayoutManager
	 */
	public LayoutManager createLayoutManager(FigureContext figureContext);

	/**
	 * Gets the minimum width of the figure.
	 * 
	 * @return int The minimum width of the figure
	 */
	public int getMinWidth();	
	
	/**
	 * Gets the minimum height of the figure.
	 * 
	 * @return int The minimum height of the figure
	 */
	public int getMinHeight();	

	/**
	 * Gets the maximum width of the figure. HorizontalBoxes do not have
	 * maximum widths. Instead they can expand to fill the entire width
	 * of the parent container.
	 * 
	 * @return int The maximum width of the figure
	 */
	public int getMaxWidth();
	
	/**
	 * Gets the maximum height of the figure.
	 * 
	 * @return int The maximum height of the figure
	 */
	public int getMaxHeight();
	
	
	/**
	 * Gets the String used to display a difference between the boxes.
	 * 
	 * @return String
	 */
	public String getBoxType();
}
