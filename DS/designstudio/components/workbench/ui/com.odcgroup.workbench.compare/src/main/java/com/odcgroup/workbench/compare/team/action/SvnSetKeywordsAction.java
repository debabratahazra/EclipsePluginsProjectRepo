package com.odcgroup.workbench.compare.team.action;

import org.eclipse.team.internal.ui.actions.TeamAction;
import org.eclipse.team.svn.ui.action.local.SetKeywordsAction;

/**
 *
 * @author pkk
 *
 */
@SuppressWarnings("restriction")
public class SvnSetKeywordsAction extends AbstractTeamAction {

	/**
	 * @param text
	 */
	public SvnSetKeywordsAction() {
		super("Set Keywords...");
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.compare.team.action.AbstractTeamAction#fetchTeamActionDelegate()
	 */
	public TeamAction fetchTeamActionDelegate() {
		return new SetKeywordsAction();
	}

}
