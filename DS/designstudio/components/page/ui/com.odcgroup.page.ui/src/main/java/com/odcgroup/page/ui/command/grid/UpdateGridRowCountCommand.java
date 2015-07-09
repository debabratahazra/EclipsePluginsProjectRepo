package com.odcgroup.page.ui.command.grid;

import java.util.List;

import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.widgets.Grid;
import com.odcgroup.page.model.widgets.impl.GridAdapter;
import com.odcgroup.page.ui.command.BaseCommand;

/**
 * Define a command to update a grid-row-count property.
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class UpdateGridRowCountCommand extends BaseCommand {

	/** The property to update*/
	private Grid grid;	
	
	/** The new value to set*/
	private int newRowCount;
	
	/** The old value of the property*/
	private int oldRowCount;
	
	/** the deleted rows */
	private List< List<Widget> > deletedRows;
	
	/**
	 * Constructor
	 * 
	 * @param property
	 * 				The property to udpate
	 * @param newRowCount
	 * 				The new value of the property
	 */
	public UpdateGridRowCountCommand(Property property, int newRowCount) {
		this.grid = new GridAdapter(property.getWidget());
		this.oldRowCount = grid.getColumnCount();
		this.newRowCount = newRowCount;
	}
	
	/**
	 * Executes the update command
	 *
	 */
	public void execute() {
		deletedRows = grid.setRowCount(newRowCount);
	}
	
	/**
	 * Undos the update command
	 */
	public void undo () {
		int delta = newRowCount - oldRowCount;
		if (delta < 0) {
			for (List<Widget> row : deletedRows) {
				grid.insertRowAt(grid.getRowCount(), row);
			}
		} else {
			grid.setRowCount(oldRowCount);
		}
	}
}
