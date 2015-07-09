package com.odcgroup.page.ui.command.grid;

import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.widgets.Grid;
import com.odcgroup.page.model.widgets.GridCell;
import com.odcgroup.page.model.widgets.impl.GridCellAdapter;
import com.odcgroup.page.ui.command.BaseCommand;

/**
 * Define a command to update a a grid-css-class property.
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class UpdateGridColumnCssClassCommand extends BaseCommand {

	/** The property to update*/
	private Grid grid;	
	
	private int columnIndex;
	
	/** The new value to set*/
	private String oldValue;
	
	/** The old value of the property*/
	private String newValue;
	
	/**
	 * Constructor
	 * 
	 * @param property
	 * 				The property to udpate
	 * @param newValue
	 * 				The new value of the property
	 */
	public UpdateGridColumnCssClassCommand(Property property, String newValue) {
		GridCell cell = new GridCellAdapter(property.getWidget());
		this.columnIndex = cell.getColumnIndex();
		this.grid = cell.getGrid();
		this.oldValue = grid.getColumnCssClass(columnIndex);
		this.newValue = newValue;
	}
	
	/**
	 * Executes the update command
	 *
	 */
	public void execute() {
		grid.setColumnCssClass(columnIndex, newValue);
	}
	
	/**
	 * Undos the update command
	 */
	public void undo () {
		grid.setColumnCssClass(columnIndex, oldValue);
	}
}
