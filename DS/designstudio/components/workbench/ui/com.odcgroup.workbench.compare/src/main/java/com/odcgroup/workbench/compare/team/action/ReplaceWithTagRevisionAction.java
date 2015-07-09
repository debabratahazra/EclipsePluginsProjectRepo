package com.odcgroup.workbench.compare.team.action;

import org.eclipse.team.internal.ui.actions.TeamAction;
import org.eclipse.team.svn.ui.action.local.ReplaceWithTagAction;

/**
 * @author pkk
 *
 */
@SuppressWarnings("restriction")
public class ReplaceWithTagRevisionAction extends AbstractTeamAction  {

	public ReplaceWithTagRevisionAction() {
		super("Tag...");
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.compare.team.action.AbstractTeamAction#fetchTeamActionDelegate()
	 */
	public TeamAction fetchTeamActionDelegate() {
		return new ReplaceWithTagAction();
	}
}
