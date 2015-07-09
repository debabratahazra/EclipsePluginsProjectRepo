package com.odcgroup.page.ui.action.table;

import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.action.ActionParameters;
import com.odcgroup.page.ui.command.table.TableColumnAddItemCommand;

/**
 * This action inserts a new column at the right of the table.
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class TableColumnAddItemAction extends AbstractTableAction {

	/**
	 * @see com.odcgroup.page.ui.action.AbstractGenericAction#calculateEnabled()
	 */
	protected boolean calculateEnabled() {
		Widget widget = getSelectedWidget();
		String wt = widget.getTypeName();
		if (WidgetTypeConstants.TABLE_COLUMN.equals(wt)) {
			if (! (getColumn().isPlaceHolder() || getColumn().isNeverVisible()) ) {
				return true;
			} 
		}
		return false;
	}

	/**
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		execute(new TableColumnAddItemCommand(getColumn()));
	}

	/**
	 * @param parameters
	 */
	public TableColumnAddItemAction(ActionParameters parameters) {
		super(parameters);
	}

}
