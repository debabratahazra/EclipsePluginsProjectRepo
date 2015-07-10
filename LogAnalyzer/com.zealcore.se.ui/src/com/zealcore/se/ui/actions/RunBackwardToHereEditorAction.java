package com.zealcore.se.ui.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;

import com.zealcore.se.core.model.IState;
import com.zealcore.se.core.model.IStateTransition;
import com.zealcore.se.ui.editors.ILogsetBrowser;
import com.zealcore.se.ui.editors.LogsetEditor;

public class RunBackwardToHereEditorAction extends AbstractEditorAction {

    public RunBackwardToHereEditorAction() {}

    @Override
    public void runSafe(final IAction action) {
        final IEditorPart editor = getEditor();
        if (!(editor instanceof LogsetEditor)) {
            throw new IllegalStateException(
                    "Action not allowed on this editor type.");
        }
        if (!(getSelection() instanceof IStructuredSelection)) {
            return;
        }
        final IStructuredSelection struct = (IStructuredSelection) getSelection();
        if (!(struct.getFirstElement() instanceof IState)) {
            if (!(struct.getFirstElement() instanceof IStateTransition)) {
                return;
            }
        }
        final LogsetEditor logsetEditor = (LogsetEditor) editor;
        final ILogsetBrowser logsetBrowser = logsetEditor.getLogsetBrowser();
        logsetBrowser.gotoPreviousLikeSelected();
    }

    @Override
    public void selectionChanged(final IAction action,
            final ISelection selection) {
        final IEditorPart editor = getEditor();
        if (!(editor instanceof LogsetEditor)) {
            action.setEnabled(false);
        }
        if (!(selection instanceof IStructuredSelection)) {
            action.setEnabled(false);
            return;
        }
        final IStructuredSelection struct = (IStructuredSelection) selection;
        if (!(struct.getFirstElement() instanceof IState)) {
            if (!(struct.getFirstElement() instanceof IStateTransition)) {
                action.setEnabled(false);
                return;
            }
        }
        action.setEnabled(true);
        super.selectionChanged(action, selection);
    }
}
