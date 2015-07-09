package com.odcgroup.page.ui.action.grid;

import com.odcgroup.page.ui.action.ActionParameters;
import com.odcgroup.page.ui.command.grid.GridDeleteRowCommand;

/**
 * This action deletes one row an all its content from the grid
 * @author atr
 * @since DS 1.40.0
 */
public class GridDeleteRowAction extends GridAbstractAction {

	/* 
	 * @see com.odcgroup.page.ui.action.AbstractGenericAction#calculateEnabled()
	 */
	protected boolean calculateEnabled() {
		return getCell() != null;
	}
	
	/* 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {		
        execute(new GridDeleteRowCommand(getRow()));
	}
	
	/**
	 * @param parameters
	 */
	public GridDeleteRowAction(ActionParameters parameters) {
		super(parameters);
		setEnabled(calculateEnabled());
	}

}
