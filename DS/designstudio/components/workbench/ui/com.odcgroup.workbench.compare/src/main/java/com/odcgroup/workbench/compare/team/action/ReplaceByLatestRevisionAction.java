package com.odcgroup.workbench.compare.team.action;

import org.eclipse.team.internal.ui.actions.TeamAction;
import org.eclipse.team.svn.ui.action.local.ReplaceWithLatestRevisionAction;

/**
 *
 * @author pkk
 *
 */
@SuppressWarnings("restriction")
public class ReplaceByLatestRevisionAction extends AbstractTeamAction  {

	public ReplaceByLatestRevisionAction() {
		super("Latest from Repository");
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.compare.team.action.AbstractTeamAction#fetchTeamActionDelegate()
	 */
	public TeamAction fetchTeamActionDelegate() {
		return new ReplaceWithLatestRevisionAction();
	}
}