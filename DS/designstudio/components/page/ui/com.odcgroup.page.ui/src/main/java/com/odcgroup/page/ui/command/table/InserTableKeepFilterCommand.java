package com.odcgroup.page.ui.command.table;

import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.model.widgets.table.ITableKeepFilter;

/**
 * TODO: Document me!
 *
 * @author pkk
 *
 */
public class InserTableKeepFilterCommand extends AbstractTableKeepFilterCommand {
		
	/** keepFilter which is inserted */
	private ITableKeepFilter keepFilter;

	/**
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		getTable().add(keepFilter);
	}
	
	/**
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		if (keepFilter != null) {
			getTable().remove(keepFilter);
		}
	}
	
	/**
	 * @param table
	 * @param keepFilter 
	 */
	public InserTableKeepFilterCommand(ITable table, ITableKeepFilter keepFilter) {
		super(table);
		this.keepFilter = keepFilter;
	}



}
