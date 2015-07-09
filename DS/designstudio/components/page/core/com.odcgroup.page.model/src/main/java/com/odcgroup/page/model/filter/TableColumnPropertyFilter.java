package com.odcgroup.page.model.filter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.widgets.table.ITableColumn;

/**
 * Filters the Properties of an TableColumn WidgetType.
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class TableColumnPropertyFilter implements PropertyFilter {

	/**
	 * Filters the widget returning a sub-group of the properties.
	 * 
	 * @param widget The Widget to filter
	 * @return List The List of properties
	 */
	public List<Property> filter(Widget widget) {
		
		// These are the WidgetType's of the Label and Field
		MetaModel mm = MetaModelRegistry.getMetaModel();
		String library = widget.getLibraryName();
		WidgetType wt = mm.findWidgetType(library, widget.getTypeName());
		
		// Add all the PropertyTypes which exist 
		Set<PropertyType> allowedTypes = new HashSet<PropertyType>();
		allowedTypes.addAll(wt.getAllPropertyTypes().values());
		
		ITableColumn column = TableHelper.getTableColumn(widget);
		if (column.isComputed()) {
			allowedTypes.remove(mm.findPropertyType(library, "domainAttribute"));
			//allowedTypes.remove(mm.findPropertyType(library, "format"));
			allowedTypes.remove(mm.findPropertyType(library, "column-is-part-of-filter"));
			allowedTypes.remove(mm.findPropertyType(library, "column-based-on-group"));
		} else if (column.isDynamic())  {
			allowedTypes.remove(mm.findPropertyType(library, "domainAttribute"));
			allowedTypes.remove(mm.findPropertyType(library, "column-is-part-of-filter"));
			allowedTypes.remove(mm.findPropertyType(library, "column-display-grouping"));
		} else if (column.isBoundToDomain()){
			allowedTypes.remove(mm.findPropertyType(library, "column-name"));
			allowedTypes.remove(mm.findPropertyType(library, "column-computation"));
			allowedTypes.remove(mm.findPropertyType(library, "column-computation-parameters"));
			allowedTypes.remove(mm.findPropertyType(library, "type"));
			allowedTypes.remove(mm.findPropertyType(library, "column-based-on-group"));
		} else if (column.isPlaceHolder()) {
			allowedTypes.remove(mm.findPropertyType(library, "domainAttribute"));
			allowedTypes.remove(mm.findPropertyType(library, "column-computation"));
			allowedTypes.remove(mm.findPropertyType(library, "column-computation-parameters"));
			allowedTypes.remove(mm.findPropertyType(library, "format"));
			allowedTypes.remove(mm.findPropertyType(library, "column-is-part-of-filter"));
			allowedTypes.remove(mm.findPropertyType(library, "column-based-on-group"));
			//allowedTypes.remove(mm.findPropertyType(library, "column-sortable"));
		} 		
		
		
		allowedTypes.remove(mm.findPropertyType(library, "column-display-grouping"));			
		

		allowedTypes.remove(mm.findPropertyType(library, "table-column-halign"));
		allowedTypes.remove(mm.findPropertyType(library, "format"));
		
		List<Property> result = new ArrayList<Property>();
		for (Property p : widget.getProperties()) {
			if (allowedTypes.contains(p.getType())) {
				result.add(p);
			}
		}
		
		return result;
	}

}
