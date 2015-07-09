package com.odcgroup.page.model.widgets.table.impl;

import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.widgets.table.ITableColumn;
import com.odcgroup.page.model.widgets.table.ITableColumnItem;

/**
 *
 * @author pkk
 *
 */
public class TableColumnItem extends WidgetAdapter implements ITableColumnItem {	
	
	/** */
	private static final String COLUMN_ATTRIBUTE = "item-column";

	/** the name of the property that stores the display format */
	private static final String DISPLAY_FORMAT_PROPERTY = "format";

	/**
	 * @param widget
	 */
	protected TableColumnItem(Widget widget) {
		super(widget);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableColumnItem#getColumn()
	 */
	public String getColumn() {
		return getWidget().getPropertyValue(COLUMN_ATTRIBUTE);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableColumnItem#setColumn(java.lang.String)
	 */
	public void setColumn(String column) {
		getProperty(COLUMN_ATTRIBUTE).setValue(column);		
	}
	
	@Override
	public ITableColumn getTableColumn() {
		ITableColumn column = null;
		Widget parent = getWidget().findAncestor(WidgetTypeConstants.TABLE_COLUMN);
		if (parent != null) {
			column = TableHelper.getTableColumn(parent);
		}
		return column;
	}

	@Override
	public boolean isNewLine() {
		boolean retval = false;
		Property prop = getWidget().findProperty("newLine");
		if (prop != null) {
			retval = prop.getBooleanValue();
		}
		return retval;
	}

	@Override
	public String getItemPercentageWidth() {
		String retval = "";
		Property prop = getWidget().findProperty("item-width");
		if (prop != null) {
			retval = prop.getValue();
		}
		return retval;
	}
	
	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableColumn#getDisplayFormat()
	 */
	public String getDisplayFormat() {
		String retval = "";
		Property prop = getWidget().findProperty(DISPLAY_FORMAT_PROPERTY);
		if (prop != null) {
			retval = prop.getValue();
		}
		return retval;
	}

	@Override
	public String getHorizontalAlignment() {
		String result = "";	
		Property p = getProperty("item-halign");	
		if (p != null) {
			result = p.getValue();
		}
        return result;
	}
	

}
