package com.odcgroup.t24.enquiry.editor.part.commands;

import org.eclipse.gef.commands.Command;

import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.WebService;

/**
 * TODO: Document me!
 * 
 * @author mumesh
 * 
 */
public class WebServiceCreationCommand extends Command {

	private WebService child;
	private Enquiry parent;

	@Override
	public boolean canExecute() {
		return child != null && parent != null;
	}

	/**
	 * @param child
	 * @param parent
	 */
	public WebServiceCreationCommand(WebService child, Enquiry parent) {
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
		parent.setWebService(null);
	}

	@Override
	public void redo() {
		parent.setWebService(child);
	}

}
