package com.odcgroup.t24.enquiry.editor.part.commands;

import org.eclipse.emf.common.util.EList;
import org.eclipse.gef.commands.Command;

import com.odcgroup.t24.enquiry.enquiry.Target;
import com.odcgroup.t24.enquiry.enquiry.TargetMapping;

/**
 * TODO: Document me!
 * 
 * @author mumesh
 * 
 */
public class TargetMappingMoveCommand extends Command {

	private boolean isIcrement;
	private TargetMapping child;
	private Target parent;

	/**
	 * @param child
	 * @param isIcrement
	 */
	public TargetMappingMoveCommand(TargetMapping child, Target parent, boolean isIcrement) {
		super();
		this.isIcrement = isIcrement;
		this.child = child;
		this.parent = parent;
	}

	@Override
	public void execute() {
		redo();
	}

	@Override
	public void redo() {
		EList<TargetMapping> list = parent.getMappings();
		int index = list.indexOf(child);
		list.remove(child);
		int modifiedIndex = isIcrement ? index + 1 : index - 1;
		list.add(modifiedIndex, child);
	}

	@Override
	public void undo() {
		EList<TargetMapping> list = parent.getMappings();
		int index = list.indexOf(child);
		list.remove(child);
		int modifiedIndex = isIcrement ? index - 1 : index + 1;
		list.add(modifiedIndex, child);
	}

}
