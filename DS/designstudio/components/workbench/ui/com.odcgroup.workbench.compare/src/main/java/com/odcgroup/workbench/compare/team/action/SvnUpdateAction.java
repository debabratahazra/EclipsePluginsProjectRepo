package com.odcgroup.workbench.compare.team.action;

import org.eclipse.swt.SWT;
import org.eclipse.team.internal.ui.actions.TeamAction;
import org.eclipse.team.svn.ui.SVNTeamUIPlugin;
import org.eclipse.team.svn.ui.action.local.UpdateAction;

/**
 *
 * @author pkk
 *
 */
@SuppressWarnings("restriction")
public class SvnUpdateAction extends AbstractTeamAction {

	/**
	 * @param text
	 */
	public SvnUpdateAction() {
		super("&Update@Ctrl+Alt+U");
		setImageDescriptor(SVNTeamUIPlugin.instance().getImageDescriptor("icons/common/actions/update.gif"));
		setAccelerator(SWT.CTRL+SWT.ALT+'U');
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.compare.team.action.AbstractTeamAction#fetchTeamActionDelegate()
	 */
	public TeamAction fetchTeamActionDelegate() {
		return new UpdateAction();
	}

}
