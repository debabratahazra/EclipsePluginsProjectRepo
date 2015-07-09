package com.odcgroup.page.model.filter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.widgets.table.ITable;

/**
 * filters the properties that are not required by TableTree widget
 *
 * @author pkk
 *
 */
public class TableTreePropertyFilter implements PropertyFilter {

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.model.filter.PropertyFilter#filter(com.odcgroup.page.model.Widget)
	 */
	public List<Property> filter(Widget widget) {
		
		MetaModel mm = MetaModelRegistry.getMetaModel();
		String library = widget.getLibraryName();
		WidgetType wt = mm.findWidgetType(library, widget.getTypeName());
		
		// Add all the PropertyTypes which exist 
		Set<PropertyType> allowedTypes = new HashSet<PropertyType>();
		allowedTypes.addAll(wt.getAllPropertyTypes().values());
		
		//DS-3736 remove the sticky flag
		PropertyType type = mm.findPropertyType(library, "sticky");
		if (type != null)
			allowedTypes.remove(type);
		
		//DS-5122 
		Widget root = widget.getRootWidget();
		if (WidgetTypeConstants.MODULE.equals(root.getTypeName())) {
			String searchType = root.getPropertyValue(PropertyTypeConstants.SEARCH);
			if (!searchType.equals("none")) {
				allowedTypes.remove(mm.findPropertyType(library, PropertyTypeConstants.TABLE_EDITABLE_DATASET));
				allowedTypes.remove(mm.findPropertyType(library, PropertyTypeConstants.TABLE_EDITABLE_ROW_IDENTIFIER));
				allowedTypes.remove(mm.findPropertyType(library, PropertyTypeConstants.TABLE_FORMAT_ROW_PERMISSION));
				allowedTypes.remove(mm.findPropertyType(library, PropertyTypeConstants.TABLE_EDITABLE_FORMAT_IDENTIFIER));
			}
		}
		
		ITable table = TableHelper.getTable(widget);
		if (!TableHelper.getTableUtilities().isDetailedDelegatedRenderingMode(table.getRenderingMode())) {
			allowedTypes.remove(mm.findPropertyType(library, PropertyTypeConstants.TABLE_COUNT_TREE_ELEMENTS));
		}
		
		//DS-4871 checkbox grouping
		allowedTypes.remove(mm.findPropertyType(library, "display-checkbox"));
		List<Property> result = new ArrayList<Property>();
		for (Property p : widget.getProperties()) {
			if (allowedTypes.contains(p.getType())) {
				result.add(p);
			}
		}
		
		return result;
	}

}
