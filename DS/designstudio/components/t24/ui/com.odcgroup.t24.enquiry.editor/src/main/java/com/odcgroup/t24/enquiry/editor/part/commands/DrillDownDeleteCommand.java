package com.odcgroup.t24.enquiry.editor.part.commands;

import org.eclipse.gef.commands.Command;

import com.odcgroup.t24.enquiry.enquiry.DrillDown;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;

/**
 * TODO: Document me!
 *
 * @author mumesh
 *
 */
public class DrillDownDeleteCommand extends Command {

	private DrillDown child;
	private Enquiry parent;
	
	@Override
	public boolean canExecute() {
		return child != null && parent != null;
	}
	
	/**
	 * @param child
	 * @param parent
	 */
	public DrillDownDeleteCommand(DrillDown child, Enquiry parent) {
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
		parent.getDrillDowns().add(child);
	}
	
	@Override
	public void redo() {
		parent.getDrillDowns().remove(child);
	}



}
