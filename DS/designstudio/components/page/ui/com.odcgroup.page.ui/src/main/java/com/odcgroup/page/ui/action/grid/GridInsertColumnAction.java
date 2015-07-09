package com.odcgroup.page.ui.action.grid;

import com.odcgroup.page.ui.action.ActionParameters;
import com.odcgroup.page.ui.command.grid.GridInsertColumnCommand;

/**
 * This action inserts a new column at the right of the grid
 * @author atr
 * @since DS 1.40.0
 */
public class GridInsertColumnAction extends GridAbstractAction {

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
        execute(new GridInsertColumnCommand(getGrid()));
	}
	
	/**
	 * @param parameters
	 */
	public GridInsertColumnAction(ActionParameters parameters) {
		super(parameters);
	}

}
