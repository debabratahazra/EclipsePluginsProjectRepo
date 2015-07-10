package com.zealcore.se.ui.actions;

import java.util.List;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

import com.zealcore.se.core.ifw.Logset;
import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.IArtifactID;
import com.zealcore.se.core.model.ISequence;
import com.zealcore.se.ui.ITimeCluster;
import com.zealcore.se.ui.editors.ILogSessionWrapper;
import com.zealcore.se.ui.editors.SequenceDiagram;

public class OpenSequenceDiagramAction extends AbstractOpenBrowserAction {

    public OpenSequenceDiagramAction() {}

    @Override
    public IArtifactID getData() {

        final ITimeCluster cluster = (ITimeCluster) getFirstElement();
        final ElementListSelectionDialog dlg = new ElementListSelectionDialog(
                null, new LabelProvider() {
                    @Override
                    public String getText(final Object element) {
                        if (element instanceof IArtifact) {
                            return ((IArtifact) element).getName();
                        }
                        return super.getText(element);
                    }
                });

        final ILogSessionWrapper parent = cluster.getParent();

        final List<ISequence> list = Logset.valueOf(parent.getId())
                .getArtifacts(ISequence.class);
        // Sequence Diagram: If there is only one sequence in the logset,
        // do not show the sequence selection dialog when opening the view
        if (list.size() == 1) {
            return list.get(0);
        }

        dlg.setElements(list.toArray());
        dlg.setTitle("Select Sequence");
        dlg.setIgnoreCase(true);
        dlg.setMessage("Select Sequence to open in this ViewSet");
        if (dlg.open() != Window.OK) {
            return null;
        }
        return (IArtifactID) dlg.getFirstResult();
    }

    @Override
    protected String getBrowserId() {
        return SequenceDiagram.VIEW_ID;
    }
}
