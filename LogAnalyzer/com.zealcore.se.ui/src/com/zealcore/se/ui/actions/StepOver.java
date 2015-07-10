package com.zealcore.se.ui.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;

import com.zealcore.se.ui.editors.IStepable;
import com.zealcore.se.ui.editors.LogsetEditor;

public class StepOver extends AbstractEditorAction {

    @Override
    protected void runSafe(final IAction action) {
        Object stepper = getEditor().getAdapter(IStepable.class);
        if (stepper instanceof IStepable) {
            IStepable stepable = (IStepable) stepper;
            stepable.stepForward();
        }
    }

    @Override
    public void selectionChanged(final IAction action,
            final ISelection selection) {
        final IEditorPart editor = getEditor();
        if (editor instanceof LogsetEditor) {
            LogsetEditor edit = (LogsetEditor) editor;
            if (edit.getLogsetBrowser() instanceof IStepable) {
                action.setEnabled(true);
            } else {
                action.setEnabled(false);
            }
        }
        super.selectionChanged(action, selection);
    }
}
