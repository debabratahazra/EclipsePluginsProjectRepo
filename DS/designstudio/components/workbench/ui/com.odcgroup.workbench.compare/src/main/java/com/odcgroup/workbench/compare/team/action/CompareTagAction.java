package com.odcgroup.workbench.compare.team.action;

import org.eclipse.team.internal.ui.actions.TeamAction;
import org.eclipse.team.svn.ui.action.local.CompareWithTagAction;

/**
 *
 * @author pkk
 *
 */
@SuppressWarnings("restriction")
public class CompareTagAction extends AbstractTeamAction {
		
	public CompareTagAction() {
		super("Tag...");
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.compare.team.action.AbstractTeamAction#fetchTeamActionDelegate()
	 */
	public TeamAction fetchTeamActionDelegate() {
		return new CompareWithTagAction();
	}

}
