package com.odcgroup.page.model.filter;

import java.util.ArrayList;
import java.util.List;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;

public class TabbedPanePropertyFilter implements PropertyFilter {

	@Override
	public List<Property> filter(Widget widget) {
		
		List<Property> filteredProperties = new ArrayList<Property>();
		filteredProperties.addAll(widget.getProperties());
		
		Property p = widget.findProperty(PropertyTypeConstants.HIDE_EMPTY_TABS);
		if (p != null && !p.getBooleanValue()) {
			// not a dynamic tab
			p = widget.findProperty(PropertyTypeConstants.FILTER_LEVEL);
			if (p != null) {
				filteredProperties.remove(p);
			}
		}
		return filteredProperties;
	}

}
