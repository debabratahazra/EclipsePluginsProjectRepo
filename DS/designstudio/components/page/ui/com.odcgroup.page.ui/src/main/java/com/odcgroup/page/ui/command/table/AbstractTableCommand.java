package com.odcgroup.page.ui.command.table;

import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.model.widgets.table.ITableColumn;
import com.odcgroup.page.ui.command.BaseCommand;

/**
 * This is the base class for all Table/Tree command
 *
 * @author atr
 * @since DS 1.40.0
 */
public class AbstractTableCommand extends BaseCommand {

	/** the table adapter */
	private ITable table = null;
	
	/**
	 * @return the table
	 */
	protected final ITable getTable() {
		return table;
	}
	
	/**
	 * @return the table
	 */
	protected final ITableColumn getTableGroupingColumn() {
		return table.getDisplayGroupingColumn();
	}

	/**
	 * Constructs the command with the designated table
	 * @param the table adapter, cannot be {@code null}
	 */
	public AbstractTableCommand(ITable table) {
		this.table = table;
		
	}

}
