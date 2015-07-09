package com.odcgroup.page.ui.action.grid;

import com.odcgroup.page.model.widgets.GridCell;
import com.odcgroup.page.ui.action.ActionParameters;
import com.odcgroup.page.ui.command.grid.GridMoveCellRightCommand;

/**
 * This action move the grid cell to to right 
 * @author atr
 * @since DS 1.40.0
 */
public class GridMoveCellRightAction extends GridAbstractAction {

	/* 
	 * @see com.odcgroup.page.ui.action.AbstractGenericAction#calculateEnabled()
	 */
	protected boolean calculateEnabled() {
		GridCell cell = getCell();
		return cell != null ? cell.canMoveRight() : false;
	}
	
	/* 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {		
        execute(new GridMoveCellRightCommand(getCell()));
	}
	
	/**
	 * @param parameters
	 */
	public GridMoveCellRightAction(ActionParameters parameters) {
		super(parameters);
		setEnabled(calculateEnabled());
	}

}
