package com.odcgroup.t24.enquiry.editor.part.commands;

import org.eclipse.gef.commands.Command;

import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.Field;

/**
 * TODO: Document me!
 *
 * @author mumesh
 *
 */
public class FieldDeleteCommand extends Command {


	private Field child;
	private Enquiry parent;
	
	@Override
	public boolean canExecute() {
		return child != null && parent != null;
	}
	
	/**
	 * @param child
	 * @param parent
	 */
	public FieldDeleteCommand(Field child, Enquiry parent) {
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
		parent.getFields().add(child);
	}
	
	@Override
	public void redo() {
		parent.getFields().remove(child);
	}

}
