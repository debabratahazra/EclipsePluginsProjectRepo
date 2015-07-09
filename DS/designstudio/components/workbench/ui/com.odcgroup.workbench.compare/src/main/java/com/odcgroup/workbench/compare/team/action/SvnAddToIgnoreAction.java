package com.odcgroup.workbench.compare.team.action;

import org.eclipse.swt.SWT;
import org.eclipse.team.internal.ui.actions.TeamAction;
import org.eclipse.team.svn.ui.action.local.AddToSVNIgnoreAction;

/**
 *
 * @author pkk
 *
 */
@SuppressWarnings("restriction")
public class SvnAddToIgnoreAction extends AbstractTeamAction {

	/**
	 * @param text
	 */
	public SvnAddToIgnoreAction() {
		super("Add to svn:&ignore...@Ctrl+Alt+I");
		setAccelerator(SWT.CTRL+SWT.ALT+'I');
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.compare.team.action.AbstractTeamAction#fetchTeamActionDelegate()
	 */
	public TeamAction fetchTeamActionDelegate() {
		return new AddToSVNIgnoreAction();
	}

}
