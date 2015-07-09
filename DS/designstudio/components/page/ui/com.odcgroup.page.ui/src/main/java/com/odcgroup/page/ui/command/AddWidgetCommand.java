package com.odcgroup.page.ui.command;

import org.eclipse.core.runtime.Assert;

import com.odcgroup.page.model.Widget;

/**
 * Command to be executed when we want to add a Widget to another one. Note that when we add a Widget to another one EMF
 * automatically removes the Widget from the old parent. Therefore we need to keep a reference to the old parent so that
 * if the user undoes the Command we know which the old parent was.
 * 
 * @author Gary Hayes
 */
public class AddWidgetCommand extends BaseCommand {

	/** The child. */
	private Widget child;

	/** The old parent. */
	private Widget oldParent;

	/** The old index within the contents List. */
	private int oldIndex = -1;

	/** The newParent. */
	private Widget newParent;

	/** The new index within the contents List. */
	private int newIndex = -1;
	
	/**
	 * Creates a new AddWidgetCommand. The Widget is added to the 
	 * end of the contents
	 * 
	 * @param newParent
	 *            The new parent Widget
	 * @param child
	 *            The child Widget
	 */
	public AddWidgetCommand(Widget newParent, Widget child) {
		this(newParent, child, newParent.getContents().size());
	}	
	
	/**
	 * Creates a new AddWidgetCommand.
	 * 
	 * @param newParent
	 *            The new parent Widget
	 * @param child
	 *            The child Widget
	 * @param newIndex
	 *            The index
	 */
	public AddWidgetCommand(Widget newParent, Widget child, int newIndex) {
		Assert.isNotNull(newParent);
		Assert.isNotNull(child);

		this.newParent = newParent;
		this.child = child;
		this.newIndex = newIndex;

		oldParent = child.getParent();
		if (oldParent != null) {
			oldIndex = oldParent.getContents().indexOf(child);
		}
		
		setLabel("Add "+child.getTypeName()+" Widget");
	}

	/**
	 * Executes the command.
	 */
	public void execute() {
		if (newIndex < 0) {
			newParent.getContents().add(child);
		} else {
			newParent.getContents().add(newIndex, child);
		}
	}

	/**
	 * Undos the Command.
	 */
	public void undo() {
		if (oldParent != null) {
			// In this case EMF takes care of removing the child from the old parent
			if (oldIndex < 0) {
				oldParent.getContents().add(child);
			} else {
				oldParent.getContents().add(oldIndex, child);
			}
		} else {
			// In this case we have to remove the child ourselves
			newParent.getContents().remove(child);
		}
	}
}