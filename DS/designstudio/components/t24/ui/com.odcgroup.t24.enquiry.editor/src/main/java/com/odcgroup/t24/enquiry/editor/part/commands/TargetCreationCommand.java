package com.odcgroup.t24.enquiry.editor.part.commands;

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
public class TargetCreationCommand extends Command {

	private TargetMapping child;
	private Target parent;
	private Enquiry enquiry;

	/**
	 * @param parent
	 * @param child
	 */
	public TargetCreationCommand(Target parent, TargetMapping child) {
		super();
		this.parent = parent;
		this.child = child;
	}

	public TargetCreationCommand(Enquiry enquiry, Target parent, TargetMapping child) {
		super();
		this.parent = parent;
		this.child = child;
		this.enquiry = enquiry;
	}

	@Override
	public void execute() {
		redo();
	}

	@Override
	public void undo() {
		parent.getMappings().remove(child);
		if (enquiry != null) {
			enquiry.setTarget(null);
		}

	}

	@Override
	public void redo() {
		if (enquiry != null && parent == null) {
			parent = EnquiryFactoryImpl.eINSTANCE.createTarget();
			parent.setApplication("ApplicationName");
			enquiry.setTarget(parent);
		}
		parent.getMappings().add(child);
	}

}
