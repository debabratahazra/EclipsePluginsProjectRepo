package com.zealcore.se.ui.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.zealcore.se.core.model.IObject;
import com.zealcore.se.core.model.ITimed;
import com.zealcore.se.ui.util.ArtifactSelection;

public class GotoDurationStartAction extends AbstractObjectDelegate {

    public GotoDurationStartAction() {}

    @Override
    public void runSafe(final IAction action) {
        if (!(getSelection() instanceof IStructuredSelection)) {
            return;
        }
        final IStructuredSelection struct = (IStructuredSelection) getSelection();
        final Object o = struct.getFirstElement();
        if (!(o instanceof ArtifactSelection)) {
            return;
        }
        final ArtifactSelection artifactSelection = (ArtifactSelection) o;
        final IObject item = artifactSelection.getItem();
        if (!(item instanceof ITimed)) {
            return;
        }
        final ITimed timed = (ITimed) item;
        artifactSelection.getSource().getTimeCluster().setCurrentTime(
                timed.getTimeReference());
    }
}
