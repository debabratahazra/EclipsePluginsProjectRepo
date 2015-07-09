package com.odcgroup.workbench.compare.team.action;

import org.eclipse.team.internal.ui.actions.TeamAction;
import org.eclipse.team.svn.ui.SVNTeamUIPlugin;
import org.eclipse.team.svn.ui.action.local.BranchAction;

/**
 *
 * @author pkk
 *
 */
@SuppressWarnings("restriction")
public class SvnBranchAction extends AbstractTeamAction {

	/**
	 * @param text
	 */
	public SvnBranchAction() {
		super("&Branch...");
		setImageDescriptor(SVNTeamUIPlugin.instance().getImageDescriptor("icons/common/actions/branch.gif"));
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.compare.team.action.AbstractTeamAction#fetchTeamActionDelegate()
	 */
	public TeamAction fetchTeamActionDelegate() {
		return new BranchAction();
	}

}
