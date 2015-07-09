package com.odcgroup.page.ui.command.grid;

import com.odcgroup.page.model.widgets.Grid;
import com.odcgroup.page.model.widgets.GridCell;
import com.odcgroup.page.ui.command.BaseCommand;

/**
 * @author atr
 * @since DS 1.40.0
 */
public class GridMoveCellRightCommand extends BaseCommand {
	
	private Grid grid;
	private int rowIndex;
	private int columnIndex;

	protected final Grid getGrid() {
		return this.grid;
	}

	protected final int getRowIndex() {
		return this.rowIndex;
	}

	private final int getColumnIndex() {
		return this.columnIndex;
	}

	/*
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		getGrid().moveCellRight(getRowIndex(), getColumnIndex());
	}
	
	/*
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		getGrid().moveCellLeft(getRowIndex(), getColumnIndex()+1);
	}
	
	/**
	 * @param cell
	 */
	public GridMoveCellRightCommand(GridCell cell) {
		this.grid = cell.getGrid();
		this.rowIndex = cell.getRowIndex();
		this.columnIndex = cell.getColumnIndex();
	}

}
