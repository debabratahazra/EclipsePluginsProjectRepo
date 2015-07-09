package com.odcgroup.workbench.compare.team.action;

import org.eclipse.team.internal.ui.actions.TeamAction;
import org.eclipse.team.svn.ui.action.local.CompareWithLatestRevisionAction;


@SuppressWarnings("restriction")
public class CompareLatestRevisionAction extends AbstractTeamAction {
	
	
	public CompareLatestRevisionAction() {
		super("&Latest from Repository");
	}

	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.compare.team.action.AbstractTeamAction#fetchTeamActionDelegate()
	 */
	public TeamAction fetchTeamActionDelegate(){
		return new CompareWithLatestRevisionAction();
	}

}
