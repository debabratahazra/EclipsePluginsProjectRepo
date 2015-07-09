package com.odcgroup.page.ui.figure;

/**
 * Figures implementing this interface cane be constrained. 
 * The constraint is expressed as a percentage. For this interface
 * to have any effect the WidgetFigure must have a maxWidth of -1.
 * 
 * @author Gary Hayes
 */
public interface Constrainable {

	/**
	 * Gets the constraints expressed as a percentage.
	 * 
	 * @return int The constraints expressed as a percentage
	 */
	public int getConstraints();
}
