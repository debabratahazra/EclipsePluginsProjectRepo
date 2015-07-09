package com.odcgroup.page.ui.command.table;

import com.odcgroup.page.model.widgets.table.ITableColumn;
import com.odcgroup.page.model.widgets.table.ITableGroup;

/**
 * This command add a group within the column
 * 
 */
public class TableColumnAddGroupCommand extends AbstractTableColumnCommand {
	
	/** */
	private ITableGroup group;

	/**
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		group = getTableColumn().addGroup();
		if (!getTableColumn().isDisplayGrouping()) {
			getTableColumn().getWidget().setPropertyValue("column-display-grouping", "true");
		}
	}

	/**
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		getTableColumn().removeGroup(group);
		if (getTableColumn().getGroups().isEmpty()) {
			getTableColumn().getWidget().setPropertyValue("column-display-grouping", "false");			
		}
	}

	/**
	 * @param column
	 */
	public TableColumnAddGroupCommand(ITableColumn column) {
		super(column);
	}

}
