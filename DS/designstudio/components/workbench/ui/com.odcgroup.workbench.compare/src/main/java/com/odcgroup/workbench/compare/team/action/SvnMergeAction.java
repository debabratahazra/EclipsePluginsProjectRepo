package com.odcgroup.workbench.compare.team.action;

import org.eclipse.team.internal.ui.actions.TeamAction;
import org.eclipse.team.svn.ui.SVNTeamUIPlugin;
import org.eclipse.team.svn.ui.action.local.MergeAction;

/**
 *
 * @author pkk
 *
 */
@SuppressWarnings("restriction")
public class SvnMergeAction extends AbstractTeamAction {

	/**
	 * @param text
	 */
	public SvnMergeAction() {
		super("&Merge...");
		setImageDescriptor(SVNTeamUIPlugin.instance().getImageDescriptor("icons/common/actions/merge.gif"));
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.compare.team.action.AbstractTeamAction#fetchTeamActionDelegate()
	 */
	public TeamAction fetchTeamActionDelegate() {
		return new MergeAction();
	}

}
