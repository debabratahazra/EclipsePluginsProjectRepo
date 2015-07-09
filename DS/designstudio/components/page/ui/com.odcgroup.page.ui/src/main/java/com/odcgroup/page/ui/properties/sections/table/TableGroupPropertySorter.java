package com.odcgroup.page.ui.properties.sections.table;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.ui.views.properties.IPropertySheetEntry;
import org.eclipse.ui.views.properties.PropertySheetSorter;

import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.WidgetLibrary;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.widgets.table.ITableGroup;

/**
 * @author phanikumark
 *
 */
public class TableGroupPropertySorter extends PropertySheetSorter {
	
	private List<String> properties = new ArrayList<String>();

	/*
	 * get the list of properties.
	 */
	public TableGroupPropertySorter() {
		configureProperties();
	}
	
	/**
	 * 
	 */
	private void configureProperties() {
		List<String> propertyTypes = new ArrayList<String>();		
		propertyTypes.add(ITableGroup.GROUP_COLUMN_NAME_PROPERTY);
		propertyTypes.add(ITableGroup.GROUP_SORTING_COLUMN_NAME_PROPERTY);
		propertyTypes.add(ITableGroup.GROUP_SORTING_DIRECTION_PROPERTY);
		propertyTypes.add(ITableGroup.COLUMN_DYNAMIC_COLUMN);
		propertyTypes.add(ITableGroup.GROUP_MAX_PROPERTY);
		propertyTypes.add(ITableGroup.GROUP_COLLAPSED);
		propertyTypes.add(ITableGroup.GROUP_HIERARCHY);
		propertyTypes.add(ITableGroup.GROUP_DATA_AGGR);
		propertyTypes.add(ITableGroup.EVENT_LEVEL);		
		populatePropertyNames(propertyTypes);		
	}
	
	/**
	 * 
	 */
	private void populatePropertyNames(List<String> types) {
		MetaModel mm = MetaModelRegistry.getMetaModel();
		WidgetLibrary library = mm.findWidgetLibrary("xgui");
		EList<PropertyType> list = library.getPropertyTypes();
		for (String name : types) {
			for (PropertyType ptype : list) {
				if (ptype.getName().equals(name)) {
					properties.add(ptype.getDisplayName());
				}
			}
		}
	}

	/*
	 * 
	 * @see
	 * org.eclipse.ui.views.properties.PropertySheetSorter#compare(org.eclipse
	 * .ui.views.properties.IPropertySheetEntry,
	 * org.eclipse.ui.views.properties.IPropertySheetEntry)
	 */
	public int compare(IPropertySheetEntry entryA,
			IPropertySheetEntry entryB) {		
		int aIndex = properties.indexOf(entryA.getDisplayName().trim());
		int bIndex = properties.indexOf(entryB.getDisplayName().trim());
		return aIndex - bIndex;
	}

}