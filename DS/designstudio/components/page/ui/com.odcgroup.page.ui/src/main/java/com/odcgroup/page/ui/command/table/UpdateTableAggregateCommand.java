package com.odcgroup.page.ui.command.table;

import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.model.widgets.table.ITableAggregate;

/**
 * TODO: Document me!
 *
 * @author pkk
 *
 */
public class UpdateTableAggregateCommand extends AbstractTableAggregateCommand {
	/** */
	private ITableAggregate oldValue;
	/** */
	private ITableAggregate newValue;

	/**
	 * @param table
	 * @param newValue 
	 */
	public UpdateTableAggregateCommand(ITable table, ITableAggregate newValue) {
		super(table);
		this.oldValue = getOldValue(table, newValue);
		this.newValue = newValue;
	}
	
	/**
	 * @param table
	 * @param newValue
	 * @return oldValue
	 */
	private ITableAggregate getOldValue(ITable table, ITableAggregate newValue) {
		for (ITableAggregate aggregate : table.getAggregatedColumns()) {
			if (aggregate.getColumnName().equals(newValue.getColumnName())) {
				return aggregate;
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
