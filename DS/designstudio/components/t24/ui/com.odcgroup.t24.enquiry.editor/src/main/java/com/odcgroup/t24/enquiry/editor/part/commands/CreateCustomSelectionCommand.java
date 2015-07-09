package com.odcgroup.t24.enquiry.editor.part.commands;

import org.eclipse.gef.commands.Command;

import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.Selection;
import com.odcgroup.t24.enquiry.enquiry.SelectionExpression;
import com.odcgroup.t24.enquiry.enquiry.impl.EnquiryFactoryImpl;

/**
 *
 * @author phanikumark
 *
 */
public class CreateCustomSelectionCommand extends Command {
	
	private Enquiry enquiry = null;
	private Selection selection;

	public CreateCustomSelectionCommand(Enquiry enquiry) {
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
		if(enquiry != null){
			SelectionExpression selexp = enquiry.getCustomSelection();
			selexp.getSelection().remove(selection);
			if (selexp.getSelection().isEmpty()) {
				enquiry.setCustomSelection(null);
			}
		}

	}
	
	@Override
	public void redo() {
		if(enquiry != null){
			SelectionExpression selexp = enquiry.getCustomSelection();
			if (selexp == null) {
				selexp = EnquiryFactoryImpl.eINSTANCE.createSelectionExpression();
				enquiry.setCustomSelection(selexp);
			}
			selexp.getSelection().add((Selection)selection);
		} 
	}

	public void setSelection(Selection selection) {
		this.selection = selection;
	}

}
