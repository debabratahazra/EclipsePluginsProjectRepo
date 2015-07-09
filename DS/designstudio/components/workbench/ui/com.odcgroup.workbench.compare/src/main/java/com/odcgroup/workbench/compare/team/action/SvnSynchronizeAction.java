package com.odcgroup.workbench.compare.team.action;

import org.eclipse.swt.SWT;
import org.eclipse.team.internal.ui.actions.TeamAction;
import org.eclipse.team.svn.ui.SVNTeamUIPlugin;
import org.eclipse.team.svn.ui.action.local.SynchronizeAction;

/**
 *
 * @author pkk
 *
 */
@SuppressWarnings("restriction")
public class SvnSynchronizeAction extends AbstractTeamAction {

	/**
	 * @param text
	 */
	public SvnSynchronizeAction() {
		super("&Synchronize with Repository@Ctrl+Alt+S");
		setImageDescriptor(SVNTeamUIPlugin.instance().getImageDescriptor("icons/common/actions/synch.gif"));
		setAccelerator(SWT.CTRL+SWT.ALT+'S');
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.compare.team.action.AbstractTeamAction#fetchTeamActionDelegate()
	 */
	public TeamAction fetchTeamActionDelegate() {
		return new SynchronizeAction();
	}

}
