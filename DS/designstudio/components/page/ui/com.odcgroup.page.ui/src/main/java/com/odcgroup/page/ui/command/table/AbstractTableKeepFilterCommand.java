package com.odcgroup.page.ui.command.table;

import com.odcgroup.page.model.widgets.table.ITable;

/**
 * This is the base class for all command related to table sorts of a table.
 * 
 * @author pkk
 * @since DS 1.40.0
 *
 */
public class AbstractTableKeepFilterCommand extends AbstractTableCommand {
	
	/** the rank of the tablekeepfilter, the value (-1) means undefined */
	private int sortRank = -1;

	/**
	 * @return the sortRank
	 */
	protected final int getSortRank() {
		return sortRank;
	}

	/**
	 * @param sortRank
	 *            the sortIndex to set
	 */
	protected final void setSortIndex(int sortRank) {
		this.sortRank = sortRank;
	}	
	
	/**
	 * Constructs the command with the specified table
	 * @param table the Table adapter for a table widget
	 */
	public AbstractTableKeepFilterCommand(ITable table) {
		super(table);
	}	

}
