package com.odcgroup.page.ui.command.table;

import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.model.widgets.table.ITableAggregate;

/**
 * TODO: Document me!
 *
 * @author pkk
 *
 */
public class InsertTableAggregateCommand extends AbstractTableAggregateCommand {
		
	/** tableAggregate which is inserted */
	private ITableAggregate tableAggregate;

	/**
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		getTable().add(tableAggregate);
	}
	
	/**
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		if (tableAggregate != null) {
			getTable().remove(tableAggregate);
		}
	}
	
	/**
	 * @param table
	 * @param tableAggregate 
	 */
	public InsertTableAggregateCommand(ITable table, ITableAggregate tableAggregate) {
		super(table);
		this.tableAggregate = tableAggregate;
	}



}
