package com.odcgroup.t24.enquiry.editor.part.commands;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;

import com.odcgroup.t24.enquiry.enquiry.DrillDown;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.Selection;
import com.odcgroup.t24.enquiry.enquiry.SelectionCriteria;
import com.odcgroup.t24.enquiry.enquiry.SelectionExpression;
import com.odcgroup.t24.enquiry.enquiry.impl.EnquiryFactoryImpl;

/**
 *
 * @author mumesh
 * @author phanikumark
 *
 */
public class SelectionCreationCommand extends Command {

	private EObject child;
	private Enquiry enquiry;
	private DrillDown drillDown;
	
	/**
	 * create a custom selection
	 * 
	 * @param enquiry
	 * @param child
	 */
	public SelectionCreationCommand(Enquiry enquiry, EObject child){
		super();
		this.enquiry = enquiry;
		this.child = child;
	}
	
	/**
	 * create selection criteria for a drilldown
	 * 
	 * @param drilldown
	 * @param child
	 */
	public SelectionCreationCommand(DrillDown drilldown, EObject child) {
		super();
		this.drillDown = drilldown;
		this.child = child;
		
	}

	@Override
	public void execute() {
		redo();
	}
	
	@Override
	public void undo() {
		if(enquiry != null){
			SelectionExpression selexp = enquiry.getCustomSelection();
			selexp.getSelection().remove(child);
			if (selexp.getSelection().isEmpty()) {
				enquiry.setCustomSelection(null);
			}
		} else if (drillDown != null) {
			drillDown.getCriteria().remove(child);
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
			selexp.getSelection().add((Selection)child);
		} else if (drillDown != null) {
			drillDown.getCriteria().add((SelectionCriteria)child);
		}
	}
}
