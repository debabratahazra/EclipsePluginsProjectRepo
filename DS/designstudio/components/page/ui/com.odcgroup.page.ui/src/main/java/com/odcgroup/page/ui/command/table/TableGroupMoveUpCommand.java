package com.odcgroup.page.ui.command.table;

import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.model.widgets.table.ITableGroup;

/**
 *
 * @author pkk
 *
 */
public class TableGroupMoveUpCommand extends AbstractTableGroupCommand {
	
	/** */
	private ITableGroup selection;	
	/** */
	private ITableGroup oneOnTop;	
	/** */
	int rankSelection = -1;	
	/** */
	int rankTop = -1;
	/** */
	boolean hierarchy = false;

	/**
	 * @param table
	 * @param selection 
	 */
	public TableGroupMoveUpCommand(ITable table, ITableGroup selection) {
		super(table);
		this.selection = selection;
	}
	
	/**
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		oneOnTop = TableHelper.getGroupBeforeByRank(getTable(), selection);
		rankSelection = selection.getRank();
		rankTop = oneOnTop.getRank();
		if (rankTop == 1) {
			hierarchy = oneOnTop.isHierarchy();
			oneOnTop.setHierarchy(false);
		}
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
		if (hierarchy) {
			oneOnTop.setHierarchy(true);
		}
	}

}
