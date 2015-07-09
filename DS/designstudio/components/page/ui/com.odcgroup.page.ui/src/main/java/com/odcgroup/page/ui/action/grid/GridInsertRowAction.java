package com.odcgroup.page.ui.action.grid;

import com.odcgroup.page.ui.action.ActionParameters;
import com.odcgroup.page.ui.command.grid.GridInsertRowCommand;

/**
 * This action inserts a new row at the 'bottom' of the grid
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class GridInsertRowAction extends GridAbstractAction {

	/*
	 * @see com.odcgroup.page.ui.action.AbstractGenericAction#calculateEnabled()
	 */
	protected boolean calculateEnabled() {
		return getGrid() != null;
	}

	/*
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		execute(new GridInsertRowCommand(getGrid()));
	}

	/**
	 * @param parameters
	 */
	public GridInsertRowAction(ActionParameters parameters) {
		super(parameters);
		setEnabled(calculateEnabled());
	}

}
