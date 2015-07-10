package com.zealcore.se.ui.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;

import com.zealcore.se.ui.editors.IToggleable;
import com.zealcore.se.ui.editors.LogsetEditor;

public class ToggleLogaritmicAction extends AbstractEditorAction {

    @Override
    protected void runSafe(final IAction action) {
        toggle();
    }

//    @Override
//    public void setActiveEditor(final IAction action,
//            final IEditorPart targetEditor) {
//        super.setActiveEditor(action, targetEditor);
//        toggle();
//    }

    private void toggle() {
        Object stepper = getEditor().getAdapter(IToggleable.class);
        if (stepper instanceof IToggleable) {
            IToggleable toggle = (IToggleable) stepper;
            toggle.toggleScale();
            LogsetEditor ed = (LogsetEditor) getEditor();
            ed.refresh();
        }
    }

    @Override
    public void selectionChanged(final IAction action,
            final ISelection selection) {
        final IEditorPart editor = getEditor();
        if (editor instanceof LogsetEditor) {
            LogsetEditor edit = (LogsetEditor) editor;
            if (edit.getLogsetBrowser() instanceof IToggleable) {
                action.setEnabled(true);
            } else {
                action.setEnabled(false);
            }
        }
        super.selectionChanged(action, selection);
    }
}
