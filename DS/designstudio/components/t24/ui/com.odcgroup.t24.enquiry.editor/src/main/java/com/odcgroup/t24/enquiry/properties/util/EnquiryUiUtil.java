package com.odcgroup.t24.enquiry.properties.util;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;

import com.odcgroup.t24.enquiry.editor.part.EnquiryDiagramEditPart;

/**
 * UI Utility method for Enquiry form.
 *
 * @author mumesh
 *
 */
public class EnquiryUiUtil {

	/**
	 * Converts a Point to local coordinates relative the container.
	 * 
	 * @param container The container whose x and y position should be used
	 * @param point The Point to be converted
	 * @return point The newly converted Point
	 * @author Gary Hayes
	 */
	public static Point convertToLocalCoordinates(IFigure container, Point point) {
		Point local = new Point();
		int xOffset = 0;
		int yOffset = 0;
		while (container != null) {
			xOffset += container.getBounds().x;
			yOffset += container.getBounds().y;
			
			container = container.getParent();
		}
		
		local.x = point.x - xOffset;//container.getBounds().x;
		local.y = point.y - yOffset;//
		return local;
	}
	
	
	/**
	 * Converts the location as returned by the request to a location
	 * which takes into account the Scrollbar.
	 * 
	 * @param enquiryEditPart The WidgetEditPart
	 * @param location The original Point
	 * @return Point The offset Point
	 */	
	public static Point getOffsetLocation(EnquiryDiagramEditPart enquiryEditPart, Point location) {
		Point offset = enquiryEditPart.getScrollbarOffset();
		return new Point(location.x + offset.x, location.y + offset.y);
	}

}
