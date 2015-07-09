package com.odcgroup.page.ui.command;

import org.eclipse.core.runtime.Assert;

import com.odcgroup.page.model.Widget;

/**
 * Command executed when a child Widget is moved.
 * 
 * @author Gary Hayes
 */
public class MoveWidgetCommand extends BaseCommand {

	/** The old index of the child Widget. */
	private int oldIndex;
	
	/** The new index of the child Widget. */
	private int newIndex;
	
	/** The child Widget. */
	private Widget child;
	
	/** The parent Widget. */
	private Widget parent;

	/**
	 * Creates a new MoveChildWidgetCommand.
	 * 
	 * @param child The child Widget
	 * @param parent The parent Widget
	 * @param newIndex The new index of the child Widget
	 */
	public MoveWidgetCommand(Widget parent, Widget child, int newIndex) {
		Assert.isNotNull(child);
		Assert.isNotNull(parent);
		
		this.child = child;
		this.parent = parent;
		this.newIndex = newIndex;
		
		setLabel("Move "+child.getTypeName()+" Widget");
	}

	/**
	 * Executes the Command.
	 */
	public void execute() {
		oldIndex = parent.getContents().indexOf(child);
		parent.getContents().remove(child);
		parent.getContents().add(newIndex, child);
	}

	/**
	 * Undoes the Command.
	 */
	public void undo() {
		parent.getContents().remove(child);
		parent.getContents().add(oldIndex, child);
	}
}