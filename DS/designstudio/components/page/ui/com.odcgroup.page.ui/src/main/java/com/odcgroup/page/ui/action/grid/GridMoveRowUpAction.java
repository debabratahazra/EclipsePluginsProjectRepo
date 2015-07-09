package com.odcgroup.page.ui.action.grid;

import com.odcgroup.page.model.widgets.GridRow;
import com.odcgroup.page.ui.action.ActionParameters;
import com.odcgroup.page.ui.command.grid.GridMoveRowUpCommand;

/**
 * This action move a row up 
 * @author atr
 * @since DS 1.40.0
 */
public class GridMoveRowUpAction extends GridAbstractAction {
	
	/* 
	 * @see com.odcgroup.page.ui.action.AbstractGenericAction#calculateEnabled()
	 */
	protected boolean calculateEnabled() {
		GridRow row = getRow();
		return row != null ? row.canMoveUp() : false;
	}
	
	/* 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {		
        execute(new GridMoveRowUpCommand(getRow()));
	}
	
	/**
	 * @param parameters
	 */
	public GridMoveRowUpAction(ActionParameters parameters) {
		super(parameters);
		setEnabled(calculateEnabled());
	}

}
