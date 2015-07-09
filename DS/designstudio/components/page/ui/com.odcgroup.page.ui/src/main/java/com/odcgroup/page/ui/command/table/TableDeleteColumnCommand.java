package com.odcgroup.page.ui.command.table;

import java.util.List;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.widgets.table.ITableAggregate;
import com.odcgroup.page.model.widgets.table.ITableColumn;

/**
 * This command deletes a column in a table.
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class TableDeleteColumnCommand extends AbstractTableColumnCommand {

	/** the deleted widget */ 
	private Widget deletedColumn;
	/** the column to delete */ 
	private ITableColumn column;
	/** the corresponding table-aggregate */ 
	private ITableAggregate deletedAggregate = null;

	/**
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		int deletedColumnIndex = column.getColumnIndex();
		setColumnIndex(deletedColumnIndex);
		deletedColumn = getTable().removeColumn(deletedColumnIndex);
		if (!column.isBoundToDomain()) {
			List<ITableAggregate> aggregates = getTable().getAggregatedColumns();
			for (ITableAggregate aggregate : aggregates) {
				if (aggregate.getColumnName().equals(column.getColumnName())) {
					this.deletedAggregate = aggregate;
					break;
				}
			}
			if (deletedAggregate != null) {
				getTable().remove(deletedAggregate);
			}
		}
	}

	/**
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		getTable().insertColumnAt(getColumnIndex(), deletedColumn);
		if (deletedAggregate != null) {
			getTable().add(deletedAggregate);
		}
	}

	/**
	 * @param column
	 */
	public TableDeleteColumnCommand(ITableColumn column) {
		super(column);
		this.column = column;
		setLabel("Delete table column");
	}
	
	/**
	 * @param parent
	 * @param child
	 */
	public TableDeleteColumnCommand(Widget parent, Widget child) {
		this(TableHelper.getTableColumn(child));
		this.column = TableHelper.getTableColumn(child);
	}	

}
