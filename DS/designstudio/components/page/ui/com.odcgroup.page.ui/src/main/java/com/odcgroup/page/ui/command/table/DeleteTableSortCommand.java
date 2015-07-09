package com.odcgroup.page.ui.command.table;

import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.model.widgets.table.ITableSort;

/**
 * This command deletes a tablesort in a table.
 *
 * @author pkk
 *
 */
public class DeleteTableSortCommand extends AbstractTableSortCommand {
	
	/** */
	private ITableSort tableSort = null;

	/**
	 * @param table
	 * @param tableSort 
	 */
	public DeleteTableSortCommand(ITable table, ITableSort tableSort) {
		super(table);
		this.tableSort = tableSort;
	}
	
	/**
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		getTable().remove(tableSort);
	}

	/**
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		getTable().add(tableSort);
	}


}
