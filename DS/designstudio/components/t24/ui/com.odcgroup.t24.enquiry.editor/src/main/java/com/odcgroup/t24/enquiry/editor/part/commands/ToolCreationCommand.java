package com.odcgroup.t24.enquiry.editor.part.commands;

import org.eclipse.gef.commands.Command;

import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.Tool;

/**
 * TODO: Document me!
 * 
 * @author mumesh
 * 
 */
public class ToolCreationCommand extends Command {

	private Tool child;
	private Enquiry parent;

	@Override
	public boolean canExecute() {
		return child != null && parent != null;
	}

	/**
	 * @param child
	 * @param parent
	 */
	public ToolCreationCommand(Tool child, Enquiry parent) {
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
		parent.getTools().remove(child);
	}

	@Override
	public void redo() {
		parent.getTools().add(child);
	}

}
