package com.odcgroup.page.ui.action.grid;

import com.odcgroup.page.model.widgets.GridCell;
import com.odcgroup.page.ui.action.ActionParameters;
import com.odcgroup.page.ui.command.grid.GridMoveCellUpCommand;

/**
 * This action move the grid cell up 
 * @author atr
 * @since DS 1.40.0
 */
public class GridMoveCellUpAction extends GridAbstractAction {
	
	/* 
	 * @see com.odcgroup.page.ui.action.AbstractGenericAction#calculateEnabled()
	 */
	protected boolean calculateEnabled() {
		GridCell cell = getCell();
		return cell != null ? cell.canMoveUp() : false;
	}
	
	/* 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {		
        execute(new GridMoveCellUpCommand(getCell()));
	}
	
	/**
	 * @param parameters
	 */
	public GridMoveCellUpAction(ActionParameters parameters) {
		super(parameters);
		setEnabled(calculateEnabled());
	}

}
