package com.odcgroup.page.ui.action.grid;

import com.odcgroup.page.ui.action.ActionParameters;
import com.odcgroup.page.ui.command.grid.GridInsertRowAboveCommand;

/**
 * This action inserts a new row just above the selected one
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class GridInsertRowAboveAction extends GridAbstractAction {

	/*
	 * @see com.odcgroup.page.ui.action.AbstractGenericAction#calculateEnabled()
	 */
	protected boolean calculateEnabled() {
		return getRow() != null;
	}

	/*
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		execute(new GridInsertRowAboveCommand(getRow()));
	}

	/**
	 * @param parameters
	 */
	public GridInsertRowAboveAction(ActionParameters parameters) {
		super(parameters);
		setEnabled(calculateEnabled());
	}

}
