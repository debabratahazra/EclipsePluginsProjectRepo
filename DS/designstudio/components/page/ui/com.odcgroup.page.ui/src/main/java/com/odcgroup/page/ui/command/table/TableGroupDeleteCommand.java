package com.odcgroup.page.ui.command.table;

import org.eclipse.core.runtime.Assert;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.widgets.table.ITableColumn;
import com.odcgroup.page.model.widgets.table.ITableGroup;
import com.odcgroup.page.ui.command.BaseCommand;

/**
 * @author phanikumark
 *
 */
public class TableGroupDeleteCommand extends BaseCommand {

	/** Widget to remove. */
	private ITableGroup group;

	/** Widget to remove from. */	
	private ITableColumn column;
	
	/** The index of the deleted Widget. */
	private int index;

	/**
	 * Create a command that will remove the tablegroup from its parent.
	 * @param parent
	 * @param child  
	 * @throws IllegalArgumentException 
	 */
	public TableGroupDeleteCommand(Widget parent, Widget child) {
		Assert.isNotNull(parent);
		Assert.isNotNull(child);
		if (parent.getTypeName().equals("TableColumn") 
				&& child.getTypeName().equals("TableGroup")) {
			this.column = TableHelper.getTableColumn(parent);
			this.group = TableHelper.getTableGroup(child);
		
			setLabel("Delete "+child.getTypeName()+" widget");
		}
	}

	/**
	 * Returns true if the Command can be undone.
	 * 
	 * @return boolean True since this command can be undone
	 */
	public boolean canUndo() {
		return true;
	}

	/**
	 * Executes the command. This method delegates to redo.
	 */
	public void execute() {
		column.removeGroup(group);		
		if (column.getTable().getGroups().isEmpty()) {
			column.getWidget().setPropertyValue("column-display-grouping", "false");
		}
	}

	/**
	 * Undoes the deletion.
	 */
	public void undo() {
		column.getWidget().getContents().add(index, group.getWidget());
		if (!column.getTable().getGroups().isEmpty()) {
			column.getWidget().setPropertyValue("column-display-grouping", "true");			
		}
	}

}
