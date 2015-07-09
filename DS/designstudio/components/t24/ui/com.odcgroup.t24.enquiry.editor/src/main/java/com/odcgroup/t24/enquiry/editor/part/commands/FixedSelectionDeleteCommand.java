package com.odcgroup.t24.enquiry.editor.part.commands;

import org.eclipse.gef.commands.Command;

import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.FixedSelection;

/**
 * 
 * @author mumesh
 * 
 */
public class FixedSelectionDeleteCommand extends Command {

	protected FixedSelection child;
	protected Enquiry parent;

	/**
	 * @param parentPart
	 * @param parent2
	 * @param child2
	 */
	public FixedSelectionDeleteCommand(Enquiry parent, FixedSelection child) {
		this.parent = parent;
		this.child = child;
	}

	@Override
	public void execute() {
		redo();
	}
	
	@Override
	public void undo() {
		parent.getFixedSelections().add(child);

	}
	
	@Override
	public void redo() {
		parent.getFixedSelections().remove(child);
	}
}
