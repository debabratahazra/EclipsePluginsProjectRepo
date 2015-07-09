package com.odcgroup.page.ui.command.table;

import java.util.List;

import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.TableHelper;

/**
 * Define a command to update the property table-column-count.
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class TableUpdateColumnCountCommand extends AbstractTableCommand {

	/** The new value to set */
	private int newColumnCount;

	/** The old value of the property */
	private int oldColumnCount;

	/** the deleted columns */
	private List<Widget> deletedColumns;

	/**
	 * Constructor
	 * 
	 * @param property
	 *            The property to be modified
	 * @param newColumnCount
	 *            The new value of the property
	 */
	public TableUpdateColumnCountCommand(Property property, int newColumnCount) {
		super(TableHelper.getTable(property.getWidget()));
		this.oldColumnCount = getTable().getColumnCount();
		this.newColumnCount = newColumnCount;
	}

	/**
	 * Executes the update command
	 * 
	 */
	public void execute() {
		deletedColumns = getTable().setColumnCount(newColumnCount);
	}

	/**
	 * Undo the update command
	 */
	public void undo() {
		int delta = newColumnCount - oldColumnCount;
		if (delta < 0) {
			for (Widget column : deletedColumns) {
				getTable().insertColumnAt(getTable().getColumnCount(), column);
			}
		} else {
			getTable().setColumnCount(oldColumnCount);
		}
	}
}
