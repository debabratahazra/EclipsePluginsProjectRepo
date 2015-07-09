package com.odcgroup.page.model.widgets.table.impl;

import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.model.widgets.table.ITableFeature;
import com.odcgroup.page.model.widgets.table.ITableGroup;

/**
 * TODO: Document me!
 * 
 * @author atr
 * @since DS 1.40.0
 */
public abstract class TableFeature extends WidgetAdapter implements ITableFeature {

	/**
	 * @param widget
	 */
	public TableFeature(Widget widget) {
		super(widget);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableFeature#getTable()
	 */
	public final ITable getTable() {
		ITable table = null;
		Widget parent = getWidget().getParent();
		if (parent != null) {
			table = new Table(parent);
		}
		return table;
	}
	
	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableFeature#getTable()
	 */
	public final ITable getTable(ITableGroup group) {
		ITable table = null;
		Widget parent = null;
		if(getWidget().getParent().getTypeName().equals(WidgetTypeConstants.TABLE_COLUMN)) {
			parent = getWidget().getParent().getParent();
		}
		if (parent != null) {
			table = new Table(parent);
		}
		return table;
	}

}
