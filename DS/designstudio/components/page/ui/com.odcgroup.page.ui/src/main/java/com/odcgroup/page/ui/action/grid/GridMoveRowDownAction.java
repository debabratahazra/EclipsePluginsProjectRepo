package com.odcgroup.page.ui.action.grid;

import com.odcgroup.page.model.widgets.GridRow;
import com.odcgroup.page.ui.action.ActionParameters;
import com.odcgroup.page.ui.command.grid.GridMoveRowDownCommand;

/**
 * This action move a row down 
 * @author atr
 * @since DS 1.40.0
 */
public class GridMoveRowDownAction extends GridAbstractAction {
	
	/* 
	 * @see com.odcgroup.page.ui.action.AbstractGenericAction#calculateEnabled()
	 */
	protected boolean calculateEnabled() {
		GridRow row = getRow();
		return row != null ? row.canMoveDown() : false;
	}
	
	/* 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {		
        execute(new GridMoveRowDownCommand(getRow()));
	}
	
	/**
	 * @param parameters
	 */
	public GridMoveRowDownAction(ActionParameters parameters) {
		super(parameters);
		setEnabled(calculateEnabled());
	}

}
