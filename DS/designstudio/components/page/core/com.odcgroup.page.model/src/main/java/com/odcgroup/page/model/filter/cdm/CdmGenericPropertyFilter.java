package com.odcgroup.page.model.filter.cdm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetLibraryConstants;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.filter.PropertyFilter;

/**
 * Property filter specific to CDM. This is used by the Attribute Widget when the domain model
 * is CDM.
 * 
 * @author Gary Hayes
 */
public class CdmGenericPropertyFilter implements PropertyFilter {

	/**
	 * Filters the widget returning a sub-group of the properties.
	 * 
	 * @param widget The Widget to filter
	 * @return List The List of properties
	 */
	public List<Property> filter(Widget widget) {
		MetaModel mm = getMetaModel();
		
		// These are the WidgetType's of the CDM Generic Widget
		WidgetType cwt = mm.findWidgetType(WidgetLibraryConstants.CDM_COMP, WidgetTypeConstants.CDM_GENERIC);
		
		// Add all the PropertyTypes which exist for this Widget
		Set<PropertyType> allowedTypes = new HashSet<PropertyType>();
		allowedTypes.addAll(cwt.getAllPropertyTypes().values());
			
		// Add intrinsic Attribute WidgetType's PropertyType's
		allowedTypes.add(mm.findPropertyType(WidgetLibraryConstants.XGUI, PropertyTypeConstants.FIELD_TYPE));
		allowedTypes.add(mm.findPropertyType(WidgetLibraryConstants.XGUI, PropertyTypeConstants.SHOW_LABEL));
		
		List<Property> result = new ArrayList<Property>();
		
		for (Property p : widget.getProperties()) {
			if (allowedTypes.contains(p.getType())) {
				result.add(p);
			}
		}
		
		return result;
	}
	
	/**
	 * Gets the MetaModel.
	 * 
	 * @return MetaModel
	 */
	private MetaModel getMetaModel() {
		return MetaModelRegistry.getMetaModel();
	}
}