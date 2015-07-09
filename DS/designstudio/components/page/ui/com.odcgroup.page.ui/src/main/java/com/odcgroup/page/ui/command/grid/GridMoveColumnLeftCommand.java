package com.odcgroup.page.ui.command.grid;

import com.odcgroup.page.model.widgets.Grid;
import com.odcgroup.page.model.widgets.GridColumn;
import com.odcgroup.page.ui.command.BaseCommand;

/**
 * @author atr
 * @since DS 1.40.0
 */
public class GridMoveColumnLeftCommand extends BaseCommand {
	
	private Grid grid;
	private int columnIndex;
	
	protected final int getColumnIndex() {
		return this.columnIndex;
	}

	/*
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		grid.moveColumnLeft(getColumnIndex());
	}
	
	/*
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		grid.moveColumnRight(getColumnIndex()-1);
	}
	
	/**
	 * @param column
	 */
	public GridMoveColumnLeftCommand(GridColumn column) {
		this.grid = column.getGrid();
		this.columnIndex = column.getIndex();
	}

}
