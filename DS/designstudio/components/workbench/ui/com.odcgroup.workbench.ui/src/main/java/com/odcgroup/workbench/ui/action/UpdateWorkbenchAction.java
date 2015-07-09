package com.odcgroup.workbench.ui.action;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.ui.IWorkbenchWindow;

public class UpdateWorkbenchAction extends Action {

	final static private String ID = "UPDATE_WORKBENCH";

	private IWorkbenchWindow window;
	
	public UpdateWorkbenchAction(IWorkbenchWindow window) {
		this.setId(ID);
		this.setText("Update Workbench...");
		this.window = window;
	}

	public void run() {
		BusyIndicator.showWhile(window.getShell().getDisplay(),
			new Runnable() {
				public void run() {
//					UpdateJob job = new UpdateJob("Search for updates",
//					true, true);
//					job.setUser(true);
//					job.setPriority(Job.INTERACTIVE);
//					UpdateManagerUI.openInstaller(window.getShell(), job);
				}
			});
	}
}