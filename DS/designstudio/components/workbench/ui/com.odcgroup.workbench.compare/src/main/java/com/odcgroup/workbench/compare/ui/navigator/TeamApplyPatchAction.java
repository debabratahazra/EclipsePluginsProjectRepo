package com.odcgroup.workbench.compare.ui.navigator;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.team.internal.ui.actions.ApplyPatchAction;

/**
 *
 * @author pkk
 *
 */
@SuppressWarnings("restriction")
public class TeamApplyPatchAction extends Action  {
	
	private ApplyPatchAction action = new ApplyPatchAction();
	
	public TeamApplyPatchAction() {
		setText("Apply Patch...");
		action.init(this);
	}
	
	public void dispose() {
		action.dispose();
	}	

	public void run() {
		action.run(this);
	}
	
	
	public boolean isEnabled() {
		return action.isEnabled();
	}

	/**
	 * @param selection
	 */
	public void selectionChanged(ISelection selection) {
		action.selectionChanged(this, selection);
	}

}
