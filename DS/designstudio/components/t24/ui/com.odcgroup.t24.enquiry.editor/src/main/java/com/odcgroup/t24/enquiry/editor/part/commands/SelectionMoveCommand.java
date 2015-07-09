package com.odcgroup.t24.enquiry.editor.part.commands;

import org.eclipse.emf.common.util.EList;
import org.eclipse.gef.commands.Command;

import com.odcgroup.t24.enquiry.enquiry.Selection;
import com.odcgroup.t24.enquiry.enquiry.SelectionExpression;

/**
 * TODO: Document me!
 * 
 * @author mumesh
 * 
 */
public class SelectionMoveCommand extends Command {

	private boolean isIcrement;
	private Selection child;
	private SelectionExpression parent;

	/**
	 * @param child
	 * @param isIcrement
	 */
	public SelectionMoveCommand(Selection child, SelectionExpression parent, boolean isIcrement) {
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
		EList<Selection> list = parent.getSelection();
		int index = list.indexOf(child);
		list.remove(child);
		int modifiedIndex = isIcrement ? index + 1 : index - 1;
		list.add(modifiedIndex, child);
	}

	@Override
	public void undo() {
		EList<Selection> list = parent.getSelection();
		int index = list.indexOf(child);
		list.remove(child);
		int modifiedIndex = isIcrement ? index - 1 : index + 1;
		list.add(modifiedIndex, child);
	}

}
