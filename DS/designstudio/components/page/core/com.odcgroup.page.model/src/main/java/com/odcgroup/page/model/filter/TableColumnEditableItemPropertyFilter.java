package com.odcgroup.page.model.filter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;

/**
 */
public class TableColumnEditableItemPropertyFilter implements PropertyFilter {
		
	@Override
	public List<Property> filter(Widget widget) {
		
		// These are the WidgetType's of the Label and Field
		MetaModel mm = MetaModelRegistry.getMetaModel();
		String library = widget.getLibraryName();
		WidgetType wt = mm.findWidgetType(library, widget.getTypeName());

		// Add all the PropertyTypes which exist 
		Set<PropertyType> allowedTypes = new HashSet<PropertyType>();
		allowedTypes.addAll(wt.getAllPropertyTypes().values());		

		// hide some properties
		allowedTypes.remove(mm.findPropertyType(library, PropertyTypeConstants.EDITABLE));
		
		List<Property> result = new ArrayList<Property>();
		for (Property p : widget.getProperties()) {
			if (allowedTypes.contains(p.getType())) {
				result.add(p);
			}
		}
		return result;
	}

}
