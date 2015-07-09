package com.odcgroup.page.ui.action.table;

import com.odcgroup.page.ui.action.ActionParameters;
import com.odcgroup.page.ui.command.table.TableInsertColumnCommand;

/**
 * This action inserts a new column at the right of the table.
 * 
 * @author atr
 * @since DS 1.40.0
 */
public abstract class TableInsertColumnAction extends AbstractTableAction {

	/**
	 * @see com.odcgroup.page.ui.action.AbstractGenericAction#calculateEnabled()
	 */
	protected boolean calculateEnabled() {
		return getTable() != null;
	}

	/**
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		execute(new TableInsertColumnCommand(getType(), getTable()));
	}

	/**
	 * @param parameters
	 */
	public TableInsertColumnAction(ActionParameters parameters) {
		super(parameters);
	}
	

	/**
	 * @return String
	 */
	public abstract String getType();

}
