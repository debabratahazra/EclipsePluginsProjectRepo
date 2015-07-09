package com.odcgroup.page.ui.command.table;

import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.widgets.table.ITableColumn;

/**
 * This command inserts a new column to the left of the selected column in the
 * table.
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class TableInsertColumnLeftCommand extends AbstractTableColumnCommand {
	
	/** */
	private ITableColumn newColumn;

	/**
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		if (newColumn == null) {
			return;
		}
		getTable().insertColumnLeft(newColumn, getColumnIndex());		
	}

	/**
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		getTable().removeColumn(getColumnIndex());
	}

	/**
	 * @param column
	 * @param type 
	 */
	public TableInsertColumnLeftCommand(ITableColumn column, String type) {
		super(column);
		createColumn(type);
	}
	
	/**
	 * @param type
	 */
	private void createColumn(String type) {
		newColumn = TableHelper.getTableUtilities().getFactory().createTableColumn(type);
	}

}
