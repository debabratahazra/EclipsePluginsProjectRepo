package com.odcgroup.workbench.compare.team.action;

import org.eclipse.team.internal.ui.actions.TeamAction;
import org.eclipse.team.svn.ui.SVNTeamUIPlugin;
import org.eclipse.team.svn.ui.action.local.EditPropertiesAction;

/**
 *
 * @author pkk
 *
 */
@SuppressWarnings("restriction")
public class SvnEditPropertiesAction extends AbstractTeamAction {

	/**
	 * @param text
	 */
	public SvnEditPropertiesAction() {
		super("Show &Properties");
		setImageDescriptor(SVNTeamUIPlugin.instance().getImageDescriptor("icons/views/propertiesedit.gif"));
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.compare.team.action.AbstractTeamAction#fetchTeamActionDelegate()
	 */
	public TeamAction fetchTeamActionDelegate() {
		return new EditPropertiesAction();
	}

}
