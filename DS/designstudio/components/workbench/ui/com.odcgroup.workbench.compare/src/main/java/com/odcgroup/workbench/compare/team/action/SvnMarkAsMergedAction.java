package com.odcgroup.workbench.compare.team.action;

import org.eclipse.team.internal.ui.actions.TeamAction;
import org.eclipse.team.svn.ui.action.local.MarkAsMergedAction;

/**
 *
 * @author pkk
 *
 */
@SuppressWarnings("restriction")
public class SvnMarkAsMergedAction extends AbstractTeamAction {

	/**
	 * @param text
	 */
	public SvnMarkAsMergedAction() {
		super("Mark as Merged");
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.compare.team.action.AbstractTeamAction#fetchTeamActionDelegate()
	 */
	public TeamAction fetchTeamActionDelegate() {
		return new MarkAsMergedAction();
	}

}

