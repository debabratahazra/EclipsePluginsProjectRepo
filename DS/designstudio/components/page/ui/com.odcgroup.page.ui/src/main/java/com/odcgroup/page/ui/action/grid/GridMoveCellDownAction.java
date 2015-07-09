package com.odcgroup.page.ui.action.grid;

import com.odcgroup.page.model.widgets.GridCell;
import com.odcgroup.page.ui.action.ActionParameters;
import com.odcgroup.page.ui.command.grid.GridMoveCellDownCommand;

/**
 * This action move the grid cell down 
 * @author atr
 * @since DS 1.40.0
 */
public class GridMoveCellDownAction extends GridAbstractAction {
	
	/* 
	 * @see com.odcgroup.page.ui.action.AbstractGenericAction#calculateEnabled()
	 */
	protected boolean calculateEnabled() {
		GridCell cell = getCell();
		return cell != null ? cell.canMoveDown() : false;
	}
	
	/* 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {		
        execute(new GridMoveCellDownCommand(getCell()));
        // Since the Command removes and then adds the Widget GEF removes the old 
        // edit part and then creates a new one. We need to find the new one 
        // and reselect it
        //selectEditPart(cellWidget, oldEditPart, viewer);
        
	}
	
	/**
	 * @param parameters
	 */
	public GridMoveCellDownAction(ActionParameters parameters) {
		super(parameters);
        setEnabled(calculateEnabled());
	}

}
