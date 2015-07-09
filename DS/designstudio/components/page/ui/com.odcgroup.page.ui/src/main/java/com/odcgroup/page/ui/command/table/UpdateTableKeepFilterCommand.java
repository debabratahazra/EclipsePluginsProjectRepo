package com.odcgroup.page.ui.command.table;

import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.model.widgets.table.ITableKeepFilter;

/**
 * TODO: Document me!
 *
 * @author pkk
 *
 */
public class UpdateTableKeepFilterCommand extends AbstractTableSortCommand {
	
	/** new value */
	private ITableKeepFilter newValue;
	/** old value */
	private ITableKeepFilter oldValue;

	/**
	 * @param table
	 * @param newValue 
	 */
	public UpdateTableKeepFilterCommand(ITable table, ITableKeepFilter newValue, ITableKeepFilter oldValue) {
		super(table);
		this.oldValue = getOldValue(table, newValue, oldValue);
		this.newValue = newValue;
	}
	
	/**
	 * @param table
	 * @param newValue
	 * @return oldValue
	 */
	private ITableKeepFilter getOldValue(ITable table, ITableKeepFilter newValue, ITableKeepFilter oldValue) {
		for (ITableKeepFilter group : table.getKeepFilters()) {
			if (group.getColumnName().equals(newValue.getColumnName())
					&& group.getOperand().equals(oldValue.getOperand()) 
					&& group.getOperator().equals(oldValue.getOperator())) {
				return group;
			}
		}
		return null;
	}
	
	/**
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		getTable().remove(oldValue);
		getTable().add(newValue);
	}
	
	/**
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		getTable().remove(newValue);
		getTable().add(oldValue);
	}

}
