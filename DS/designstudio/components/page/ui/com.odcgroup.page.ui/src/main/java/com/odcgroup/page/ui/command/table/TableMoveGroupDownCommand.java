package com.odcgroup.page.ui.command.table;

import com.odcgroup.page.model.widgets.table.ITableGroup;

/**
 * This command moves a column to the left in the table.
 * 
 * @author scn
 */
public class TableMoveGroupDownCommand extends AbstractTableGroupCommand {

	/*
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		getTableGroupingColumn().moveGroupDown(getGroupIndex());
	}

	/*
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		getTableGroupingColumn().moveGroupUp(getGroupIndex() - 1);
	}

	/**
	 * @param column
	 */
	public TableMoveGroupDownCommand(ITableGroup group) {
		super(group);
	}

}
