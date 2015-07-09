package com.odcgroup.page.ui.action.grid;

import com.odcgroup.page.ui.action.ActionParameters;
import com.odcgroup.page.ui.command.grid.GridInsertColumnRightCommand;

/**
 * This action inserts a new row just after the selected one
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class GridInsertColumnRightAction extends GridAbstractAction {

	/*
	 * @see com.odcgroup.page.ui.action.AbstractGenericAction#calculateEnabled()
	 */
	protected boolean calculateEnabled() {
		return getColumn() != null;
	}

	/*
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		execute(new GridInsertColumnRightCommand(getColumn()));
	}

	/**
	 * @param parameters
	 */
	public GridInsertColumnRightAction(ActionParameters parameters) {
		super(parameters);
		setEnabled(calculateEnabled());
	}

}
