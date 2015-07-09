package com.odcgroup.workbench.compare.team.action;

import org.eclipse.team.internal.ui.actions.TeamAction;
import org.eclipse.team.svn.ui.SVNTeamUIPlugin;
import org.eclipse.team.svn.ui.action.local.ShowHistoryAction;

/**
 *
 * @author pkk
 *
 */
@SuppressWarnings("restriction")
public class SvnShowHistoryAction extends AbstractTeamAction {

	/**
	 * @param text
	 */
	public SvnShowHistoryAction() {
		super("&Show &History");
		setImageDescriptor(SVNTeamUIPlugin.instance().getImageDescriptor("icons/common/actions/showhistory.gif"));
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.compare.team.action.AbstractTeamAction#fetchTeamActionDelegate()
	 */
	public TeamAction fetchTeamActionDelegate() {
		return new ShowHistoryAction();
	}

}
