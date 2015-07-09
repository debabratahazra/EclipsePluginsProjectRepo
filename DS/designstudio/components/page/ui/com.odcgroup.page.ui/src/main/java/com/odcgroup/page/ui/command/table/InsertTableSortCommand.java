package com.odcgroup.page.ui.command.table;

import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.model.widgets.table.ITableSort;

/**
 * This command inserts a new tablesort in the table.
 * 
 * @author pkk
 * @since DS 1.40.0
 *
 */
public class InsertTableSortCommand extends AbstractTableSortCommand {
		
	/** tableSort which is inserted */
	private ITableSort tableSort;

	/**
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		getTable().add(tableSort);
	}
	
	/**
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		if (tableSort != null) {
			getTable().remove(tableSort);
		}
	}
	
	/**
	 * @param table
	 * @param tableSort 
	 */
	public InsertTableSortCommand(ITable table, ITableSort tableSort) {
		super(table);
		this.tableSort = tableSort;
	}

}
