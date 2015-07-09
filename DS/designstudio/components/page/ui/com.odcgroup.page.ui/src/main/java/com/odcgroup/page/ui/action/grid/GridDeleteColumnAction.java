package com.odcgroup.page.ui.action.grid;

import com.odcgroup.page.ui.action.ActionParameters;
import com.odcgroup.page.ui.command.grid.GridDeleteColumnCommand;

/**
 * This action deletes one column an all its content from the grid
 * @author atr
 * @since DS 1.40.0
 */
public class GridDeleteColumnAction extends GridAbstractAction {

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
        execute(new GridDeleteColumnCommand(getColumn()));
	}
	
	/**
	 * @param parameters
	 */
	public GridDeleteColumnAction(ActionParameters parameters) {
		super(parameters);
		setEnabled(calculateEnabled());
	}

}
