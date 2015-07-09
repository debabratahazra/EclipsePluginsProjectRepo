package com.odcgroup.page.ui.command.table;

import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.model.widgets.table.ITableExtra;

/**
 * This command deletes a tablesort in a table.
 *
 * @author pkk
 *
 */
public class DeleteTableExtraCommand extends AbstractTableSortCommand {
	
	/** */
	private ITableExtra tableExtra = null;

	/**
	 * @param table
	 * @param tableExtra 
	 */
	public DeleteTableExtraCommand(ITable table, ITableExtra tableExtra) {
		super(table);
		this.tableExtra = tableExtra;
	}
	
	/**
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		getTable().remove(tableExtra);
	}

	/**
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		getTable().add(tableExtra);
	}


}
