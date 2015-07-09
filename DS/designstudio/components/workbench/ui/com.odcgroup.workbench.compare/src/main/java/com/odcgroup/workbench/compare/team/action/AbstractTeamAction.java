package com.odcgroup.workbench.compare.team.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.team.internal.ui.actions.TeamAction;
import org.eclipse.ui.PlatformUI;


@SuppressWarnings("restriction")
public abstract class AbstractTeamAction extends Action {
	
	protected TeamAction teamAction = null;
	
	public AbstractTeamAction(String text) {
		setText(text);
		this.teamAction = fetchTeamActionDelegate();
	}
	
	/**
	 * @return
	 * @throws SvnTeamProviderException
	 */
	public abstract TeamAction fetchTeamActionDelegate();	
	
	/**
	 * 
	 */
	public final void dispose() {
		if (teamAction != null)
			teamAction.dispose();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public final void run() {
		if (teamAction != null) {
			teamAction.setActivePart(this, 
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart());
			teamAction.run(this);
		}
	}
	
	/**
	 * @param selection
	 */
	public final void selectionChanged(ISelection selection) {
		if (teamAction != null)
			teamAction.selectionChanged(this, selection);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#isEnabled()
	 */
	public final boolean isEnabled() {
		if (teamAction != null) {
			return teamAction.isEnabled();
		} else {
			return false;
		}
	}

}
