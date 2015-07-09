package com.odcgroup.page.ui.action.table;

import com.odcgroup.page.ui.action.ActionParameters;
import com.odcgroup.page.ui.command.table.TableDeleteColumnCommand;

/**
 * This action deletes one column an all its content from the table.
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class TableDeleteColumnAction extends AbstractTableAction {

	/* 
	 * @see com.odcgroup.page.ui.action.AbstractGenericAction#calculateEnabled()
	 */
	protected boolean calculateEnabled() {
		return getColumn() != null;
	}
	
	/* 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {		
        execute(new TableDeleteColumnCommand(getColumn()));
	}
	
	/**
	 * @param parameters
	 */
	public TableDeleteColumnAction(ActionParameters parameters) {
		super(parameters);
		setEnabled(calculateEnabled());
	}

}
