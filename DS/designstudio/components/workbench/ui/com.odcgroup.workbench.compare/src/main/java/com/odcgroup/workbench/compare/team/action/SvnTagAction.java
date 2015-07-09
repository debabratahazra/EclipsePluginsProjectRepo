package com.odcgroup.workbench.compare.team.action;

import org.eclipse.team.internal.ui.actions.TeamAction;
import org.eclipse.team.svn.ui.SVNTeamUIPlugin;
import org.eclipse.team.svn.ui.action.local.TagAction;

/**
 *
 * @author pkk
 *
 */
@SuppressWarnings("restriction")
public class SvnTagAction extends AbstractTeamAction {

	/**
	 * @param text
	 */
	public SvnTagAction() {
		super("&Tag...");
		setImageDescriptor(SVNTeamUIPlugin.instance().getImageDescriptor("icons/common/actions/tag.gif"));
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.compare.team.action.AbstractTeamAction#fetchTeamActionDelegate()
	 */
	public TeamAction fetchTeamActionDelegate() {
		return new TagAction();
	}

}
