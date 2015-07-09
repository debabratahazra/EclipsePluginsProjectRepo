package com.odcgroup.page.ui.command.table;

import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.model.widgets.table.ITableGroup;

/**
 *
 * @author pkk
 *
 */
public class TableGroupMoveDownCommand extends AbstractTableGroupCommand {
	
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
	public TableGroupMoveDownCommand(ITable table, ITableGroup selection) {
		super(table);
		this.selection = selection;
	}
	
	/**
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		oneOnTop = TableHelper.getGroupAfterByRank(getTable(), selection);
		rankSelection = selection.getRank();
		if (rankSelection == 1) {
			hierarchy = selection.isHierarchy();
			selection.setHierarchy(false);
		}
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
		if (hierarchy) {
			selection.setHierarchy(true);
		}
		oneOnTop.setRank(rankTop);
		
	}

}
