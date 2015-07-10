package com.zealcore.se.ui.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;

import com.zealcore.se.ui.SeUiPlugin;

public abstract class AbstractEditorAction implements IEditorActionDelegate {

    private IEditorPart targetEditor;

    private IAction action;

    private ISelection selection;

    public void setActiveEditor(final IAction action,
            final IEditorPart targetEditor) {
        this.action = action;
        this.targetEditor = targetEditor;
    }

    public IEditorPart getEditor() {
        return targetEditor;

    }

    public void run(final IAction action) {
        if (getTargetEditor() == null) {
            return;
        }
        try {
            runSafe(action);
        } catch (final RuntimeException ex) {
            handleException(ex);
        }

    }

    protected void handleException(final RuntimeException ex) {
        SeUiPlugin.logError(ex);
    }

    public final IEditorPart getTargetEditor() {
        return targetEditor;
    }

    public final IAction getAction() {
        return action;
    }

    protected abstract void runSafe(IAction action);

    public void selectionChanged(final IAction action,
            final ISelection selection) {
        this.selection = selection;
    }

    public final ISelection getSelection() {
        return selection;
    }
}
