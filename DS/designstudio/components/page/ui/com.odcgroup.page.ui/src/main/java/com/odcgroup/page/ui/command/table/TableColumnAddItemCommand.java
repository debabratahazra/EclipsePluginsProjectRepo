package com.odcgroup.page.ui.command.table;

import com.odcgroup.page.model.widgets.table.ITableColumn;
import com.odcgroup.page.model.widgets.table.ITableColumnItem;

/**
 * This command add an item within the column
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class TableColumnAddItemCommand extends AbstractTableColumnCommand {
	
	/** */
	private ITableColumnItem item;

	/**
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		item = getTableColumn().addItem();
	}

	/**
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		getTableColumn().removeItem(item);
	}

	/**
	 * @param column
	 */
	public TableColumnAddItemCommand(ITableColumn column) {
		super(column);
	}

}
