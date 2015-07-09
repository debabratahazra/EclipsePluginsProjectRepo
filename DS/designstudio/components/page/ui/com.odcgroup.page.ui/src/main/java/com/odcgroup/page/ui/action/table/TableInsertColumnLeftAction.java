package com.odcgroup.page.ui.action.table;

import com.odcgroup.page.ui.action.ActionParameters;
import com.odcgroup.page.ui.command.table.TableInsertColumnLeftCommand;

/**
 * This action inserts a column to the left of the selected column.
 * 
 * @author atr
 * @since DS 1.40.0
 */
public abstract class TableInsertColumnLeftAction extends AbstractTableAction {

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
		execute(new TableInsertColumnLeftCommand(getColumn(), getType()));
	}

	/**
	 * @param parameters
	 */
	public TableInsertColumnLeftAction(ActionParameters parameters) {
		super(parameters);
		setEnabled(calculateEnabled());
	}

	/**
	 * @return String
	 */
	public abstract String getType();

}
