package com.odcgroup.page.model.util;

import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.widgets.IAutoCompleteDesign;
import com.odcgroup.page.model.widgets.ISearchField;
import com.odcgroup.page.model.widgets.impl.AutoCompleteDesign;
import com.odcgroup.page.model.widgets.impl.SearchField;

public class WidgetHelper {
	
	/**
	 * @param widget
	 * @return
	 */
	public static IAutoCompleteDesign getAutoCompleteDesign(Widget widget) {
		if (!"AutoCompleteDesign".equals(widget.getTypeName())) {
			throw new IllegalArgumentException("This is not a AutoCompleteDesign widget");
		}
		return new AutoCompleteDesign(widget);
	}
	
	/**
	 * @param widget
	 * @return
	 */
	public static ISearchField getSearchField(Widget widget) {
		if (!WidgetTypeConstants.SEARCH_FIELD.equals(widget.getTypeName()) &&
				!WidgetTypeConstants.TABLE_COLUMN_SEARCH_ITEM.equals(widget.getTypeName())) {
			throw new IllegalArgumentException("This is not a SearchField widget");
		}
		return new SearchField(widget);
	}

}
