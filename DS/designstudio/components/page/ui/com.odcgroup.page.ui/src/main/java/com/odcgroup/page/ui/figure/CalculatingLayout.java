package com.odcgroup.page.ui.figure;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;

/**
 * All the layouts used in the PageDesigner MUST implements this interface.
 * This is because the WidgetLayoutEditPolicy needs to calculate the index
 * of the new IFigure to send to the WidgetCreateCommand.
 * 
 * @author Gary Hayes
 */
public interface CalculatingLayout {
	
	/**
	 * Calculates the index of the IFigure.
	 * 
	 * @param container The container of the new Figure
	 * @param location The location of the CreateRequest
	 * @return int The index of the new Widget
	 */
	public int calculateIndex(IFigure container, Point location);

}
