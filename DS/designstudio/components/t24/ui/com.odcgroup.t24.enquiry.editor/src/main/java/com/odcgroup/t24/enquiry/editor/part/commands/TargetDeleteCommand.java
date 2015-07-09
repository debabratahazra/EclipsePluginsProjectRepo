package com.odcgroup.t24.enquiry.editor.part.commands;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;

import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.Target;
import com.odcgroup.t24.enquiry.enquiry.TargetMapping;
import com.odcgroup.t24.enquiry.enquiry.impl.EnquiryFactoryImpl;

/**
 * TODO: Document me!
 * 
 * @author mumesh
 * 
 */
public class TargetDeleteCommand extends Command {

	private Target parent;
	private TargetMapping child;
	private Enquiry enquiry;

	/**
	 * @param parentPart
	 * @param parent2
	 * @param child2
	 */
	public TargetDeleteCommand(Target parent, TargetMapping child) {
		this.parent = parent;
		this.child = child;
	}

	@Override
	public void execute() {
		redo();
	}

	@Override
	public void redo() {
		parent.getMappings().remove(child);
		if (parent.getMappings().isEmpty()) {
			EObject enquiryObj = parent.eContainer();
			if (enquiryObj instanceof Enquiry) {
				this.enquiry = (Enquiry) enquiryObj;
				enquiry.setTarget(null);
			}
		}
	}

	@Override
	public void undo() {
		if (enquiry != null) {
			parent = EnquiryFactoryImpl.eINSTANCE.createTarget();
			parent.setApplication("Application Name");
			enquiry.setTarget(parent);
			parent.getMappings().add(child);
		} else {
			parent.getMappings().add(child);
		}
	}
}
