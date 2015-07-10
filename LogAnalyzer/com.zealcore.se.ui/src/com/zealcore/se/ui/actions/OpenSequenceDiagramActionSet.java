package com.zealcore.se.ui.actions;

import com.zealcore.se.ui.editors.SequenceDiagram;

public class OpenSequenceDiagramActionSet extends AbstractActionSetOpenEditor {

    public OpenSequenceDiagramActionSet() {}

    protected String getBrowserId() {
        return SequenceDiagram.VIEW_ID;
    }

    protected String getName() {
        return SequenceDiagram.NAME;
    }

}
