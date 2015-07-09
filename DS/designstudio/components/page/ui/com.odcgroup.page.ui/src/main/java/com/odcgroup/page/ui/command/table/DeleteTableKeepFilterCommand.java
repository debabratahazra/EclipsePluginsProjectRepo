package com.odcgroup.page.ui.command.table;

import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.model.widgets.table.ITableKeepFilter;

/**
 * This command deletes a tablesort in a table.
 *
 * @author pkk
 *
 */
public class DeleteTableKeepFilterCommand extends AbstractTableSortCommand {
	
	/** */
	private ITableKeepFilter keepFilter = null;

	/**
	 * @param table
	 * @param keepFilter 
	 */
	public DeleteTableKeepFilterCommand(ITable table, ITableKeepFilter keepFilter) {
		super(table);
		this.keepFilter = keepFilter;
	}
	
	/**
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		getTable().remove(keepFilter);
	}

	/**
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		getTable().add(keepFilter);
	}


}
