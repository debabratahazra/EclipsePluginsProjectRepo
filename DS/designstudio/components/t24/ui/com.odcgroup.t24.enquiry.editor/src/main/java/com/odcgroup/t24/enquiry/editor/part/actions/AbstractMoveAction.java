package com.odcgroup.t24.enquiry.editor.part.actions;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.gef.ui.actions.UpdateAction;
import org.eclipse.ui.IWorkbenchPart;

/**
 * TODO: Document me!
 *
 * @author mumesh
 *
 */
public abstract class AbstractMoveAction extends SelectionAction  implements UpdateAction{

	public static String INCREMENT_FLAG = "isIcrement";
	public static String SELECTED_CHILD = "selected_child";
	public static String EDIT_PART = "edit_part";
	/**
	 * @param part
	 */
	public AbstractMoveAction(IWorkbenchPart part) {
		super(part);
	}

	@Override
	public void run() {
		getCommandStack().execute(getCommand());
	}
	
	protected abstract Command getCommand();
}
