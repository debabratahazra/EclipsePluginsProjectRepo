package com.odcgroup.page.ui.util;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;

/**
 * Utility methods for Points.
 * 
 * @author Gary Hayes
 */
public class PointUtils {

	/**
	 * Converts a Point to local coordinates relative the container.
	 * 
	 * @param container The container whose x and y position should be used
	 * @param point The Point to be converted
	 * @return point The newly converted Point
	 */
	public static Point convertToLocalCoordinates(IFigure container, Point point) {
		Point local = new Point();
		
		int xOffset = 0;
		int yOffset = 0;
		while (container != null) {
			xOffset += container.getBounds().x;
			yOffset += container.getBounds().y;;
			
			container = container.getParent();
		}
		
		local.x = point.x - xOffset;//container.getBounds().x;
		local.y = point.y - yOffset;//
		return local;
	}
}
