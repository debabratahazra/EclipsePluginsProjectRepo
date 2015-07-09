package com.odcgroup.workbench.compare.team.action;

import org.eclipse.team.internal.ui.actions.TeamAction;
import org.eclipse.team.svn.ui.action.local.management.CleanupAction;

/**
 *
 * @author pkk
 *
 */
@SuppressWarnings("restriction")
public class SvnCleanUpAction extends AbstractTeamAction {

	/**
	 * @param text
	 */
	public SvnCleanUpAction() {
		super("C&leanup");
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.compare.team.action.AbstractTeamAction#fetchTeamActionDelegate()
	 */
	public TeamAction fetchTeamActionDelegate() {
		return new CleanupAction();
	}

}
