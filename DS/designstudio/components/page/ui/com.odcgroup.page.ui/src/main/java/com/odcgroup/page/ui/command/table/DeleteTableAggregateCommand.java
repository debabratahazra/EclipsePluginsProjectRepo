package com.odcgroup.page.ui.command.table;

import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.model.widgets.table.ITableAggregate;

/**
 * TODO: Document me!
 *
 * @author pkk
 *
 */
public class DeleteTableAggregateCommand extends AbstractTableAggregateCommand {
	
	/** */
	private ITableAggregate tableaggr = null;

	/**
	 * @param table
	 * @param tableaggr 
	 */
	public DeleteTableAggregateCommand(ITable table, ITableAggregate tableaggr) {
		super(table);
		this.tableaggr = tableaggr;
	}
	
	/**
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		getTable().remove(tableaggr);
	}

	/**
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		getTable().add(tableaggr);
	}
}
