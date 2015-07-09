package com.odcgroup.page.ui.edit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.geometry.Point;

import com.odcgroup.page.model.Widget;

/**
 * Several static utility methods for WidgetEditParts.
 * 
 * @author Gary Hayes
 */
public class WidgetEditPartUtils {
	
	/**
	 * Obtains a List of Widgets from a List of WidgetEditParts.
	 * 
	 * @param widgetEditParts The List of WidgetEditParts
	 * @return List The List of Widget
	 */
	public static List<Widget> getWidgets(List<WidgetEditPart> widgetEditParts) {
		List<Widget> result = new ArrayList<Widget>();
		if (widgetEditParts == null) {
			return result;
		}
		
		for (Iterator<WidgetEditPart> it = widgetEditParts.iterator(); it.hasNext();) {
			WidgetEditPart wep = (WidgetEditPart) it.next();
			result.add(wep.getWidget());
		}
		
		return result;	
	}

	/**
	 * Obtains a List of WidgetTypes from a List of WidgetEditParts. These are obtained
	 * via the associated Widget.
	 * 
	 * @param widgetEditParts The List of WidgetEditParts
	 * @return List The List of WidgetTypes
	 */
	public static List getWidgetTypes(List widgetEditParts) {
		List result = new ArrayList();
		if (widgetEditParts == null) {
			return result;
		}
		
		for (Iterator it = widgetEditParts.iterator(); it.hasNext();) {
			WidgetEditPart wep = (WidgetEditPart) it.next();
			result.add(wep.getWidget().getType());
		}
		
		return result;
	}
		
	/**
	 * Converts the location as returned by the request to a location
	 * which takes into account the Scrollbar.
	 * 
	 * @param wep The WidgetEditPart
	 * @param location The original Point
	 * @return Point The offset Point
	 */	
	public static Point getOffsetLocation(WidgetEditPart wep, Point location) {
		Point o = wep.getScrollbarOffset();
		return new Point(location.x + o.x, location.y + o.y);
	}
}