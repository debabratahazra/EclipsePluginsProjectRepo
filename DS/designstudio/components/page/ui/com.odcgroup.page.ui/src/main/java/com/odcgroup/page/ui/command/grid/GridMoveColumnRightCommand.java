package com.odcgroup.page.ui.command.grid;

import com.odcgroup.page.model.widgets.Grid;
import com.odcgroup.page.model.widgets.GridColumn;
import com.odcgroup.page.ui.command.BaseCommand;

/**
 * @author atr
 * @since DS 1.40.0
 */
public class GridMoveColumnRightCommand extends BaseCommand {
	
	private Grid grid;
	private int columnIndex;
	
	protected final int getColumnIndex() {
		return this.columnIndex;
	}

	/*
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		grid.moveColumnRight(getColumnIndex());
	}
	
	/*
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		grid.moveColumnLeft(getColumnIndex()+1);
	}
	
	/**
	 * @param column
	 */
	public GridMoveColumnRightCommand(GridColumn column) {
		this.grid = column.getGrid();
		this.columnIndex = column.getIndex();
	}

}
