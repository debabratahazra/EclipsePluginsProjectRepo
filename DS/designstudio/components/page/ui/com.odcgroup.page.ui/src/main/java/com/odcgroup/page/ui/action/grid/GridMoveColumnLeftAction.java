package com.odcgroup.page.ui.action.grid;

import com.odcgroup.page.model.widgets.GridColumn;
import com.odcgroup.page.ui.action.ActionParameters;
import com.odcgroup.page.ui.command.grid.GridMoveColumnLeftCommand;

/**
 * This action move a column to the left 
 * @author atr
 * @since DS 1.40.0
 */
public class GridMoveColumnLeftAction extends GridAbstractAction {

	/* 
	 * @see com.odcgroup.page.ui.action.AbstractGenericAction#calculateEnabled()
	 */
	protected boolean calculateEnabled() {
		GridColumn column = getColumn();
		return column != null ? column.canMoveLeft() : false;
	}
	
	/* 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {		
        execute(new GridMoveColumnLeftCommand(getColumn()));
	}
	
	/**
	 * @param parameters
	 */
	public GridMoveColumnLeftAction(ActionParameters parameters) {
		super(parameters);
		setEnabled(calculateEnabled());
	}

}
