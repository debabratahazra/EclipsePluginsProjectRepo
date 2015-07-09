package com.odcgroup.t24.enquiry.editor.part.actions;

import org.eclipse.ui.IWorkbenchPart;

/**
 * TODO: Document me!
 * 
 * @author mumesh
 * 
 */
public class AddJBCRoutineAction extends AddRoutineAction {

	public static final String ACTION_ID = "ADD_JBC_ROUTINE";

	/**
	 * @param part
	 */
	public AddJBCRoutineAction(IWorkbenchPart part) {
		super(part, "JBC");
		setId(ACTION_ID);
		setText("Add JBC Routine");
	}
}
