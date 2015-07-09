package com.odcgroup.workbench.compare.team.action;

import org.eclipse.team.internal.ui.actions.TeamAction;
import org.eclipse.team.svn.ui.action.local.CompareWithBranchAction;

/**
 *
 * @author pkk
 *
 */
@SuppressWarnings("restriction")
public class CompareBranchAction extends AbstractTeamAction {
	
	
	public CompareBranchAction() {
		super("Branch...");
	}

	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.compare.team.action.AbstractTeamAction#fetchTeamActionDelegate()
	 */
	public TeamAction fetchTeamActionDelegate(){
		return new CompareWithBranchAction();
	}

}