package com.zealcore.se.ui.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Display;

import com.zealcore.se.core.model.IActivity;
import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.IDuration;
import com.zealcore.se.core.model.ILogEvent;
import com.zealcore.se.ui.core.IEventColorProvider;
import com.zealcore.se.ui.graphics.ColorAdapter;
import com.zealcore.se.ui.graphics.IColor;
import com.zealcore.se.ui.util.ArtifactColorMap;
import com.zealcore.se.ui.util.ArtifactSelection;

public class SetResourceColorAction extends AbstractObjectDelegate {

    public SetResourceColorAction() {}

    @Override
    public void runSafe(final IAction action) {
        if (guardFail()) {
            return;
        }
        if (!(getSelection() instanceof IStructuredSelection)) {
            return;
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

        if (!(element instanceof IArtifact) && !(element instanceof ILogEvent)) {
            return;
        }
        final ArtifactColorMap colorMap = artifactSelection.getSource()
                .getLog().getColorMap();
        final ColorDialog dlg = new ColorDialog(Display.getDefault()
                .getActiveShell());
        final RGB color = dlg.open();
        if (color == null) {
            return;
        }

        if (element instanceof IArtifact) {
            final IArtifact abstractArtifact = (IArtifact) element;
            colorMap.setColor(abstractArtifact, color);
        } else if (element instanceof ILogEvent) {
            ILogEvent o = (ILogEvent) element;
            final ILogEvent event = (ILogEvent) o;
            final IEventColorProvider colorProvider = getServiceProvider()
                    .getService(IEventColorProvider.class);
            final IColor clr = ColorAdapter.valueOf(color);
            colorProvider.setColor(event.getType(), clr);
        }
    }
}
