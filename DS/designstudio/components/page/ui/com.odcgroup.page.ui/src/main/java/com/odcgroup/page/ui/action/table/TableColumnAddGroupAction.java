package com.odcgroup.page.ui.action.table;

import java.util.List;

import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.model.widgets.table.ITableColumn;
import com.odcgroup.page.ui.action.ActionParameters;
import com.odcgroup.page.ui.command.table.TableColumnAddGroupCommand;

/**
 * @author phanikumark
 *
 */
public class TableColumnAddGroupAction extends AbstractTableAction {

	/**
	 * @param parameters
	 */
	public TableColumnAddGroupAction(ActionParameters parameters) {
		super(parameters);
	}
	
	/**
	 * @see com.odcgroup.page.ui.action.AbstractGenericAction#calculateEnabled()
	 */
	protected boolean calculateEnabled() {
		Widget widget = getSelectedWidget();
		String wt = widget.getTypeName();
		if (WidgetTypeConstants.TABLE_COLUMN.equals(wt)) {
			ITableColumn col = TableHelper.getTableColumn(widget);
			ITableColumn disp = getDisplayGroupingColumn(col.getTable());
			if (disp != null) {
				if (col.isDisplayGrouping()) {
					return true;
				}
			} else {
				if (col.isPlaceHolder() || col.isBoundToDomain()) {
					return true;
				} 
			}
		}
		return false;
	}

	/**
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		execute(new TableColumnAddGroupCommand(getColumn()));
	}
	
	/**
	 * @param table
	 * @return
	 */
	private ITableColumn getDisplayGroupingColumn(ITable table) {
		List<ITableColumn> columns = table.getColumns();
		for (ITableColumn column : columns) {
			if (column.isDisplayGrouping()) {
				return column;
			}
		}
		return null;
	}

}
