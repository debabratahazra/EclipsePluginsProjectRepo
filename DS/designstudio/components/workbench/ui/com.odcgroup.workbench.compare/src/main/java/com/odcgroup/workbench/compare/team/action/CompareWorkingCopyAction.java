package com.odcgroup.workbench.compare.team.action;

import org.eclipse.team.internal.ui.actions.TeamAction;
import org.eclipse.team.svn.ui.action.local.CompareWithWorkingCopyAction;

/**
 *
 * @author pkk
 *
 */
@SuppressWarnings("restriction")
public class CompareWorkingCopyAction extends AbstractTeamAction {
	
	public CompareWorkingCopyAction() {
		super("Base from Working Copy");
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.compare.team.action.AbstractTeamAction#fetchTeamActionDelegate()
	 */
	public TeamAction fetchTeamActionDelegate() {
		return new CompareWithWorkingCopyAction();
	}

}
