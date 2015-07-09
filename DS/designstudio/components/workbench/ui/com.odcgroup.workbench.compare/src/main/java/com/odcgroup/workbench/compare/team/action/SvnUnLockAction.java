package com.odcgroup.workbench.compare.team.action;

import org.eclipse.team.internal.ui.actions.TeamAction;
import org.eclipse.team.svn.ui.SVNTeamUIPlugin;
import org.eclipse.team.svn.ui.action.local.UnlockAction;

/**
 *
 * @author pkk
 *
 */
@SuppressWarnings("restriction")
public class SvnUnLockAction extends AbstractTeamAction {

	/**
	 * @param text
	 */
	public SvnUnLockAction() {
		super("&UnLock");
		setImageDescriptor(SVNTeamUIPlugin.instance().getImageDescriptor("icons/common/actions/unlock.gif"));
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.compare.team.action.AbstractTeamAction#fetchTeamActionDelegate()
	 */
	public TeamAction fetchTeamActionDelegate() {
		return new UnlockAction();
	}

}
