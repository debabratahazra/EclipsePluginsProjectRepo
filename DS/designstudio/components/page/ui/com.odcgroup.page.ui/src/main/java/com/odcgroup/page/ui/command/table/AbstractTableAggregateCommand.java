package com.odcgroup.page.ui.command.table;

import com.odcgroup.page.model.widgets.table.ITable;

/**
 * TODO: Document me!
 *
 * @author pkk
 *
 */
public class AbstractTableAggregateCommand extends AbstractTableCommand {

	
	/**
	 * Constructs the command with the specified table
	 * @param table the Table adapter for a table widget
	 */
	public AbstractTableAggregateCommand(ITable table) {
		super(table);
	}	

}
