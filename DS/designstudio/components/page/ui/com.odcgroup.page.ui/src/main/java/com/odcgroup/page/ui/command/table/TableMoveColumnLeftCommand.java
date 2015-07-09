package com.odcgroup.page.ui.command.table;

import com.odcgroup.page.model.widgets.table.ITableColumn;

/**
 * This command moves a column to the left in the table.
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class TableMoveColumnLeftCommand extends AbstractTableColumnCommand {

	/*
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		getTable().moveColumnLeft(getColumnIndex());
	}

	/*
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		getTable().moveColumnRight(getColumnIndex() - 1);
	}

	/**
	 * @param column
	 */
	public TableMoveColumnLeftCommand(ITableColumn column) {
		super(column);
	}

}
