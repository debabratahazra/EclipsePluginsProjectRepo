package com.odcgroup.page.model.filter;

import java.util.List;

import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;

/**
 * The PropertyFilter filters properties of Widgets.
 * 
 * @author Gary Hayes
 */
public interface PropertyFilter {

	/**
	 * Filters the widget returning a sub-group of the properties.
	 * 
	 * @param widget The Widget to filter
	 * @return List The List of properties
	 */
	public List<Property> filter(Widget widget);
}
