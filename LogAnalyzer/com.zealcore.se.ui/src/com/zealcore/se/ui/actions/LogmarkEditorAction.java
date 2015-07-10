package com.zealcore.se.ui.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;

import com.zealcore.se.ui.ITimeCluster;
import com.zealcore.se.ui.editors.LogsetEditor;

public class LogmarkEditorAction extends AbstractEditorAction {

    @Override
    protected void runSafe(final IAction action) {
        if (getTargetEditor() instanceof LogsetEditor) {
            LogsetEditor logEditor = (LogsetEditor) getTargetEditor();
            ITimeCluster timeCluster = logEditor.getInput().getTimeCluster();
            placeLogmarkDialog(timeCluster);
        }
    }

    public static void placeLogmarkDialog(final ITimeCluster tc) {
        if (!tc.getParent().getCaseFile().getResource().exists()) {
            return;
        }
        final IInputValidator validator = new IInputValidator() {
            public String isValid(final String newText) {
                if (newText.length() < 1) {
                    return "Name must be atleast one character";
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

}
