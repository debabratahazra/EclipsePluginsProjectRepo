package com.odcgroup.workbench.compare.team.action;

import org.eclipse.team.internal.ui.actions.TeamAction;
import org.eclipse.team.svn.ui.action.local.management.ShareProjectsAction;

@SuppressWarnings("restriction")
public class SvnShareProjectsAction extends AbstractTeamAction {

	public SvnShareProjectsAction() {
		super("Share Projects...");
	}
	
	@Override
	public TeamAction fetchTeamActionDelegate() {
		return new ShareProjectsAction();
	}

}
