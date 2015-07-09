package com.odcgroup.page.ui.command.table;

import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.model.widgets.table.ITableSort;

/**
 * TODO: Document me!
 *
 * @author pkk
 *
 */
public class UpdateTableSortCommand extends AbstractTableSortCommand {
	
	/** table sort */
	private ITableSort tableSort;
	/** new value for sort direction*/
	private String newdirection;
	/** old value for sort direction*/
	private String olddirection;

	/**
	 * @param table
	 * @param tableSort 
	 * @param newdirection 
	 */
	public UpdateTableSortCommand(ITable table, ITableSort tableSort, String newdirection) {
		super(table);
		this.tableSort = tableSort;
		this.newdirection = newdirection;
	}
	
	/**
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		for (ITableSort sort : getTable().getSorts()) {
			if (sort.getColumnName().equals(tableSort.getColumnName())) {		
				olddirection = sort.getSortingDirection();
				sort.setSortingDirection(newdirection);
			}
		}
	}
	
	/**
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		for (ITableSort sort : getTable().getSorts()) {
			if (sort.getColumnName().equals(tableSort.getColumnName())) {	
				sort.setSortingDirection(olddirection);
			}
		}
	}

}
