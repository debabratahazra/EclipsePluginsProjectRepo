package com.odcgroup.t24.enquiry.editor.part.commands;

import org.eclipse.gef.commands.Command;

import com.odcgroup.t24.enquiry.enquiry.DrillDown;
import com.odcgroup.t24.enquiry.enquiry.SelectionCriteria;

/**
 *
 * @author phanikumark
 *
 */
public class CriteriaDeleteCommand extends Command {

	private DrillDown parent;
	private SelectionCriteria child;
	
	@Override
	public boolean canExecute() {
		return child != null && parent != null;
	}
	
	/**
	 * @param child
	 * @param parent
	 */
	public CriteriaDeleteCommand(SelectionCriteria child, DrillDown parent) {
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
		parent.getCriteria().add(child);
	}
	
	@Override
	public void redo() {
		parent.getCriteria().remove(child);
	}

}
