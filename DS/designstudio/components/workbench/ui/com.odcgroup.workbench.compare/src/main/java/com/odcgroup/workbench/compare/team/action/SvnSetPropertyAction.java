package com.odcgroup.workbench.compare.team.action;

import org.eclipse.team.internal.ui.actions.TeamAction;
import org.eclipse.team.svn.ui.action.local.SetPropertyAction;

/**
 *
 * @author pkk
 *
 */
@SuppressWarnings("restriction")
public class SvnSetPropertyAction extends AbstractTeamAction {

	/**
	 * @param text
	 */
	public SvnSetPropertyAction() {
		super("Set Property...");
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.compare.team.action.AbstractTeamAction#fetchTeamActionDelegate()
	 */
	public TeamAction fetchTeamActionDelegate() {
		return new SetPropertyAction();
	}

}
