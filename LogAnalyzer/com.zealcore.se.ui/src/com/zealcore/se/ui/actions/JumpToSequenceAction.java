/**
 * 
 */
package com.zealcore.se.ui.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.zealcore.se.core.model.IActivity;
import com.zealcore.se.core.model.IObject;
import com.zealcore.se.core.model.ISequenceMessage;
import com.zealcore.se.ui.util.ArtifactSelection;

/**
 * @author pasa
 * 
 */
public class JumpToSequenceAction extends AbstractObjectDelegate {

    @Override
    public void runSafe(final IAction action) {
        if (guardFail()) {
            return;
        }
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
        if (item instanceof ISequenceMessage) {
            ISequenceMessage message = (ISequenceMessage) item;
            if (message.getDeliveryTime() >= 0) {
                artifactSelection.getSource().getTimeCluster().setCurrentTime(
                        message.getDeliveryTime());
            }
        } else if (item instanceof IActivity) {
            IActivity activity = (IActivity) item;
            if (activity.getTriggerTime() >= 0) {
                artifactSelection.getSource().getTimeCluster().setCurrentTime(
                        activity.getTriggerTime());
            }
        }
    }

    @Override
    public void selectionChanged(final IAction action,
            final ISelection selection) {

        if (!(selection instanceof IStructuredSelection)) {
            return;
        }
        final IStructuredSelection struct = (IStructuredSelection) selection;
        final Object o = struct.getFirstElement();
        if (!(o instanceof ArtifactSelection)) {
            return;
        }
        final ArtifactSelection artifactSelection = (ArtifactSelection) o;
        final IObject item = artifactSelection.getItem();
        if (item instanceof ISequenceMessage) {
            ISequenceMessage message = (ISequenceMessage) item;
            action.setText("Signal Receiver");
            if (message.getDeliveryTime() >= 0) {
                action.setEnabled(true);
            } else {
                action.setEnabled(false);
            }

        } else if (item instanceof IActivity) {
            IActivity activity = (IActivity) item;
            action.setText("Signal Sender");
            if (activity.getTriggerTime() >= 0) {
                action.setEnabled(true);
            } else {
                action.setEnabled(false);
            }
        } else {
            action.setText("Undefined");
            action.setEnabled(false);
        }
        super.selectionChanged(action, selection);

    }
}
