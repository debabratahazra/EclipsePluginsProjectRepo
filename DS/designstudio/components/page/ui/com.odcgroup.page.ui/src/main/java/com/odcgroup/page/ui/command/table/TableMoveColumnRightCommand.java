package com.odcgroup.page.ui.command.table;

import com.odcgroup.page.model.widgets.table.ITableColumn;

/**
 * This command moves a column to the right in the table.
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class TableMoveColumnRightCommand extends AbstractTableColumnCommand {

	/*
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		getTable().moveColumnRight(getColumnIndex());
	}

	/*
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		getTable().moveColumnLeft(getColumnIndex() + 1);
	}

	/**
	 * @param column
	 */
	public TableMoveColumnRightCommand(ITableColumn column) {
		super(column);
	}

}
