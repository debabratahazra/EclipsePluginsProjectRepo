package com.odcgroup.page.ui.action.table;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.model.widgets.table.ITableColumn;
import com.odcgroup.page.model.widgets.table.ITableGroup;
import com.odcgroup.page.ui.action.AbstractGenericAction;
import com.odcgroup.page.ui.action.ActionParameters;

/**
 * This is the base class for all actions on Table/Tree.
 * 
 * @author atr
 * @since DS 1.40.0
 */
class AbstractTableAction extends AbstractGenericAction {

	/**
	 * @return a ITableColumn adapter for the selected Table Column Widget or
	 *         {@code null} if the widget does not represents a table column. In
	 *         that case it certainly means there is somewhere a programming
	 *         error.
	 */
	protected ITableColumn getColumn() {
		ITableColumn column = null;
		Widget widget = getSelectedWidget();
		if (widget.getTypeName().equals("TableGroup")) {
			column = TableHelper.getTableColumn(widget.getParent());
		}
		else if (widget.getTypeName().equals("TableColumn")) {
			column = TableHelper.getTableColumn(widget);
		}
		return column;
	}
	
	protected ITableGroup getGroup() {
		ITableGroup group = null;
		Widget widget = getSelectedWidget();
		if (widget.getTypeName().equals("TableGroup")) {
			group = TableHelper.getTableGroup(widget);
		}
		return group;
	}

	/**
	 * @return a ITable adapter for the selected Widget or {@code null} if the
	 *         selected widget represents neither a table nor a table column. In
	 *         that case it certainly means there is somewhere a programming
	 *         error.
	 */
	protected ITable getTable() {
		ITable table = null;
		ITableColumn column = getColumn();
		if (column != null) {
			table = column.getTable();
		} else {
			Widget widget = getSelectedWidget();
			if (widget.getTypeName().equals("TableTree")) {
				table = TableHelper.getTable(widget);
			}
		}
		return table;
	}

	/**
	 * @param parameters
	 */
	protected AbstractTableAction(ActionParameters parameters) {
		super(parameters);
		setEnabled(calculateEnabled());
	}

}
