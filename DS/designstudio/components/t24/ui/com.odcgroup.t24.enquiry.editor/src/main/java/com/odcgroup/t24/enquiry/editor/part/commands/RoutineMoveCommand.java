package com.odcgroup.t24.enquiry.editor.part.commands;

import org.eclipse.emf.common.util.EList;
import org.eclipse.gef.commands.Command;

import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.Routine;

/**
 * TODO: Document me!
 * 
 * @author mumesh
 * 
 */
public class RoutineMoveCommand extends Command {

	private boolean isIcrement;
	private Routine child;
	private Enquiry parent;

	/**
	 * @param isIcrement
	 * @param child
	 * @param parent
	 */
	public RoutineMoveCommand(boolean isIcrement, Routine child, Enquiry parent) {
		super();
		this.isIcrement = isIcrement;
		this.child = child;
		this.parent = parent;
	}

	@Override
	public void execute() {
		redo();
	}

	@Override
	public void redo() {
		EList<Routine> list = parent.getBuildRoutines();
		int index = list.indexOf(child);
		list.remove(child);
		int modifiedIndex = isIcrement ? index + 1 : index - 1;
		list.add(modifiedIndex, child);
	}

	@Override
	public void undo() {
		EList<Routine> list = parent.getBuildRoutines();
		int index = list.indexOf(child);
		list.remove(child);
		int modifiedIndex = isIcrement ? index - 1 : index + 1;
		list.add(modifiedIndex, child);
	}

}
