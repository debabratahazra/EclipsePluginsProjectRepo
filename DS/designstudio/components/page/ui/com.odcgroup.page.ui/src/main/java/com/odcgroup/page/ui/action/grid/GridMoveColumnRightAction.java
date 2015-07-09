package com.odcgroup.page.ui.action.grid;

import com.odcgroup.page.model.widgets.GridColumn;
import com.odcgroup.page.ui.action.ActionParameters;
import com.odcgroup.page.ui.command.grid.GridMoveColumnRightCommand;

/**
 * This action move a column to the right 
 * @author atr
 * @since DS 1.40.0
 */
public class GridMoveColumnRightAction extends GridAbstractAction {
	
	/* 
	 * @see com.odcgroup.page.ui.action.AbstractGenericAction#calculateEnabled()
	 */
	protected boolean calculateEnabled() {
		GridColumn column = getColumn();
		return column != null ? column.canMoveRight() : false;
	}
	
	/* 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {		
        execute(new GridMoveColumnRightCommand(getColumn()));
	}
	
	/**
	 * @param parameters
	 */
	public GridMoveColumnRightAction(ActionParameters parameters) {
		super(parameters);
		setEnabled(calculateEnabled());
	}

}
