package com.odcgroup.page.ui.command.grid;

import com.odcgroup.page.model.widgets.Grid;
import com.odcgroup.page.model.widgets.GridRow;
import com.odcgroup.page.ui.command.BaseCommand;

/**
 * @author atr
 * @since DS 1.40.0
 */
public class GridMoveRowDownCommand extends BaseCommand {
	
	private Grid grid;
	private int rowIndex;
	
	protected final int getRowIndex(){
		return rowIndex;
	}

	/*
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		grid.moveRowDown(getRowIndex());
	}
	
	/*
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		grid.moveRowUp(getRowIndex()+1);
	}
	
	/**
	 * @param row
	 */
	public GridMoveRowDownCommand(GridRow row) {
		this.grid = row.getGrid();
		this.rowIndex = row.getIndex();
	}

}
