package com.odcgroup.page.ui.command;

import org.eclipse.core.runtime.Assert;

import com.odcgroup.page.model.Widget;

/**
 * A command to remove a Column from a table.
 * 
 * @author Alexandre Jaquet
 *
 */
public class DeleteColumnCommand extends BaseCommand {
	
	/** Child Widget. */
	private Widget child;

	/** Widget to remove from. */	
	private Widget parent;
	
	/** Widget to remove. */
	private Widget column;
	
	/** The index of the deleted Widget. */
	private int oldIndex;

	/**
	 * Create a command that will remove the Column from its parent.
	 * 
	 * @param parent The Widget containing the child
	 * @param child The Widget to remove
	 */
	public DeleteColumnCommand(Widget parent, Widget child) {
		Assert.isNotNull(parent);
		Assert.isNotNull(child);
		// Retrieve the Table widget and set it as the parent
		this.parent = parent.getParent();
		this.child = child;
	
		setLabel("Delete "+child.getTypeName()+" widget");
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
		// This Command is only active for the ColumnHeader and ColumnBody. We need to delete
		// The Column (the parent)
		column = child.getParent();
		oldIndex = parent.getContents().indexOf(column);
		parent.getContents().remove(column);
		
		
		if (parent.getContents().size() > 1) {
		    parent.setIndexOfSelectedChild(0);
		}
	}

	/**
	 * Undoes the deletion.
	 */
	public void undo() {
		parent.getContents().add(oldIndex, column);
		parent.setIndexOfSelectedChild(oldIndex);
	}
}