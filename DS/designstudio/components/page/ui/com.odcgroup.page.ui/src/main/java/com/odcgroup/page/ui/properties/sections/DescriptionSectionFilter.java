package com.odcgroup.page.ui.properties.sections;

import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.IFilter;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.util.AdaptableUtils;

/**
 * This filter is used to filter a description section from display for a
 * selection even though it meets the input type criteria.
 * 
 * @author <a href="mailto:atripod@odyssey-group.com">Alain Tripod</a>
 * @version 1.0
 */
public class DescriptionSectionFilter implements IFilter {

	/** Names of widget for which the description tab shall no be displayed. */
//	private Set<String> widgetNames = new HashSet<String>();

	/**
	 * Initializes this filter.
	 */
	/*
	 * private void init() { widgetNames.add(WidgetTypeConstants.ATTRIBUTE);
	 * widgetNames.add(WidgetTypeConstants.BOX);
	 * widgetNames.add(WidgetTypeConstants.COLUMN);
	 * widgetNames.add(WidgetTypeConstants.COLUMN_HEADER);
	 * widgetNames.add(WidgetTypeConstants.COLUMN_BODY);
	 * widgetNames.add(WidgetTypeConstants.CONDITIONAL);
	 * widgetNames.add(WidgetTypeConstants.GLUE);
	 * widgetNames.add(WidgetTypeConstants.LABEL);
	 * widgetNames.add(WidgetTypeConstants.SPACER);
	 * widgetNames.add(WidgetTypeConstants.TABBED_PANE);
	 * widgetNames.add(WidgetTypeConstants.MATRIX_CELL);
	 * widgetNames.add(WidgetTypeConstants.MATRIX_CONTENTCELL);
	 * widgetNames.add(WidgetTypeConstants.MATRIX_EXTRACOLUMN); }
	 */

	/**
	 * @param object
	 *            the object to be filtered
	 * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
	 */
	public boolean select(Object object) {
		if (object instanceof EditPart) {
			Widget w = (Widget) AdaptableUtils.getAdapter(object, Widget.class);
			Property p = w.findProperty(PropertyTypeConstants.DOCUMENTATION);
			if (p != null) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * Constructor
	 */
	public DescriptionSectionFilter() {
		// init();
	}

}
