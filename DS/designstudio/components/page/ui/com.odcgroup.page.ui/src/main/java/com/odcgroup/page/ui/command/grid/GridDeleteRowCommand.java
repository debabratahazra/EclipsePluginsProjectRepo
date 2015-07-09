package com.odcgroup.page.ui.command.grid;

import java.util.List;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.widgets.Grid;
import com.odcgroup.page.model.widgets.GridRow;
import com.odcgroup.page.ui.command.BaseCommand;

/**
 * @author atr
 * @since DS 1.40.0
 */
public class GridDeleteRowCommand extends BaseCommand {
	
	private Grid grid;
	private int rowIndex;
	private List<Widget> deletedCells;
	
	/*
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		deletedCells = grid.removeRow(rowIndex);
	}
	
	/*
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		grid.insertRowAt(rowIndex, deletedCells);
	}
	
	/**
	 * @param row
	 */
	public GridDeleteRowCommand(GridRow row) {
		this.grid = row.getGrid();
		this.rowIndex = row.getIndex();
	}

}
