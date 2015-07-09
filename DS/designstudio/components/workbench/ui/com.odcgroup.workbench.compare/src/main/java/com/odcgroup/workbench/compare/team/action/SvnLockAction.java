package com.odcgroup.workbench.compare.team.action;

import org.eclipse.swt.SWT;
import org.eclipse.team.internal.ui.actions.TeamAction;
import org.eclipse.team.svn.ui.SVNTeamUIPlugin;
import org.eclipse.team.svn.ui.action.local.LockAction;

/**
 *
 * @author pkk
 *
 */
@SuppressWarnings("restriction")
public class SvnLockAction extends AbstractTeamAction {

	/**
	 * @param text
	 */
	public SvnLockAction() {
		super("&Lock...@Ctrl+Alt+K");
		setImageDescriptor(SVNTeamUIPlugin.instance().getImageDescriptor("icons/common/actions/lock.gif"));
		setAccelerator(SWT.CTRL+SWT.ALT+'K');
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.compare.team.action.AbstractTeamAction#fetchTeamActionDelegate()
	 */
	public TeamAction fetchTeamActionDelegate() {
		return new LockAction();
	}

}
