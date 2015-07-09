package com.odcgroup.workbench.compare.ui.navigator;

import org.eclipse.compare.internal.CompareAction;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class OdsCompareAction extends Action  {

	@SuppressWarnings("restriction")
	private CompareAction action = new CompareAction();;

	public OdsCompareAction() {
		setText("Each Other");
	}

	public void dispose() {
	}

	@SuppressWarnings("restriction")
	public void init(IWorkbenchWindow window) {
		action.setActivePart(this, window.getActivePage().getActivePart());
	}

	@SuppressWarnings("restriction")
	public void run() {
		action.setActivePart(this, 
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart());
		action.run(this);
	}

	@SuppressWarnings("restriction")
	public void selectionChanged(ISelection selection) {
		action.selectionChanged(this, selection);
	}
}
