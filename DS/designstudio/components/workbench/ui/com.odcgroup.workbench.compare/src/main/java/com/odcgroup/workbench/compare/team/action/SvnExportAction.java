package com.odcgroup.workbench.compare.team.action;

import org.eclipse.team.internal.ui.actions.TeamAction;
import org.eclipse.team.svn.ui.SVNTeamUIPlugin;
import org.eclipse.team.svn.ui.action.local.ExportAction;

/**
 *
 * @author pkk
 *
 */
@SuppressWarnings("restriction")
public class SvnExportAction extends AbstractTeamAction {

	/**
	 * @param text
	 */
	public SvnExportAction() {
		super("Export...");
		setImageDescriptor(SVNTeamUIPlugin.instance().getImageDescriptor("icons/common/export.gif"));
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.compare.team.action.AbstractTeamAction#fetchTeamActionDelegate()
	 */
	public TeamAction fetchTeamActionDelegate() {
		return new ExportAction();
	}

}
