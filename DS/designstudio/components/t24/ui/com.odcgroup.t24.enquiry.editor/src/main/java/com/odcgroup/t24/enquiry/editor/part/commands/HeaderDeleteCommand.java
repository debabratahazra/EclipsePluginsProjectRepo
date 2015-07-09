package com.odcgroup.t24.enquiry.editor.part.commands;

import org.eclipse.gef.commands.Command;

import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.EnquiryHeader;

/**
 * TODO: Document me!
 *
 * @author mumesh
 *
 */
public class HeaderDeleteCommand extends Command {


	private EnquiryHeader child;
	private Enquiry parent;
	
	@Override
	public boolean canExecute() {
		return child != null && parent != null;
	}
	
	/**
	 * @param child
	 * @param parent
	 */
	public HeaderDeleteCommand(EnquiryHeader child, Enquiry parent) {
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
		parent.getHeader().add(child);
	}
	
	@Override
	public void redo() {
		parent.getHeader().remove(child);
	}



}
