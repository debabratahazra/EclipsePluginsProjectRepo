package com.odcgroup.page.ui.command.grid;

import java.util.List;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.widgets.Grid;
import com.odcgroup.page.model.widgets.GridColumn;
import com.odcgroup.page.ui.command.BaseCommand;

/**
 * @author atr
 * @since DS 1.40.0
 */
public class GridDeleteColumnCommand extends BaseCommand {
	
	private Grid grid;
	private int columnIndex;
	private List<Widget> deletedCells;
	
	protected final int getColumnIndex() {
		return this.columnIndex;
	}

	/*
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		deletedCells = grid.removeColumn(getColumnIndex());
	}
	
	/*
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		grid.insertColumnAt(getColumnIndex(), deletedCells);
	}
	
	/**
	 * @param column
	 */
	public GridDeleteColumnCommand(GridColumn column) {
		this.grid = column.getGrid();
		this.columnIndex = column.getIndex();
	}

}
