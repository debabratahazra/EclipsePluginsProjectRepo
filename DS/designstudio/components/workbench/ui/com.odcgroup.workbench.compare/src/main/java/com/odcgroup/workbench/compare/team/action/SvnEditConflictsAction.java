package com.odcgroup.workbench.compare.team.action;

import org.eclipse.team.internal.ui.actions.TeamAction;
import org.eclipse.team.svn.ui.action.local.EditConflictsAction;

/**
 *
 * @author pkk
 *
 */
@SuppressWarnings("restriction")
public class SvnEditConflictsAction extends AbstractTeamAction {

	/**
	 * @param text
	 */
	public SvnEditConflictsAction() {
		super("Edit Conflicts");
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.compare.team.action.AbstractTeamAction#fetchTeamActionDelegate()
	 */
	public TeamAction fetchTeamActionDelegate() {
		return new EditConflictsAction();
	}

}

