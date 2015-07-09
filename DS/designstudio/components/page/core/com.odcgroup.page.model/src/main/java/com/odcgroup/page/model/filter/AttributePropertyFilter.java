package com.odcgroup.page.model.filter;

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
import com.odcgroup.page.model.filter.cdm.CdmGenericPropertyFilter;
import com.odcgroup.page.model.util.CdmUtils;

/**
 * Filters the Properties of an Attribute WidgetType.
 * Only properties which are intrinsic to the Attribute WidgetType OR
 * intrinsic to the current FieldType are returned.
 * 
 * @author Gary Hayes
 */
public class AttributePropertyFilter implements PropertyFilter {

	/**
	 * Filters the widget returning a sub-group of the properties.
	 * 
	 * @param widget The Widget to filter
	 * @return List The List of properties
	 */
	public List<Property> filter(Widget widget) {
		if (CdmUtils.isCdmDomain(widget)) {
			return new CdmGenericPropertyFilter().filter(widget);
		}
		
		String type = widget.getPropertyValue(PropertyTypeConstants.FIELD_TYPE);
		
		MetaModel mm = getMetaModel();
		
		// These are the WidgetType's of the Label and Field
		WidgetType lwt = mm.findWidgetType(WidgetLibraryConstants.XGUI, WidgetTypeConstants.LABEL);
		WidgetType fwt = mm.findWidgetType(WidgetLibraryConstants.XGUI, type);
		
		// Add all the PropertyTypes which exist for both the Label and the Field
		Set<PropertyType> allowedTypes = new HashSet<PropertyType>();
		allowedTypes.addAll(lwt.getAllPropertyTypes().values());
		allowedTypes.addAll(fwt.getAllPropertyTypes().values());
		
		// Remove PropertyType's which are determined by the Corporate Design
		allowedTypes.remove(mm.findPropertyType(WidgetLibraryConstants.XGUI, PropertyTypeConstants.HORIZONTAL_ALIGNMENT));
		allowedTypes.remove(mm.findPropertyType(WidgetLibraryConstants.XGUI, PropertyTypeConstants.VERTICAL_ALIGNMENT));
		
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
