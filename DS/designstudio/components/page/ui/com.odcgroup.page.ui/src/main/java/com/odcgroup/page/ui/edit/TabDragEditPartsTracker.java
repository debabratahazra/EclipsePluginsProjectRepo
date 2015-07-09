package com.odcgroup.page.ui.edit;

import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.tools.DragEditPartsTracker;

import com.odcgroup.page.model.Widget;


/**
 * Drag tracker edit part for the Tab widget.
 * 
 * @author Gary Hayes
 */
public class TabDragEditPartsTracker extends DragEditPartsTracker {

    /**
     * Constructs a new DragEditPartsTracker with the given source edit part.
     * 
     * @param sourceEditPart the source edit part
     */
    public TabDragEditPartsTracker(EditPart sourceEditPart) {
        super(sourceEditPart);
    }

    /**
     * Performs the appropriate selection action based on the selection state of the source. We select the ColumnHeader
     * edit part from the selected TableHeader edit part if the selection is outside the bounds of the TableHeader then
     * the table edit part is returned.
     */
    protected void performSelection() {
        super.performSelection();

        EditPartViewer viewer = getCurrentViewer();
        List so = viewer.getSelectedEditParts();
        if (so.size() == 0) {
            return;
        }

        WidgetEditPart wep = (WidgetEditPart) so.get(0);
        Widget tab = wep.getWidget();
        tab.setSelected(true);
        tab.setVisible(true);
        Widget tabbedPane = tab.getParent();
        int index = tabbedPane.getContents().indexOf(tab);
        tabbedPane.setIndexOfSelectedChild(index);
    }
}