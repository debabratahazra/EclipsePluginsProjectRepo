package com.odcgroup.page.ui.command.table;

import com.odcgroup.page.model.widgets.table.ITable;

/**
 * This is the base class for all command related to table sorts of a table.
 * 
 * @author pkk
 * @since DS 1.40.0
 *
 */
public class AbstractTableSortCommand extends AbstractTableCommand {
	
	/**
	 * Constructs the command with the specified table
	 * @param table the Table adapter for a table widget
	 */
	public AbstractTableSortCommand(ITable table) {
		super(table);
	}	

}
