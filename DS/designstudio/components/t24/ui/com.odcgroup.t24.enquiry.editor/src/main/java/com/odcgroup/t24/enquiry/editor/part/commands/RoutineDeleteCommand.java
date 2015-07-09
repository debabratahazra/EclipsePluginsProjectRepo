package com.odcgroup.t24.enquiry.editor.part.commands;

import org.eclipse.gef.commands.Command;

import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.Routine;

/**
 * TODO: Document me!
 * 
 * @author mumesh
 * 
 */
public class RoutineDeleteCommand extends Command {

	private Routine child;
	private Enquiry parent;

	@Override
	public boolean canExecute() {
		return child != null && parent != null;
	}

	/**
	 * @param child
	 * @param parent
	 */
	public RoutineDeleteCommand(Routine child, Enquiry parent) {
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
		parent.getBuildRoutines().add(child);
	}

	@Override
	public void redo() {
		parent.getBuildRoutines().remove(child);
	}

}
