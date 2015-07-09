package com.odcgroup.page.ui.command.grid;

import java.util.List;

import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.widgets.Grid;
import com.odcgroup.page.model.widgets.impl.GridAdapter;
import com.odcgroup.page.ui.command.BaseCommand;

/**
 * Define a command to update a grid-column-count property.
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class UpdateGridColumnCountCommand extends BaseCommand {

	/** The property to update*/
	private Grid grid;	
	
	/** The new value to set*/
	private int newColumnCount;
	
	/** The old value of the property*/
	private int oldColumnCount;
	
	/** the deleted columns */
	private List< List<Widget> > deletedColumns;
	
	/**
	 * Constructor
	 * 
	 * @param property
	 * 				The property to udpate
	 * @param newColumnCount
	 * 				The new value of the property
	 */
	public UpdateGridColumnCountCommand(Property property, int newColumnCount) {
		this.grid = new GridAdapter(property.getWidget());
		this.oldColumnCount = grid.getColumnCount();
		this.newColumnCount = newColumnCount;
	}
	
	/**
	 * Executes the update command
	 *
	 */
	public void execute() {
		deletedColumns = grid.setColumnCount(newColumnCount);
	}
	
	/**
	 * Undos the update command
	 */
	public void undo () {
		int delta = newColumnCount - oldColumnCount;
		if (delta < 0) {
			for (List<Widget> column : deletedColumns) {
				grid.insertColumnAt(grid.getColumnCount(), column);
			}
		} else {
			grid.setColumnCount(oldColumnCount);
		}
	}
}
