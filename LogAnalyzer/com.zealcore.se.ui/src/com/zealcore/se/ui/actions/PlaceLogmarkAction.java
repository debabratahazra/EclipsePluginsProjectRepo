package com.zealcore.se.ui.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;

import com.zealcore.se.core.ifw.Logset;
import com.zealcore.se.ui.ITimeCluster;
import com.zealcore.se.ui.editors.ILogSessionWrapper;

public class PlaceLogmarkAction extends AbstractObjectDelegate {

    public PlaceLogmarkAction() {}

    public static void placeLogmarkDialog(final ITimeCluster tc) {
        if (!tc.getParent().getCaseFile().getResource().exists()) {
            return;
        }
        final IInputValidator validator = new IInputValidator() {
            public String isValid(final String newText) {
                if (newText.length() < 1) {
                    return "Name must be at least one character";
                }
                return null;
            }
        };
        final InputDialog logmarkDialog = new InputDialog(null, "Logmark",
                "Logmark note", tc.toString(), validator);
        logmarkDialog.setBlockOnOpen(true);
        if (Window.OK == logmarkDialog.open()) {
            final String note = logmarkDialog.getValue();
            tc.logmark(note);
        }
    }

    @Override
    public void runSafe(final IAction action) {
        if (guardFail()) {
            return;
        }
        if (!(getSelection() instanceof IStructuredSelection)) {
            return;
        }
        final IStructuredSelection struct = (IStructuredSelection) getSelection();
        final Object first = struct.getFirstElement();
        final ITimeCluster tc = (ITimeCluster) first;
        PlaceLogmarkAction.placeLogmarkDialog(tc);
    }
    
	public void selectionChanged(IAction action, ISelection selection) {
		super.selectionChanged(action, selection);
		if (this.guardFail()) {
			return;
		}
		final IStructuredSelection struct = (IStructuredSelection) selection;
		if (struct.getFirstElement() instanceof ITimeCluster) {
			final ITimeCluster logSession = (ITimeCluster) struct
					.getFirstElement();
			ILogSessionWrapper lWarapper = logSession.getParent();

			Logset logset = Logset.valueOf(lWarapper.getId());
			if (logset.isLocked()) {
				action.setEnabled(false);
			} else {
				action.setEnabled(true);
			}
		}
	}

}
