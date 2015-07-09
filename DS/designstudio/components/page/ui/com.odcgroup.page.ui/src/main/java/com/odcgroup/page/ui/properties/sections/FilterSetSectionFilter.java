package com.odcgroup.page.ui.properties.sections;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.IFilter;

import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.util.AdaptableUtils;

/**
 *
 * @author pkk
 *
 */
public class FilterSetSectionFilter implements IFilter {

	
	/** Names of widget for which the description tab shall no be displayed. */
	private static Set<String> widgetNames = new HashSet<String>();
	
	/**
	 * Initializes this filter.
	 */
	static {
		widgetNames.add(WidgetTypeConstants.AUTO_COMPLETE);
	}

	/**
	 * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
	 */
	public boolean select(Object object) {
		if (object instanceof EditPart) {
			Widget w = (Widget) AdaptableUtils.getAdapter(object, Widget.class);
			return widgetNames.contains(w.getTypeName());
		}
		return false;
	}

}
