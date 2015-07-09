package com.odcgroup.page.ui.action.grid;

import com.odcgroup.page.ui.action.ActionParameters;
import com.odcgroup.page.ui.command.grid.GridInsertColumnLeftCommand;

/**
 * This action inserts a new row just after the selected one
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class GridInsertColumnLeftAction extends GridAbstractAction {

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
		execute(new GridInsertColumnLeftCommand(getColumn()));
	}

	/**
	 * @param parameters
	 */
	public GridInsertColumnLeftAction(ActionParameters parameters) {
		super(parameters);
		setEnabled(calculateEnabled());
	}

}
