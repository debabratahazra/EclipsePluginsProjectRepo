package com.odcgroup.workbench.compare.team.action;

import org.eclipse.swt.SWT;
import org.eclipse.team.internal.ui.actions.TeamAction;
import org.eclipse.team.svn.ui.SVNTeamUIPlugin;
import org.eclipse.team.svn.ui.action.local.CommitAction;

/**
 *
 * @author pkk
 *
 */
@SuppressWarnings("restriction")
public class SvnCommitAction extends AbstractTeamAction {

	/**
	 * @param text
	 */
	public SvnCommitAction() {
		super("&Commit...@Ctrl+Alt+C");
		setImageDescriptor(SVNTeamUIPlugin.instance().getImageDescriptor("icons/common/actions/commit.gif"));
		setAccelerator(SWT.CTRL+SWT.ALT+'C');
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.compare.team.action.AbstractTeamAction#fetchTeamActionDelegate()
	 */
	public TeamAction fetchTeamActionDelegate() {
		return new CommitAction();
	}

}
