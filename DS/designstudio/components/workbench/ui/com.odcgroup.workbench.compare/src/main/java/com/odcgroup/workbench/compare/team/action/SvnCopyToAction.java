package com.odcgroup.workbench.compare.team.action;

import org.eclipse.team.internal.ui.actions.TeamAction;
import org.eclipse.team.svn.ui.action.local.CopyAction;

/**
 *
 * @author pkk
 *
 */
@SuppressWarnings("restriction")
public class SvnCopyToAction extends AbstractTeamAction {

	/**
	 * @param text
	 */
	public SvnCopyToAction() {
		super("Copy To...");
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.compare.team.action.AbstractTeamAction#fetchTeamActionDelegate()
	 */
	public TeamAction fetchTeamActionDelegate() {
		return new CopyAction();
	}

}
