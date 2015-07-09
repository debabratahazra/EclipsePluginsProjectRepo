package com.odcgroup.workbench.compare.team.action;

import org.eclipse.team.internal.ui.actions.TeamAction;
import org.eclipse.team.svn.ui.action.local.AddToSVNAction;

/**
 *
 * @author pkk
 *
 */
@SuppressWarnings("restriction")
public class SvnAddToAction extends AbstractTeamAction {

	/**
	 * @param text
	 */
	public SvnAddToAction() {
		super("&Add to Version Control...");
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.compare.team.action.AbstractTeamAction#fetchTeamActionDelegate()
	 */
	public TeamAction fetchTeamActionDelegate() {
		return new AddToSVNAction();
	}

}
