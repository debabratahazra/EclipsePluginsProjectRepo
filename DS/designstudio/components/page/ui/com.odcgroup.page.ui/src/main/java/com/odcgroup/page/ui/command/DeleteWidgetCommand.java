package com.odcgroup.page.ui.command;

import org.eclipse.core.runtime.Assert;

import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.command.matrix.MatrixDeleteBoxContentCommand;


/**
 * A command to remove a Widget from its parent.
 * The command can be undone or redone.
 * 
 * @author Gary Hayes
 */
public class DeleteWidgetCommand extends BaseCommand {
	
	/** Widget to remove. */
	private Widget child;

	/** Widget to remove from. */	
	private Widget parent;
	
	/** The index of the deleted Widget. */
	private int index;
	
	
	/**
	 * Command to delete contents of a V-Box 
	 */
	private MatrixDeleteBoxContentCommand delCommand;

	/**
	 * Create a command that will remove the shape from its parent.
	 * @param parent the ShapesDiagram containing the child
	 * @param child    the Shape to remove
	 * @throws IllegalArgumentException if any parameter is null
	 */
	public DeleteWidgetCommand(Widget parent, Widget child) {
		Assert.isNotNull(parent);
		Assert.isNotNull(child);
		
		this.parent = parent;
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
		if(isContainedInMatrixCell()) {
			delCommand = new MatrixDeleteBoxContentCommand(parent, child);
			delCommand.execute();
		}
		index = parent.getContents().indexOf(child);
		parent.getContents().remove(child);		
	}
	
	private boolean isContainedInMatrixCell() {
		if(child.getTypeName().equals(WidgetTypeConstants.BOX) && parent.getTypeName().equals(WidgetTypeConstants.MATRIX_CONTENTCELL)) {
			return true;
		}
		return false;
	}

	/**
	 * Undoes the deletion.
	 */
	public void undo() {
		if(delCommand != null) {
			delCommand.undo();
		}
		parent.getContents().add(index, child);
	}
	
}