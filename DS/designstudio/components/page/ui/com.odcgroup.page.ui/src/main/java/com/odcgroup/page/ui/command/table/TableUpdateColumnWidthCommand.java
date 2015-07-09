package com.odcgroup.page.ui.command.table;

import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.util.TableHelper;

/**
 * Define a command to update the property table-column-width of a column in a
 * table.
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class TableUpdateColumnWidthCommand extends AbstractTableColumnCommand {

	/** The new value to set */
	private int newWidth;

	/** The old value of the property */
	private int oldWidth;

	/**
	 * Constructor
	 * 
	 * @param property
	 *            The property to udpate
	 * @param newWidth
	 *            The new value of the property
	 */
	public TableUpdateColumnWidthCommand(Property property, int newWidth) {
		super(TableHelper.getTableColumn(property.getWidget()));
		this.oldWidth = getTable().getColumnWidth(getColumnIndex());
		this.newWidth = newWidth;
	}

	/**
	 * Executes the update command
	 */
	public void execute() {
		getTable().setColumnWidth(getColumnIndex(), newWidth);
	}

	/**
	 * Undo the update command
	 */
	public void undo() {
		getTable().setColumnWidth(getColumnIndex(), oldWidth);
	}
}
