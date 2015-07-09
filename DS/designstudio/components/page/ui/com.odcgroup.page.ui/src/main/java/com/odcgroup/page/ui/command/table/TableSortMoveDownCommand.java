package com.odcgroup.page.ui.command.table;

import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.model.widgets.table.ITableSort;

/**
 * TODO: Document me!
 *
 * @author pkk
 *
 */
public class TableSortMoveDownCommand extends AbstractTableSortCommand {
	
	/** */
	private ITableSort selection;	
	/** */
	private ITableSort oneOnTop;	
	/** */
	int rankSelection = -1;	
	/** */
	int rankTop = -1;

	/**
	 * @param table
	 * @param selection 
	 */
	public TableSortMoveDownCommand(ITable table, ITableSort selection) {
		super(table);
		this.selection = selection;
	}
	
	/**
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		oneOnTop = TableHelper.getSortAfterByRank(getTable(), selection);
		rankSelection = selection.getRank();
		rankTop = oneOnTop.getRank();
		selection.setRank(rankTop);
		oneOnTop.setRank(rankSelection);
	}
	
	/**
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		selection.setRank(rankSelection);
		oneOnTop.setRank(rankTop);
	}

}
