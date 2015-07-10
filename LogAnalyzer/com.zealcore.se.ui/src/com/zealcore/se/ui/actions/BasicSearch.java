package com.zealcore.se.ui.actions;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;

import com.zealcore.se.core.ifw.Logset;
import com.zealcore.se.ui.Messages;
import com.zealcore.se.ui.SeUiPlugin;
import com.zealcore.se.ui.internal.DirectoryLogSession;
import com.zealcore.se.ui.search.DebuggerSearch;

public class BasicSearch extends AbstractObjectDelegate {

    public BasicSearch() {}

    @Override
    public void runSafe(final IAction action) {
                
        if (Logset.isDisabled()) {
            ErrorDialog.openError(new Shell(), Messages.LICENSE_ERROR,
                    Messages.SEARCH_ERROR,
                    new Status(IStatus.ERROR, SeUiPlugin.PLUGIN_ID,
                            IStatus.ERROR, Messages.LICENSE_EXCEPTION + ".",
                            Logset.getException()));

            return;
        }
        
        if (guardFail()) {
            return;
        }

        final IWorkbenchWindow getWindow = getPart().getSite()
                .getWorkbenchWindow();
        org.eclipse.search.ui.NewSearchUI.openSearchDialog(getWindow,
                DebuggerSearch.PAGE_ID);
    }

    
    public void selectionChanged(IAction action, ISelection selection) {
		super.selectionChanged(action, selection);
		if (this.guardFail()) {
			return;
		}
		final IStructuredSelection struct = (IStructuredSelection) selection;
		if (struct.getFirstElement() instanceof DirectoryLogSession) {
			final DirectoryLogSession logSession = (DirectoryLogSession) struct
					.getFirstElement();

			Logset logset = Logset.valueOf(logSession.getId());
			if (logset.isLocked()) {
				action.setEnabled(false);
			} else {
				action.setEnabled(true);
			}
		}
	}
}
