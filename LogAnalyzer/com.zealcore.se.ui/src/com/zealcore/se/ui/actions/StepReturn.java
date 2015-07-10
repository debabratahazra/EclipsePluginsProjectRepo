package com.zealcore.se.ui.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;

import com.zealcore.se.ui.editors.IStepable;
import com.zealcore.se.ui.editors.LogsetEditor;

public class StepReturn extends AbstractEditorAction {

    @Override
    protected void runSafe(final IAction action) {
        Object stepper = getEditor().getAdapter(IStepable.class);
        if (stepper instanceof IStepable) {
            IStepable stepable = (IStepable) stepper;
            stepable.stepBack();
        }
    }

    @Override
    public void selectionChanged(final IAction action,
            final ISelection selection) {
        final IEditorPart editor = getEditor();
        boolean enable = false;
        if (editor instanceof LogsetEditor) {
            LogsetEditor edit = (LogsetEditor) editor;
            if (edit.getLogsetBrowser() instanceof IStepable) {
                enable = true;
            } else {}
        }
        action.setEnabled(enable);
        super.selectionChanged(action, selection);
    }
}
