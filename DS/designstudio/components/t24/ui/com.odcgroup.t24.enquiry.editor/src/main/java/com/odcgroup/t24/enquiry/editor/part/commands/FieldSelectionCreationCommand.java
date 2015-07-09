package com.odcgroup.t24.enquiry.editor.part.commands;

import org.eclipse.gef.commands.Command;

import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.FixedSelection;

/**
 * TODO: Document me!
 *
 * @author mumesh
 *
 */
public class FieldSelectionCreationCommand extends Command {

	private FixedSelection child;
	private Enquiry parent;
	
	@Override
	public boolean canExecute() {
		return child != null && parent != null;
	}
	
	/**
	 * @param child
	 * @param parent
	 */
	public FieldSelectionCreationCommand(FixedSelection child, Enquiry parent) {
		super();
		this.child = child;
		this.parent = parent;
	}

	@Override
	public void execute() {
		redo();
	}
	
	@Override
	public void undo() {
		parent.getFixedSelections().remove(child);
	}
	
	@Override
	public void redo() {
		parent.getFixedSelections().add(child);
	}
}
