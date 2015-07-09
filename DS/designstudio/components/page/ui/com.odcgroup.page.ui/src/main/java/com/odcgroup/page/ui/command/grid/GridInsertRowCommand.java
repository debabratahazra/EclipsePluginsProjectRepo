package com.odcgroup.page.ui.command.grid;

import com.odcgroup.page.model.widgets.Grid;
import com.odcgroup.page.ui.command.BaseCommand;

/**
 * @author atr
 * @since DS 1.40.0
 */
public class GridInsertRowCommand extends BaseCommand {
	
	private Grid grid;
	private int rowIndex;
	
	protected final Grid getGrid() {
		return this.grid;
	}
	
	protected final int getRowIndex() {
		return this.rowIndex;
	}

	/*
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		rowIndex = getGrid().insertRow();
	}
	
	/*
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		getGrid().removeRow(getRowIndex());
	}
	
	/**
	 * @param grid
	 */
	public GridInsertRowCommand(Grid grid) {
		this.grid = grid;
	}

}
