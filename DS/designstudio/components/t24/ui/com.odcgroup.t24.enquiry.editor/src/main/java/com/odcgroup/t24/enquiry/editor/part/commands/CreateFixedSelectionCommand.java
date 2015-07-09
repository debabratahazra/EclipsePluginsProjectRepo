package com.odcgroup.t24.enquiry.editor.part.commands;

import org.eclipse.gef.commands.Command;

import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.FixedSelection;

/**
 *
 * @author phanikumark
 *
 */
public class CreateFixedSelectionCommand extends Command {
	
	private Enquiry enquiry = null;
	private FixedSelection selection;

	public CreateFixedSelectionCommand(Enquiry enquiry) {
		this.enquiry = enquiry;
	}
	
	public Enquiry getEnquiry() {
		return enquiry;
	}

	@Override
	public void execute() {
		redo();
	}
	
	@Override
	public void undo() {
		enquiry.getFixedSelections().remove(selection);
	}
	
	@Override
	public void redo() {
		enquiry.getFixedSelections().add(selection);
	}
	
	public void setSelection(FixedSelection selection) {
		this.selection = selection;
	}

}
