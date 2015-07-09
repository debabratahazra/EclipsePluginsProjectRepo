package com.odcgroup.workbench.compare.team.action;

import org.eclipse.team.internal.ui.actions.TeamAction;
import org.eclipse.team.svn.ui.action.local.CompareWithRevisionAction;

/**
 *
 * @author pkk
 *
 */
@SuppressWarnings("restriction")
public class CompareRevisionAction extends AbstractTeamAction {
		
	public CompareRevisionAction() {
		super("URL...");
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.compare.team.action.AbstractTeamAction#fetchTeamActionDelegate()
	 */
	public TeamAction fetchTeamActionDelegate() {
		return new CompareWithRevisionAction();
	}

}
