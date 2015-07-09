package com.odcgroup.workbench.compare.team.action;

import org.eclipse.team.internal.ui.actions.TeamAction;
import org.eclipse.team.svn.ui.action.local.management.DisconnectAction;

/**
 *
 * @author pkk
 *
 */
@SuppressWarnings("restriction")
public class SvnDisconnectAction extends AbstractTeamAction {

	/**
	 * @param text
	 */
	public SvnDisconnectAction() {
		super("&Disconnect");
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.compare.team.action.AbstractTeamAction#fetchTeamActionDelegate()
	 */
	public TeamAction fetchTeamActionDelegate() {
		return new DisconnectAction();
	}

}
