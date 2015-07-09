package com.odcgroup.workbench.ui.action;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.internal.dialogs.WorkbenchPreferenceDialog;

public class OpenPreferencesAction extends Action {

	final static private String ID = "OPEN_OFS_PREFERENCES";

	private IWorkbenchWindow window;
	
	public OpenPreferencesAction(IWorkbenchWindow window) {
		this.setId(ID);
		this.setText("Preferences...");
		this.window = window;
	}

	public OpenPreferencesAction() {
	}

	/*
	 * (non-Javadoc) Method declared on IAction.
	 */
	public void run() {
		WorkbenchPreferenceDialog dialog = WorkbenchPreferenceDialog.createDialogOn(window.getShell(), "com.odcgroup.workbench.preferences.ofs");
		String[] pages = {"com.odcgroup.workbench.preferences.ofs"};
		dialog.showOnly(pages);
		dialog.create();
		dialog.open();
	}
}
