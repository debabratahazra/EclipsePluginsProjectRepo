package com.odcgroup.page.ui.action.table;

import com.odcgroup.page.ui.action.ActionParameters;
import com.odcgroup.page.ui.command.table.TableInsertColumnRightCommand;

/**
 * This action inserts a column to the right of the selected column.
 * 
 * @author atr
 * @since DS 1.40.0
 */
public abstract class TableInsertColumnRightAction extends AbstractTableAction {	

	/**
	 * @see com.odcgroup.page.ui.action.AbstractGenericAction#calculateEnabled()
	 */
	protected boolean calculateEnabled() {
		return getColumn() != null;
	}

	/**
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		execute(new TableInsertColumnRightCommand(getColumn(), getType()));
	}

	/**
	 * @param parameters
	 */
	public TableInsertColumnRightAction(ActionParameters parameters) {
		super(parameters);
		setEnabled(calculateEnabled());
	}

	/**
	 * @return String
	 */
	public abstract String getType();

}
