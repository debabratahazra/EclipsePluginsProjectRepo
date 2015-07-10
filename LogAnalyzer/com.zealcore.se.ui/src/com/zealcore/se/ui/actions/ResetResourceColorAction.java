package com.zealcore.se.ui.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.zealcore.se.core.model.IActivity;
import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.IDuration;
import com.zealcore.se.ui.util.ArtifactColorMap;
import com.zealcore.se.ui.util.ArtifactSelection;

public class ResetResourceColorAction extends AbstractObjectDelegate {

    public ResetResourceColorAction() {}

    @Override
    public void runSafe(final IAction action) {
        if (guardFail()) {
            return;
        }
        if (!(getSelection() instanceof IStructuredSelection)) {
            return;
            // TODO Refactor implementation, create an AdapterFactory for model
            // instances
            // that can adapt stuff into Artifacts, for instance
            // AbstractDuration =>
            // IArtifact via resource etc
        }
        final IStructuredSelection struct = (IStructuredSelection) getSelection();
        final Object firstElement = struct.getFirstElement();
        if (!(firstElement instanceof ArtifactSelection)) {
            return;
        }
        Object element = null;
        final ArtifactSelection artifactSelection = (ArtifactSelection) firstElement;
        element = artifactSelection.getItem();
        if (element instanceof IActivity) {
            final IActivity activity = (IActivity) element;
            element = activity.getOwner();
        } else if (element instanceof IDuration) {
            final IDuration abstractDuration = (IDuration) element;
            element = abstractDuration.getOwner();
        }
        if (!(element instanceof IArtifact)) {
            return;
        }
        final IArtifact abstractArtifact = (IArtifact) element;
        final ArtifactColorMap colorMap = artifactSelection.getSource()
                .getLog().getColorMap();
        colorMap.resetColor(abstractArtifact);
        // refreshUI();
    }

    // private void refreshUI() {
    // final IWorkbench wb = PlatformUI.getWorkbench();
    // final IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
    // final IWorkbenchPage page = win.getActivePage();
    //     
    // for (final IViewReference ref : page.getViewReferences()) {
    // final IViewPart referencedPart = ref.getView(false);
    // if (referencedPart instanceof IViewSetView) {
    // final IViewSetView logview = (IViewSetView) referencedPart;
    // logview.refresh();
    // }
    // }
    // }
}
