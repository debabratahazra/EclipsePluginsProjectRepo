package com.odcgroup.workbench.compare.team.action;

import org.eclipse.team.internal.ui.actions.TeamAction;
import org.eclipse.team.svn.ui.action.local.ReplaceWithBranchAction;

/**
 *
 * @author pkk
 *
 */
@SuppressWarnings("restriction")
public class ReplaceWithBranchRevisionAction  extends AbstractTeamAction  {

	public ReplaceWithBranchRevisionAction() {
		super("Branch...");
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.compare.team.action.AbstractTeamAction#fetchTeamActionDelegate()
	 */
	public TeamAction fetchTeamActionDelegate() {
		return new ReplaceWithBranchAction();
	}
}
