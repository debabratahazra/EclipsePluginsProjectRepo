package com.odcgroup.page.ui.command.table;

import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.model.widgets.table.ITableColumn;

/**
 * This is the base class for all command related to column of a table.
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class AbstractTableColumnCommand extends AbstractTableCommand {

	/** the index of the column, the value (-1) means undefined */
	private int columnIndex = -1;

	/**
	 * @return the columnIndex
	 */
	protected final int getColumnIndex() {
		return columnIndex;
	}

	/**
	 * @param columnIndex
	 *            the columnIndex to set
	 */
	protected final void setColumnIndex(int columnIndex) {
		this.columnIndex = columnIndex;
	}
	
	/**
	 * @return the column
	 */
	protected final ITableColumn getTableColumn() {
		return getTable().getColumn(getColumnIndex());
	}

	/**
	 * Constructs the command with the specified table and column
	 * @param column the Table Column adapter for a column widget
	 */
	public AbstractTableColumnCommand(ITableColumn column) {
		super(column.getTable());
		this.columnIndex = column.getColumnIndex();
	}
	
	/**
	 * Constructs the command with the specified table
	 * @param table the Table adapter for a table widget
	 */
	public AbstractTableColumnCommand(ITable table) {
		super(table);
	}	

}
