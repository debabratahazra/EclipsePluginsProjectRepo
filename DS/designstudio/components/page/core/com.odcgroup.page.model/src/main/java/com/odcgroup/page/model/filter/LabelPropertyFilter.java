package com.odcgroup.page.model.filter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.EList;

import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.FilterUtils;

public class LabelPropertyFilter implements PropertyFilter {
 
	 private String[] filterPropertys={PropertyTypeConstants.ENABLED_IS_BASED_ON ,
	            PropertyTypeConstants.MAX_CHARACTERS,
	            PropertyTypeConstants.REQUIRED ,
	            PropertyTypeConstants.VERTICAL_TEXT_POSITION,
	            PropertyTypeConstants.VERTICAL_ALIGNMENT ,
	            PropertyTypeConstants.HORIZONTAL_TEXT_POSITION,
	            PropertyTypeConstants.CSS_CLASS,
	            PropertyTypeConstants.NO_WRAP,
	            PropertyTypeConstants.WIDTH,
	            PropertyTypeConstants.ENABLED ,
	            PropertyTypeConstants.HORIZONTAL_ALIGNMENT ,
	            PropertyTypeConstants.FOR
	};
	 
	@Override
	public List<Property> filter(Widget widget) {
		Set<PropertyType> allTypes = new HashSet<PropertyType>();
		allTypes.addAll(widget.getType().getAllPropertyTypes().values());
		Widget root=widget.getRootWidget();
		Property frgamntType=root.findProperty(PropertyTypeConstants.FRAGMENT_TYPE);
		EList<Property> widgetProperties = widget.getProperties();
		
		//if the widget root is xtooltip type fragment.
		if((frgamntType!=null&&frgamntType.getValue().equals("xtooltip"))){
			
			for(String property:Arrays.asList(filterPropertys)){
				FilterUtils.hideProperty(property, widget, allTypes);				
			}
		}
		List<Property> filteredProperties = FilterUtils.filterProperties(widgetProperties, allTypes);
		return filteredProperties;
		
	}

}
