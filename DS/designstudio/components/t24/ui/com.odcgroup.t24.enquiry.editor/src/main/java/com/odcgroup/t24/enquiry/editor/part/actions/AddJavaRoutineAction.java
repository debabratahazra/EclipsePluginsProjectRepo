package com.odcgroup.t24.enquiry.editor.part.actions;

import org.eclipse.ui.IWorkbenchPart;

/**
 * TODO: Document me!
 * 
 * @author mumesh
 * 
 */
public class AddJavaRoutineAction extends AddRoutineAction {

	public static final String ACTION_ID = "ADD_JAVA_ROUTINE";

	/**
	 * @param part
	 */
	public AddJavaRoutineAction(IWorkbenchPart part) {
		super(part, "Java");
		setId(ACTION_ID);
		setText("Add Java Routine");
	}

}
