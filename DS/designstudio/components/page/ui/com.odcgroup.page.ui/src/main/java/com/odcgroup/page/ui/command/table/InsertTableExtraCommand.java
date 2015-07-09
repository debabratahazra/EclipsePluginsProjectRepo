package com.odcgroup.page.ui.command.table;

import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.model.widgets.table.ITableExtra;

/**
 * This command inserts a new tableExtra in the table.
 * 
 * @author pkk
 * @since DS 1.40.0
 *
 */
public class InsertTableExtraCommand extends AbstractTableSortCommand {
		
	/** tableSort which is inserted */
	private ITableExtra tableExtra;

	/**
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		getTable().add(tableExtra);
	}
	
	/**
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		if (tableExtra != null) {
			getTable().remove(tableExtra);
		}
	}
	
	/**
	 * @param table
	 * @param tableExtra 
	 */
	public InsertTableExtraCommand(ITable table, ITableExtra tableExtra) {
		super(table);
		this.tableExtra = tableExtra;
	}

}
