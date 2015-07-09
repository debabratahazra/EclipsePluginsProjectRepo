package com.odcgroup.page.ui.figure;

/**
 * Figures which are positionable have x and y coordinates.
 * 
 * @author Gary Hayes
 */
public interface Positionable {

	/**
	 * Gets the x-position.
	 * 
	 * @return int The x-position
	 */
	public int getPosX();
	
	/**
	 * Gets the y-position.
	 * 
	 * @return int The y-position
	 */
	public int getPosY();	
}
