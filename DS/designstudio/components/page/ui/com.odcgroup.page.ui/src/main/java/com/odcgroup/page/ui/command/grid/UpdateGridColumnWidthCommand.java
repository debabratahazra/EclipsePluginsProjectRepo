package com.odcgroup.page.ui.command.grid;

import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.widgets.Grid;
import com.odcgroup.page.model.widgets.GridCell;
import com.odcgroup.page.model.widgets.impl.GridCellAdapter;
import com.odcgroup.page.ui.command.BaseCommand;

/**
 * Define a command to update a grid-column-width property.
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class UpdateGridColumnWidthCommand extends BaseCommand {

	/** The property to update*/
	private Grid grid;	
	
	private int columnIndex;
	
	/** The new value to set*/
	private int newWidth;
	
	/** The old value of the property*/
	private int oldWidth;
	
	/**
	 * Constructor
	 * 
	 * @param property
	 * 				The property to udpate
	 * @param newWidth
	 * 				The new value of the property
	 */
	public UpdateGridColumnWidthCommand(Property property, int newWidth) {
		GridCell cell = new GridCellAdapter(property.getWidget());
		this.columnIndex = cell.getColumnIndex();
		this.grid = cell.getGrid();
		this.oldWidth = grid.getColumnWidth(columnIndex);
		this.newWidth = newWidth;
	}
	
	/**
	 * Executes the update command
	 *
	 */
	public void execute() {
		grid.setColumnWidth(columnIndex, newWidth);
	}
	
	/**
	 * Undos the update command
	 */
	public void undo () {
		grid.setColumnWidth(columnIndex, oldWidth);
	}
}
