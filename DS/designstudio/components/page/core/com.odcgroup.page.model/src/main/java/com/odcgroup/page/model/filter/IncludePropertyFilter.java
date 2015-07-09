package com.odcgroup.page.model.filter;

import java.util.ArrayList;
import java.util.List;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.CdmUtils;
import com.odcgroup.page.model.util.ModuleContainmentUtil;

/**
 * Filter properties for the Widget Include
 * @author atripod
 */
public class IncludePropertyFilter implements PropertyFilter {
	


	@Override
	public List<Property> filter(Widget widget) {
		
		List<Property> filteredProperties = new ArrayList<Property>();
		filteredProperties.addAll(widget.getProperties());
		
		Property property = widget.findProperty(PropertyTypeConstants.INCLUDE_SOURCE);
		if (! ModuleContainmentUtil.includeContainerOrStandaloneModule(property)) {
			Property prop = widget.findProperty(PropertyTypeConstants.INCLUDE_LOADING_MODE);
			if (prop != null) {
				// hide the property loading-mode
				filteredProperties.remove(prop);
			}
		}

		if(!CdmUtils.isCdmDomain(widget)){
			Property prop = widget.findProperty(PropertyTypeConstants.BEAN_PROPERTY_PREFIX);
			if (prop != null) {
				// hide the property loading-mode
				filteredProperties.remove(prop);
			}
			prop = widget.findProperty(PropertyTypeConstants.BEAN_PROPERTY_POSTFIX);
			if (prop != null) {
				// hide the property loading-mode
				filteredProperties.remove(prop);
			}
		}
		return filteredProperties;
	}

}
