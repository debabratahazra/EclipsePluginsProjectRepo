package com.odcgroup.page.ui.action.grid;

import com.odcgroup.page.ui.action.ActionParameters;
import com.odcgroup.page.ui.command.grid.GridInsertRowBelowCommand;

/**
 * This action inserts a new row just below the selected one
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class GridInsertRowBelowAction extends GridAbstractAction {

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
		execute(new GridInsertRowBelowCommand(getRow()));
	}

	/**
	 * @param parameters
	 */
	public GridInsertRowBelowAction(ActionParameters parameters) {
		super(parameters);
		setEnabled(calculateEnabled());
	}

}
