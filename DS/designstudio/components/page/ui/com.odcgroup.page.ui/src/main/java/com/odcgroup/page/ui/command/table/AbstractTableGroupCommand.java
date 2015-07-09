package com.odcgroup.page.ui.command.table;

import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.model.widgets.table.ITableGroup;

/**
 * @author scn
 */
public class AbstractTableGroupCommand extends AbstractTableCommand {

	/** the index of the column, the value (-1) means undefined */
	private int groupRank = -1;

	/**
	 * @return the columnIndex
	 */
	protected final int getGroupIndex() {
		return groupRank;
	}

	/**
	 * @param columnIndex
	 *            the columnIndex to set
	 */
	protected final void setGroupRank(int groupRank) {
		this.groupRank = groupRank;
	}
	
	/**
	 * @return the column
	 */
	protected final ITableGroup getTableGroup() {
		return getTableGroupingColumn().getGroup(getGroupIndex());
	}

	/**
	 * Constructs the command with the specified table and column
	 * @param column the Table Column adapter for a column widget
	 */
	public AbstractTableGroupCommand(ITableGroup group) {
		super(group.getTable(group));
		this.groupRank = group.getRank();
	}
	
	/**
	 * Constructs the command with the specified table
	 * @param table the Table adapter for a table widget
	 */
	public AbstractTableGroupCommand(ITable table) {
		super(table);
	}	

}
