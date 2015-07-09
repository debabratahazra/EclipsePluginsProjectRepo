package com.odcgroup.workbench.compare.team.action;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFileState;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.team.internal.ui.TeamUIMessages;
import org.eclipse.team.internal.ui.history.CompareLocalHistory;

/**
 * @author pkk
 *
 */
@SuppressWarnings("restriction")
public class CompareLocalHistoryAction extends CompareLocalHistory {

	@Override
	protected IFileState[] getLocalHistory() {
		IFile file = (IFile) getSelection().getFirstElement();
		IFileState states[] = null;
		try {
			states = file.getHistory(null);
		} catch (CoreException ex) {
			MessageDialog.openError(getShell(), getPromptTitle(), ex.getMessage());
			return null;
		}
		if (states == null || states.length <= 0) {
			MessageDialog.openInformation(getShell(), getPromptTitle(), TeamUIMessages.ShowLocalHistory_0);
			return states;
		} else {
			return states;
		}
	}

}
