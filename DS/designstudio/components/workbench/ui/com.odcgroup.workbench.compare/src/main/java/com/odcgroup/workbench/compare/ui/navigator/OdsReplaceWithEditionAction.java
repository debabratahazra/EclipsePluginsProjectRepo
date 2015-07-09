package com.odcgroup.workbench.compare.ui.navigator;

import org.eclipse.compare.internal.EditionAction;
import org.eclipse.compare.internal.ReplaceWithEditionAction;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;

public class OdsReplaceWithEditionAction extends Action  {

	@SuppressWarnings("restriction")
	private EditionAction action = new ReplaceWithEditionAction();

	public OdsReplaceWithEditionAction() {
		setText("Local History...");
	}

	public void dispose() {
	}

	public void init(IWorkbenchWindow window) {
	}

	@SuppressWarnings("restriction")
	public void run() {
		action.run(this);
	}

	@SuppressWarnings("restriction")
	public void selectionChanged(ISelection selection) {
		action.selectionChanged(this, selection);
	}
}
