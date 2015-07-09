package com.odcgroup.page.ui.properties.sections.table;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.IFilter;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.util.AdaptableUtils;

/**
 * This filter is used to hide/show Table Specific Tabs. 
 * 
 * @author atr,pkk
 * @since DS 1.40.0
 */
public class TableSectionFilter implements IFilter {
	
	/** Names of widget for which the description tab shall no be displayed. */
	private Set<String> widgetNames = new HashSet<String>();
	
	/**
	 * Initializes this filter.
	 */
	private void init() {
		widgetNames.add("TableTree");
	}

	/** 
	 * @param object the object to be filtered
	 * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
	 */
	public boolean select(Object object) {
		if (object instanceof EditPart) {
			Widget w = (Widget) AdaptableUtils.getAdapter(object, Widget.class);
			return widgetNames.contains(w.getTypeName());
		}
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * Constructor
	 */
	public TableSectionFilter() {
		init();
	}

}
