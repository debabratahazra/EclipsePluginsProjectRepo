package com.odcgroup.workbench.compare.team.action;

import org.eclipse.team.internal.ui.actions.TeamAction;
import org.eclipse.team.svn.ui.action.local.EditTreeConflictsAction;

/**
 *
 * @author pkk
 *
 */
@SuppressWarnings("restriction")
public class SvnEditTreeConflictsAction extends AbstractTeamAction {

	/**
	 * @param text
	 */
	public SvnEditTreeConflictsAction() {
		super("Edit Tree Conflicts");
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.compare.team.action.AbstractTeamAction#fetchTeamActionDelegate()
	 */
	public TeamAction fetchTeamActionDelegate() {
		return new EditTreeConflictsAction();
	}

}

