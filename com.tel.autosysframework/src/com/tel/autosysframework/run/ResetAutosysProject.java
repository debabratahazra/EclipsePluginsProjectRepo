package com.tel.autosysframework.run;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

public class ResetAutosysProject implements IWorkbenchWindowActionDelegate {

	public void dispose() {

	}

	public void init(IWorkbenchWindow window) {
	}

	public void run(IAction action) {
		if(RunAutosysProject.isSimulationSuccessful()) {
			PlatformUI.getWorkbench().restart();
		}		
		
	}

	public void selectionChanged(IAction action, ISelection selection) {

	}

}
