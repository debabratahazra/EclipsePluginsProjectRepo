package com.odcgroup.workbench.compare.ui.navigator;

import org.eclipse.compare.internal.Utilities;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;

import com.odcgroup.workbench.compare.team.action.CompareLocalHistoryAction;
import com.odcgroup.workbench.core.IOfsModelResource;

@SuppressWarnings("restriction")
public class OdsCompareEditionAction extends Action  {

	private CompareLocalHistoryAction action = new CompareLocalHistoryAction();;

	public OdsCompareEditionAction() {
		setText("Local History...");
		action.init(this);
	}

	public void dispose() {
		action.dispose();
	}	

	public void run() {
		action.run(this);
	}
	
	@Override
	public boolean isEnabled() {
		return action.getSelection().size()==1;	
	}

	/**
	 * @param selection
	 */
	public void selectionChanged(ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			Object obj = ((IStructuredSelection) selection).getFirstElement();
			if (obj instanceof IFile) {
				action.selectionChanged(this, selection);
			} else if (obj instanceof IOfsModelResource) {
				Object[] sel = {((IOfsModelResource)obj).getResource()};
				action.selectionChanged(this, new StructuredSelection(sel));
			} else {
				IFile[] files= Utilities.getFiles(selection);
				action.selectionChanged(this, new StructuredSelection(files));
			}
		}
	}
}
