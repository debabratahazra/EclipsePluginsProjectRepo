package com.odcgroup.workbench.compare.team.action;

import org.eclipse.team.internal.ui.actions.TeamAction;
import org.eclipse.team.svn.ui.action.local.ShowHistoryAction;

@SuppressWarnings("restriction")
public class SvnShowLocalHistoryAction extends AbstractTeamAction {

	public SvnShowLocalHistoryAction() {
		super("Show Local History");
	}

	@Override
	public TeamAction fetchTeamActionDelegate() {
		return new ShowHistoryAction();
	}

}
