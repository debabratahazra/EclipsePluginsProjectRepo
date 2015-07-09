package com.odcgroup.t24.enquiry.editor.part.commands;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;

import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.Selection;
import com.odcgroup.t24.enquiry.enquiry.SelectionExpression;
import com.odcgroup.t24.enquiry.enquiry.impl.EnquiryFactoryImpl;

/**
 * TODO: Document me!
 *
 * @author mumesh
 *
 */
public class SelectionExpressionDeleteCommand extends Command {

	private SelectionExpression parent;
	private Selection child;
	private Enquiry enquiry;
	
	/**
	 * @param parentPart 
	 * @param parent2
	 * @param child2
	 */
	public SelectionExpressionDeleteCommand(SelectionExpression parent, Selection child) {
		this.parent = parent;
		this.child = child;
	}
 
	@Override
	public void execute() {
	redo();
	}
	
	@Override
	public void redo() {
		parent.getSelection().remove(child);
		if(parent.getSelection().isEmpty()){
			EObject enquiryObj = parent.eContainer();
			if(enquiryObj instanceof Enquiry){
				this.enquiry = (Enquiry)enquiryObj;
				enquiry.setCustomSelection(null);
			}
		}
	}
	
	@Override
	public void undo() {
		if(enquiry!=null){
			parent = EnquiryFactoryImpl.eINSTANCE.createSelectionExpression();
			enquiry.setCustomSelection(parent);
			parent.getSelection().add(child);
		}
		else{
			parent.getSelection().add(child);
		}
	}
}
