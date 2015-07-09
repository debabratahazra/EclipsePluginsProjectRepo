package com.odcgroup.workbench.compare.team.action;

import org.eclipse.swt.SWT;
import org.eclipse.team.internal.ui.actions.TeamAction;
import org.eclipse.team.svn.ui.action.local.CreatePatchAction;

/**
 * 
 * @author pkk
 *
 */
@SuppressWarnings("restriction")
public class SvnCreatePatchAction extends AbstractTeamAction {

	/**
	 * @param text
	 */
	public SvnCreatePatchAction() {
		super("Create Patch...@Ctrl+Alt+P");
		setAccelerator(SWT.CTRL+SWT.ALT+'P');
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.compare.team.action.AbstractTeamAction#fetchTeamActionDelegate()
	 */
	public TeamAction fetchTeamActionDelegate() {
		return new CreatePatchAction();
	}

}
