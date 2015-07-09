package com.odcgroup.page.ui.command.table;

import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.model.widgets.table.ITableColumn;

/**
 * This command inserts a new column in the table.
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class TableInsertColumnCommand extends AbstractTableColumnCommand {
	/** */
	private String type;

	/**
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		int index = -1;
		if (type != null) {
			ITableColumn newColumn = TableHelper.getTableUtilities().getFactory().createTableColumn(type);
			index = getTable().insertColumn(newColumn);
		} else {
		  index = getTable().insertColumn();
		}
		setColumnIndex(index);
	}

	/**
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		getTable().removeColumn(getColumnIndex());
	}

	/**
	 * @param type 
	 * @param table
	 */
	public TableInsertColumnCommand(String type, ITable table) {
		super(table);
		this.type = type;
	}

}
