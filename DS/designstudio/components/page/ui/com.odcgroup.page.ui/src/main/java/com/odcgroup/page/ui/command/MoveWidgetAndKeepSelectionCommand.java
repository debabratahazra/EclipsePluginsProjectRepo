package com.odcgroup.page.ui.command;

import com.odcgroup.page.model.Widget;

/**
 * Command executed when a child Widget is moved within its container.
 * 
 * @author Alain Tripod
 */
public class MoveWidgetAndKeepSelectionCommand extends MoveWidgetCommand {

	/** The parent. */
	private Widget parent;
	
	/** The child. */
	private Widget child;
	
	/**
	 * Creates a new MoveChildWidgetCommand.
	 * 
	 * @param name The name of the Command
	 * @param child The child Widget
	 * @param parent The parent Widget
	 * @param newIndex The new index of the child Widget
	 */
	public MoveWidgetAndKeepSelectionCommand(String name, Widget parent, Widget child, int newIndex) {
		super(parent, child, newIndex);
		this.parent = parent;
		this.child = child;
		setLabel(name);
	}
	
	/**
	 * Executes the Command.
	 */
	public void execute() {
		super.execute();
		updateIndexOfSelectedChild();
	}

	/**
	 * Undoes the Command.
	 */
	public void undo() {
		super.undo();
		updateIndexOfSelectedChild();
	}

	/**
	 * Updates the index of the selected child.
	 */
	protected void updateIndexOfSelectedChild() {
		int index = parent.getContents().indexOf(child);
		parent.setIndexOfSelectedChild(index);
	}
}