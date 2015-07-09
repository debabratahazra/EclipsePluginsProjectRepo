package com.odcgroup.workbench.compare.team.action;

import org.eclipse.swt.SWT;
import org.eclipse.team.internal.ui.actions.TeamAction;
import org.eclipse.team.svn.ui.SVNTeamUIPlugin;
import org.eclipse.team.svn.ui.action.local.RevertAction;

/**
 *
 * @author pkk
 *
 */
@SuppressWarnings("restriction")
public class SvnRevertAction extends AbstractTeamAction {

	/**
	 * @param text
	 */
	public SvnRevertAction() {
		super("&Revert...");
		setImageDescriptor(SVNTeamUIPlugin.instance().getImageDescriptor("icons/common/actions/revert.gif"));
		setAccelerator(SWT.CTRL+SWT.ALT+'S');
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.compare.team.action.AbstractTeamAction#fetchTeamActionDelegate()
	 */
	public TeamAction fetchTeamActionDelegate() {
		return new RevertAction();
	}

}
