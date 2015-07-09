package com.odcgroup.workbench.compare.team.action;

import org.eclipse.team.internal.ui.actions.TeamAction;
import org.eclipse.team.svn.ui.SVNTeamUIPlugin;
import org.eclipse.team.svn.ui.action.local.SwitchAction;

/**
 *
 * @author pkk
 *
 */
@SuppressWarnings("restriction")
public class SvnSwitchAction extends AbstractTeamAction {

	/**
	 * @param text
	 */
	public SvnSwitchAction() {
		super("&Switch...");
		setImageDescriptor(SVNTeamUIPlugin.instance().getImageDescriptor("icons/common/actions/switch.gif"));
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.compare.team.action.AbstractTeamAction#fetchTeamActionDelegate()
	 */
	public TeamAction fetchTeamActionDelegate() {
		return new SwitchAction();
	}

}
